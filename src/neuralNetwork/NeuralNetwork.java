package neuralNetwork;

public class NeuralNetwork {

 /**
  * The global error for the training.
  */
 protected double globalError;

 /**
  * The number of input neurons.
  */
 protected int inputNumber;

 /**
  * The number of hidden neurons.
  */
 protected int hiddenNumber;

 /**
  * The number of output neurons
  */
 protected int outputNumber;

 /**
  * The total number of neurons in the network.
  */
 protected int neuronsNumber;

 /**
  * The number of weights in the network.
  */
 protected int weightsNumber;

 /**
  * The learning rate.
  */
 protected double learning;

 /**
  * The outputs from the various levels.
  */
 protected double outputsFromLayer[];

 /**
  * The weight matrix this, along with the thresholds can be
  * thought of as the "memory" of the neural network.
  */
 protected double weights[];

 /**
  * The errors from the last calculation.
  */
 protected double error[];

 /**
  * Accumulates matrix delta's for training.
  */
 protected double accDelta[];

 /**
  * The thresholds, this value, along with the weight matrix
  * can be thought of as the memory of the neural network.
  */
 protected double thresholds[];

 /**
  * The changes that should be applied to the weight
  * matrix.
  */
 protected double weightsDelta[];

 /**
  * The accumulation of the threshold deltas.
  */
 protected double accThresholdDelta[];

 /**
  * The threshold deltas.
  */
 protected double thresholdDelta[];

 /**
  * The momentum for training.
  */
 protected double momentum;

 /**
  * The changes in the errors.
  */
 protected double errorDelta[];


 //constructor
 public NeuralNetwork(int input,
         int hidden,
         int output,
         double learnRate,
         double momentum) {

  this.learning = learnRate;
  this.momentum = momentum;

  this.inputNumber = input;
  this.hiddenNumber = hidden;
  this.outputNumber = output;
  neuronsNumber = input + hidden + output;
  weightsNumber = (input * hidden) + (hidden * output);

  outputsFromLayer    = new double[neuronsNumber];
  weights   = new double[weightsNumber];
  weightsDelta = new double[weightsNumber];
  thresholds = new double[neuronsNumber];
  errorDelta = new double[neuronsNumber];
  error    = new double[neuronsNumber];
  accThresholdDelta = new double[neuronsNumber];
  accDelta = new double[weightsNumber];
  thresholdDelta = new double[neuronsNumber];

  reset();
 }



 /**
  * @param len The length of a complete training set.
  * @return The current error for the neural network.
  */
 public double getError(int len) {
  double err = Math.sqrt(globalError / (len * outputNumber));
  globalError = 0; // clear the accumulator
  return err;

 }

 /**
  * @param sum The activation from the neuron.
  * @return The activation applied to the threshold method.
  */
 public double sigmoid(double sum) {
  return 1.0 / (1 + Math.exp(-1.0 * sum));
 }

 /**
  * @param input The input provide to the neural network.
  * @return The results from the output neurons.
  */
 public double []calculateOutput(double input[]) {
  int i, j;
  final int hiddenIndex = inputNumber;
  final int outIndex = inputNumber + hiddenNumber;

  // input layer
  for (i = 0; i < inputNumber; i++) {
   outputsFromLayer[i] = input[i];
  }

  // hidden layer
  int inx = 0;

  for (i = hiddenIndex; i < outIndex; i++) {
   double sum = thresholds[i];

   for (j = 0; j < inputNumber; j++) {
    sum += outputsFromLayer[j] * weights[inx++];
   }
   outputsFromLayer[i] = sigmoid(sum);
  }

  // out layer

  double result[] = new double[outputNumber];

  for (i = outIndex; i < neuronsNumber; i++) {
   double sum = thresholds[i];

   for (j = hiddenIndex; j < outIndex; j++) {
    sum += outputsFromLayer[j] * weights[inx++];
   }
   outputsFromLayer[i] = sigmoid(sum);
   result[i-outIndex] = outputsFromLayer[i];
  }

  return result;
 }


 /**
  * @param ideal What the output neurons should have yielded.
  */
 public void calcError(double ideal[]) {
  int i, j;
  final int hiddenIndexStart = inputNumber;
  final int outputIndexStart = inputNumber + hiddenNumber;

  // clear hidden layer errors
  for (i = inputNumber; i < neuronsNumber; i++) {
   error[i] = 0;
  }

  //backpropagation
  // layer errors and deltas for output layer
  for (i = outputIndexStart; i < neuronsNumber; i++) {
   error[i] = ideal[i - outputIndexStart] - outputsFromLayer[i];
   globalError += error[i] * error[i];
   errorDelta[i] = error[i] * outputsFromLayer[i] * (1 - outputsFromLayer[i]);
  }

  // hidden layer errors
  int winx = inputNumber * hiddenNumber;

  for (i = outputIndexStart; i < neuronsNumber; i++) {
   for (j = hiddenIndexStart; j < outputIndexStart; j++) {
    accDelta[winx] += errorDelta[i] * outputsFromLayer[j];
    error[j] += weights[winx] * errorDelta[i];
    winx++;
   }
   accThresholdDelta[i] += errorDelta[i];
  }

  // hidden layer deltas
  for (i = hiddenIndexStart; i < outputIndexStart; i++) {
   errorDelta[i] = error[i] * outputsFromLayer[i] * (1 - outputsFromLayer[i]);
  }

  // input layer errors
  winx = 0; // offset into weight array
  for (i = hiddenIndexStart; i < outputIndexStart; i++) {
   for (j = 0; j < hiddenIndexStart; j++) {
    accDelta[winx] += errorDelta[i] * outputsFromLayer[j];
    error[j] += weights[winx] * errorDelta[i];
    winx++;
   }
   accThresholdDelta[i] += errorDelta[i];
  }
 }

 /**
  * Modify the weight matrix and thresholds
  */
 public void learn() {
  int i;

  // process the matrix
  for (i = 0; i < weights.length; i++) {
   weightsDelta[i] = (learning * accDelta[i]) + (momentum * weightsDelta[i]);
   weights[i] += weightsDelta[i];
   accDelta[i] = 0;
  }

  // process the thresholds
  for (i = inputNumber; i < neuronsNumber; i++) {
   thresholdDelta[i] = learning * accThresholdDelta[i] + (momentum * thresholdDelta[i]);
   thresholds[i] += thresholdDelta[i];
   accThresholdDelta[i] = 0;
  }
 }

 /**
  * random weights and thresholds
  */
 public void reset() {
  int i;

  for (i = 0; i < neuronsNumber; i++) {
   thresholds[i] = 0.5 - (Math.random());
   thresholdDelta[i] = 0;
   accThresholdDelta[i] = 0;
  }
  for (i = 0; i < weights.length; i++) {
   weights[i] = 0.5 - (Math.random());
   weightsDelta[i] = 0;
   accDelta[i] = 0;
  }
 }
}
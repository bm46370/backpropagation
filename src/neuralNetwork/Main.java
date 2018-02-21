package neuralNetwork;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT's event classes and listener interfaces
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;

import javax.swing.*;    // Using Swing's components and container
 
/**
 * Backpropagation neural network
 */
@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {
   // Define constants for the various dimensions
   public static final int CANVAS_WIDTH = 500;
   public static final int CANVAS_HEIGHT = 300;
   public static final Color LINE_COLOR = Color.BLUE;
   //number of dots that will be used for representing any drawing
   private static final int M = 50;  
 
   // Lines drawn, consists of a List of PolyLine instances
   private Drawing drawing = new Drawing();
   private Drawing currentLine;  // the current line (for capturing)
   private List<Double> representativeDots= new ArrayList<Double>();
 
   // Constructor to set up the GUI components and event handlers
   public Main() {
	   
	   Menu fileMenu = new Menu("File",true);
	      fileMenu.add("Add character Alpha in training set");
	      fileMenu.addSeparator();
	      fileMenu.add("Add character Beta in training set");
	      fileMenu.addSeparator();
	      fileMenu.add("Add character Gamma in training set"); 
	      fileMenu.addSeparator();
	      fileMenu.add("Add character Delta in training set"); 
	      fileMenu.addSeparator();
	      fileMenu.add("Add character Epsilon in training set"); 
	      fileMenu.addSeparator();
	      fileMenu.add("Learn and decode written character");   
	      fileMenu.addSeparator();   
	      fileMenu.add("Quit");
	      fileMenu.addActionListener(this);
	      
	      MenuBar mb = new MenuBar();
	      mb.add(fileMenu);
	      
	      setMenuBar(mb);
	      
      DrawCanvas canvas = new DrawCanvas();
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
      canvas.addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent evt) {
            // Begin a new line
            currentLine = new Drawing();
            drawing=currentLine;
            currentLine.addPoint(evt.getX(), evt.getY());
         }
      });
      canvas.addMouseMotionListener(new MouseMotionAdapter() {
         @Override
         public void mouseDragged(MouseEvent evt) {
            currentLine.addPoint(evt.getX(), evt.getY());
            repaint();  // invoke paintComponent()
         }
      });
 
      setContentPane(canvas);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("Neuro example Bruno Matijak");
      pack();
      setVisible(true);
   }
 
   // Define inner class DrawCanvas, which is a JPanel used for custom drawing
   private class DrawCanvas extends JPanel {
      @Override
      protected void paintComponent(Graphics g) { // called back via repaint()
         super.paintComponent(g);
         g.setColor(LINE_COLOR);
         drawing.draw(g);
      }
   }
   
   public void actionPerformed(ActionEvent evt) {

     String command = evt.getActionCommand();

     if (command.equals("Quit")) {
         dispose();  // Close the window, then end the program.
         System.exit(0);
     }
     else if (command.equals("Add character Alpha in training set")){
    	 System.out.println("Add character Alpha in training set");
    	 drawing.setDouble(transformCordinates(drawing.xList, drawing.yList));
    	 //System.out.println(crtez.xListDoubleTransformed.size());
    	 representativeDots = computeRepresentativeDots(drawing.xListDoubleTransformed, drawing.yListDoubleTransformed);
    	 representativeDots.add(1.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 System.out.println(representativeDots);
    	 System.out.println(representativeDots.size());
    	 try {
			addInTxt(representativeDots);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 drawing = new Drawing();
    	 repaint();
     }
     else if (command.equals("Add character Beta in training set")){
    	 System.out.println("Add character Beta in training set");
    	 drawing.setDouble(transformCordinates(drawing.xList, drawing.yList));
    	 representativeDots = computeRepresentativeDots(drawing.xListDoubleTransformed, drawing.yListDoubleTransformed);
    	 representativeDots.add(0.0);
    	 representativeDots.add(1.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 System.out.println(representativeDots);
    	 System.out.println(representativeDots.size());
    	 try {
			addInTxt(representativeDots);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 drawing = new Drawing();
    	 repaint();
     }
     else if (command.equals("Add character Gamma in training set")){
    	 System.out.println("Add character Gamma in training set");
    	 drawing.setDouble(transformCordinates(drawing.xList, drawing.yList));
    	 representativeDots = computeRepresentativeDots(drawing.xListDoubleTransformed, drawing.yListDoubleTransformed);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(1.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 System.out.println(representativeDots);
    	 System.out.println(representativeDots.size());
    	 try {
			addInTxt(representativeDots);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 drawing = new Drawing();
    	 repaint();
     }
     else if (command.equals("Add character Delta in training set")){
    	 System.out.println("Add character Delta in training set");
    	 drawing.setDouble(transformCordinates(drawing.xList, drawing.yList));
    	 representativeDots = computeRepresentativeDots(drawing.xListDoubleTransformed, drawing.yListDoubleTransformed);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(1.0);
    	 representativeDots.add(0.0);
    	 System.out.println(representativeDots);
    	 System.out.println(representativeDots.size());
    	 try {
			addInTxt(representativeDots);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 drawing = new Drawing();
    	 repaint();
     }
     else if (command.equals("Add character Epsilon in training set")){
    	 System.out.println("Add character Epsilon in training set");
    	 drawing.setDouble(transformCordinates(drawing.xList, drawing.yList));
    	 representativeDots = computeRepresentativeDots(drawing.xListDoubleTransformed, drawing.yListDoubleTransformed);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(0.0);
    	 representativeDots.add(1.0);
    	 System.out.println(representativeDots);
    	 System.out.println(representativeDots.size());
    	 try {
			addInTxt(representativeDots);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 drawing = new Drawing();
    	 repaint();
     }
     else if (command.equals("Learn and decode written character")){
    	 System.out.println("Learn and decode written character");

    	 System.out.println("Starting neural network... ");
    	 drawing.setDouble(transformCordinates(drawing.xList, drawing.yList));
    	 representativeDots = computeRepresentativeDots(drawing.xListDoubleTransformed, drawing.yListDoubleTransformed);
    	 System.out.println(representativeDots);
    	 System.out.println(representativeDots.size());
    	 
    	 double inputs[][]= new double[100][100];
    	 double ideal[][]= new double[100][5];
    	 double writen[]= new double[100];
    	 int row=0;
    	 	
    	 try(BufferedReader in = new BufferedReader(new FileReader("data.txt"))){
	    	 String line;
			while((line = in.readLine()) != null){
			 		String[] tokens = line.split(" ");
			 		
			 		for(int i=0;i<(tokens.length-5);i++){
			 			inputs[row][i]=Double.parseDouble(tokens[i]);
			 			if(row==0)writen[i]=representativeDots.get(i);
			 }
			 		
			 for(int i=(tokens.length-5);i<tokens.length;i++){
			 		System.out.println(i + "  " + row);
			 		ideal[row][i-100]=Double.parseDouble(tokens[i]);
			 		System.out.println(ideal[row][i-100]);
			 }
			 		
			 row++;
			 
			 }
		} catch (Exception e) {
		}
    	 	
    	 System.out.println("Learning:");

    	 NeuralNetwork network = new NeuralNetwork(100,52,5,0.8,0.1);

    	 NumberFormat percentFormat = NumberFormat.getPercentInstance();
    	 percentFormat.setMinimumFractionDigits(4);


    	 for (int i=0;i<10000;i++) {
    	   for (int j=0;j<inputs.length;j++) {
    	    network.calculateOutput(inputs[j]);
    	    network.calcError(ideal[j]);
    	    network.learn();
    	   }
    	   System.out.println( "Epoh #" + i + ", Error:" + percentFormat .format(network.getError(inputs.length)) );
    	 }

    	 System.out.println("Output:");


    	   double out[] = network.calculateOutput(writen);
    	   System.out.println("alpha="+out[0] +"\nbeta=" + out[1] + "\ngamma=" + out[2] +"\ndelta=" + out[3] + "\nepsilon="+ out[4]);
    	     	 
    	 drawing = new Drawing();
    	 repaint();
     }
   }
   
 
   private void addInTxt(List<Double> dots) throws IOException {
	   String str=dots.toString();
	   str = str.replaceAll(",", "");
	   str = str.replace("[", "").replace("]", "");
	   List<String> lines = Arrays.asList(str);
	   
	   Path file = Paths.get("data.txt");
	   //Files.write(file, lines, Charset.forName("UTF-8"));
	   Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
	
}

private List<Double> computeRepresentativeDots(List<Double> xListDoubleTransformed, List<Double> yListDoubleTransformed) {
	   List<Double> tempDots= new ArrayList<Double>();
	   double len=0.0, help=0.0, step, delta;
	   for(int i=0; i<(xListDoubleTransformed.size()-1);i++){
			len+=Math.sqrt( ( Math.pow((xListDoubleTransformed.get(i)-xListDoubleTransformed.get(i+1)),2) + Math.pow( (yListDoubleTransformed.get(i) - yListDoubleTransformed.get(i+1)), 2)));
		}
	   step=len/(M-1);
	   int k=1;
	   tempDots.add(xListDoubleTransformed.get(0));
	   tempDots.add(yListDoubleTransformed.get(0));
	   for(int i=0; i<(xListDoubleTransformed.size()-1);i++){
		   delta=Math.sqrt( ( Math.pow((xListDoubleTransformed.get(i)-xListDoubleTransformed.get(i+1)),2) + Math.pow( (yListDoubleTransformed.get(i) - yListDoubleTransformed.get(i+1)), 2)));
		   help+=delta;
		   if((help>=(k*step)) && ((help-delta)<(k*step)) && (k<M)){
			   k++;
			   tempDots.add(xListDoubleTransformed.get(i));
			   tempDots.add(yListDoubleTransformed.get(i));
		   }
		}
	// TODO Auto-generated method stub
	return tempDots;
}

private List<List<Double>> transformCordinates(List<Integer> xList, List<Integer> yList) {
	   ////System.out.println(xList.size());
	   double dotX, dotY, max=0.0;
	   List<Double> xListDouble=new ArrayList<Double>();
	   List<Double> yListDouble=new ArrayList<Double>();
	   List<List<Double>> returning=new ArrayList<List<Double>>();
	   dotX = average(xList);
	   dotY = average(yList);
	   for(int i=0; i<xList.size();i++){
			xListDouble.add(xList.get(i)-dotX);
			yListDouble.add(yList.get(i)-dotY);
		}
	   for(int i=0; i<xList.size();i++){
		   if(Math.abs(xListDouble.get(i))>max)max=Math.abs(xListDouble.get(i));
		   if(Math.abs(yListDouble.get(i))>max)max=Math.abs(yListDouble.get(i));
		}
	   for(int i=0; i<xList.size();i++){
		   xListDouble.set(i, xListDouble.get(i)/max);
		   yListDouble.set(i, yListDouble.get(i)/max);
		}
	   returning.add(xListDouble);
	   returning.add(yListDouble);
	   return returning;
   }

	private double average(List<Integer> xList) {
		double sum=0.0;
		for(int i=0; i<xList.size();i++){
			sum = sum + xList.get(i);
		}
		return (sum/xList.size());
	}

	//main method
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         // Run the GUI codes on the Event-Dispatching thread for thread safety
         @Override
         public void run() {
            new Main(); // Let the constructor do the job
         }
      });
   }
}
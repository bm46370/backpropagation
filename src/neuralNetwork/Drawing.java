package neuralNetwork;

import java.awt.Graphics;
import java.util.*;
/*
 * The Drawing class model a line made up of many points
 */
public class Drawing {
   public List<Integer> xList;  // List of x-coord
   public List<Integer> yList;  // List of y-coord
   public List<Double> xListDoubleTransformed;  // List of x-coord
   public List<Double> yListDoubleTransformed;  // List of y-coord
 
   // Constructor
   public Drawing() {
      xList = new ArrayList<Integer>();
      yList = new ArrayList<Integer>();
      xListDoubleTransformed = new ArrayList<Double>();
      yListDoubleTransformed = new ArrayList<Double>();
   }
   
   public void setDouble(List<List<Double>> xxx) {
	      xListDoubleTransformed=xxx.get(0);
	      yListDoubleTransformed=xxx.get(1);
   }
 
   // Add a point to this Drawing
   public void addPoint(int x, int y) {
      xList.add(x);
      //System.out.println(x);
      yList.add(y);
      //System.out.println(y);
   }
 
   // This PolyLine paints itself given the Graphics context
   public void draw(Graphics g) { // draw itself
      for (int i = 0; i < xList.size() - 1; ++i) {
         g.drawLine((int)xList.get(i), (int)yList.get(i), (int)xList.get(i + 1), (int)yList.get(i + 1));
      }
   }
}

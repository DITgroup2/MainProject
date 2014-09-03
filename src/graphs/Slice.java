package graphs;

import java.awt.Color;

import javax.swing.JFrame;

public class Slice {
	double value;
	Color color;
	public Slice(double value, Color color) {  
		this.value = value;
		this.color = color;
	}
	 public static void main(String[] argv) {
	      JFrame frame = new JFrame();
	      frame.getContentPane().add(new MyComponent());
	      frame.setSize(300, 200);
	      frame.setVisible(true);
	   }
}

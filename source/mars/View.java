package mars;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel{
	JFrame frame;
	
	ArrayList<GMapElement> gMapElements;
	
	public View(){
		init();
	}
	
	public void init(){
		frame = new JFrame("Proto");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);
	}
	
	public class GMapElement{
		
		MapElement mapElement;
		int x;
		int y;
		
		
		public GMapElement(MapElement mapElement){
			this.mapElement = mapElement;
		}
		
		public void draw(Graphics g) {
			
		}
	}
	
	public class GGoo implements Drawable{
		
		@Override
		public void draw() {
		}
	}
	
	public class GPlayer implements Drawable{
		
		@Override
		public void draw() {
		}
	}
	
	public class GMicroMachine implements Drawable{
		
		@Override
		public void draw() {
		}
	}
	
	public class GOil implements Drawable{
		
		@Override
		public void draw() {
		}
	}
}
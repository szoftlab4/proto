package mars;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel{
	JFrame frame;
	
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
		g.setColor(Color.black);
		g.fillRect(0, 0, 1000, 1000);
	}
	
	public class GMapElement implements Drawable{
		ArrayList<MapElement> list;
		
		@Override
		public void draw() {
		}
	}
	
	public class GGoo implements Drawable{		
		ArrayList<Goo> list;
		
		@Override
		public void draw() {
		}
	}
	
	public class GPlayer implements Drawable{
		ArrayList<Player> list;
		
		@Override
		public void draw() {
		}
	}
	
	public class GMicroMachine implements Drawable{
		ArrayList<MicroMachine> list;
		
		@Override
		public void draw() {
		}
	}
	
	public class GOil implements Drawable{
		ArrayList<Oil> list;
		
		@Override
		public void draw() {
		}
	}
}
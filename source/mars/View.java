package mars;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel{
	JFrame frame;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	public void drawAll(){
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
package mars;

import java.awt.Graphics;

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
		@Override
		public void draw() {
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
	
	public class GRobot implements Drawable{		
		@Override
		public void draw() {
		}
	}
	
	public class GOil implements Drawable{		
		@Override
		public void draw() {
		}
	}
	
	public class GDummyElement implements Drawable{		
		@Override
		public void draw() {
		}
	}
}
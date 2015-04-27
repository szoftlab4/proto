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
		
		
		gMapElements = new ArrayList<GMapElement>();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);
		
		for(GMapElement gme : gMapElements){
			gme.draw(g);
		}
	}
	
	public void initMap(ArrayList<MapElement> map){
		for(MapElement me : map){
			Position pos = me.getPos();
			if(pos.getX() != -1 && pos.getY() != -1){
				GMapElement gme = new GMapElement();
				gme.addMapElementRef(me);
				gMapElements.add(gme);
			}
		}
	}
	
	public class GMapElement{
		
		MapElement mapElement;
		int x;
		int y;
		
		
		public GMapElement(){
			
		}
		
		public void addMapElementRef(MapElement mapElement){
			this.mapElement = mapElement;
			x = mapElement.getPos().getX();
			y = mapElement.getPos().getY();
		}
		
		public void draw(Graphics g) {
			g.setColor(Color.blue);
			g.drawRect(x*150, y*150, 150, 150);
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
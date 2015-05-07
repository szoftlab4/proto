package mars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel{
	JFrame frame;
	
	ArrayList<GCell> gMapElements;
	ArrayList<GPlayer> gPlayers;
	ArrayList<GMicroMachine> gRobot;
	
	
	
	public View(){
		init();
		// Billentyuk miatt kell
		setFocusable(true); 
	}
	
	public void init(){
		frame = new JFrame("Proto");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		
		
		gMapElements = new ArrayList<GCell>();
		gPlayers = new ArrayList<GPlayer>();
		gRobot = new ArrayList<GMicroMachine>();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);
		
		for(GCell gme : gMapElements){
			try {
				gme.draw(g);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(GPlayer gpe : gPlayers){
			try {
				gpe.draw(g);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(GMicroMachine mm : gRobot){
			try {
				mm.draw(g);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void initMap(ArrayList<MapElement> map){
		for(MapElement me : map){
			Position pos = me.getPos();
			if(pos.getX() != -1 && pos.getY() != -1){
				GCell gme = new GCell();
				gme.addMapElementRef(me);
				gMapElements.add(gme);
			}
		}
	}
	public void initPlayers(ArrayList<Player> players){
		for(Player p : players){
			GPlayer gpe = new GPlayer();
			gpe.addPlayerRef(p);
			gPlayers.add(gpe);
		}
	}

	public void addMachine(MicroMachine mm){
		GMicroMachine gmm = new GMicroMachine();
		gmm.addMMRef(mm);
		gRobot.add(gmm);
	}
	
	public class GCell{
		
		MapElement mapElement;
		int x;
		int y;
		
		
		public GCell(){
			
		}
		
		public void addMapElementRef(MapElement mapElement){
			this.mapElement = mapElement;
			x = mapElement.getPos().getX();
			y = mapElement.getPos().getY();
		}
		
		private void drawSpot(Graphics g) throws IOException{
			if(mapElement.hasSpot()){
				Spot s = mapElement.getSpot();
				if(s instanceof Goo){
					
					BufferedImage image = ImageIO.read(new FileInputStream("res/Goo.png"));
					g.drawImage(image, x*150, y*150, 150, 150, null);
					
				}
				else{
					BufferedImage image = ImageIO.read(new FileInputStream("res/Oil.png"));
					g.drawImage(image, x*150, y*150, 150, 150, null);
				}
			}
		}
		
		public void draw(Graphics g) throws IOException {

			BufferedImage image = ImageIO.read(new FileInputStream("res/Lava Cracks.png"));
			g.drawImage(image, x*150, y*150, 150, 150, null);
			
			drawSpot(g);
		}
	}
	
	
	public class GPlayer{
		private Player player;
		int x;
		int y;
		
		public void addPlayerRef(Player p){
			player = p;
		}
		
		public void draw(Graphics g) throws FileNotFoundException, IOException {
			if(player.isAlive()){
				Position pos = player.getPosition();
				x = pos.getX();
				y = pos.getY();
				
				BufferedImage image = ImageIO.read(new FileInputStream("res/Eve.png"));
				g.drawImage(image, x*150, y*150, 150, 150, null);
			}
		}
	}
	
	public class GMicroMachine{
		private MicroMachine robot;
		int x;
		int y;
		
		public void addMMRef(MicroMachine mm){
			robot = mm;
		}
		
		public void draw(Graphics g) throws FileNotFoundException, IOException {
			if(robot.isAlive()){
				Position pos = robot.getPosition();
				x = pos.getX();
				y = pos.getY();
			
				BufferedImage image = ImageIO.read(new FileInputStream("res/MicroMachine.png"));
				g.drawImage(image, x*150, y*150, 150, 150, null);
			}
		}
	}
	

}
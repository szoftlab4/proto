package mars;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class View extends JPanel{
	JFrame frame;
	
	ArrayList<GCell> gMapElements;
	ArrayList<GPlayer> gPlayers;
	ArrayList<GMicroMachine> gRobot;
	
	public View(){
		init();
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
	
	public void drawMenu(){
		JPanel panel = new JPanel();
		panel.setBackground(new Color(194,194,194));
		frame.setContentPane(panel);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

	    c.fill = GridBagConstraints.VERTICAL;
	    c.ipadx = 300;
	    c.ipady = 25;
	    c.weightx = 0.5;
	    c.gridx = 0;
	    c.insets = new Insets(100,0,100,0);

		JButton newgamebtn = new JButton("New Game");
		newgamebtn.setBorder(new LineBorder(Color.BLACK, 3));
		newgamebtn.setBackground(Color.WHITE);
		newgamebtn.setFont(new Font("Arial", Font.PLAIN, 26));
	    c.gridy = 1;
	    newgamebtn.addActionListener(new ActionListener() {
	    	 
            public void actionPerformed(ActionEvent e)
            {
                drawNewGameMenu();
            }
        });      
		panel.add(newgamebtn, c);
		
		JButton highscorebtn = new JButton("Highscores");
		highscorebtn.setBorder(new LineBorder(Color.BLACK, 3));
		highscorebtn.setBackground(Color.WHITE);
		highscorebtn.setFont(new Font("Arial", Font.PLAIN, 26));
		c.gridy = 2;
		highscorebtn.addActionListener(new ActionListener() {
	    	 
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("A KURVA ANYÁD");
            }
        });  
		panel.add(highscorebtn, c);
		
		JButton exitbtn = new JButton("Exit");
		exitbtn.setBorder(new LineBorder(Color.BLACK, 3));
		exitbtn.setBackground(Color.WHITE);
		exitbtn.setFont(new Font("Arial", Font.PLAIN, 26));
		c.gridy = 3;
		exitbtn.addActionListener(new ActionListener() {
	    	 
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });  
		panel.add(exitbtn, c);
		
		frame.setVisible(true);
		
	}
	
	private void drawNewGameMenu(){
		JPanel panel = new JPanel();
		panel.setBackground(new Color(194,194,194));
		frame.setContentPane(panel);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.VERTICAL;
		
		JLabel label1 = new JLabel("New Game");
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		label1.setFont(new Font("Arial", Font.PLAIN, 36));
		
		panel.add(label1);
		
		
		
		frame.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);
		
		for(GCell gme : gMapElements){
			gme.draw(g);
		}
		for(GPlayer gpe : gPlayers){
			gpe.draw(g);
		}
		for(GMicroMachine mm : gRobot){
			mm.draw(g);
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
		
		private void drawSpot(Graphics g){
			if(mapElement.hasSpot()){
				Spot s = mapElement.getSpot();
				if(s instanceof Goo){
					g.setColor(Color.GREEN);
				}
				else{
					g.setColor(Color.ORANGE);
				}
				
				g.fillOval(x*150+25, y*150+25, 100, 100);
			}
		}
		
		public void draw(Graphics g) {
			g.setColor(Color.blue);
			g.drawRect(x*150, y*150, 150, 150);
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
		
		public void draw(Graphics g) {
			if(player.isAlive()){
			Position pos = player.getPosition();
			x = pos.getX();
			y = pos.getY();
			g.setColor(Color.RED);
			g.fillRect(x*150+37, y*150+37, 75, 75);
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
		
		public void draw(Graphics g) {
			if(robot.isAlive()){
				Position pos = robot.getPosition();
				x = pos.getX();
				y = pos.getY();
			g.setColor(Color.CYAN);
			g.fillRect(x*150+35, y*150+35, 50, 50);
			}
		}
	}
	

}
package mars;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class View extends JPanel{
	JFrame frame;
	
	ArrayList<GCell> gMapElements;
	ArrayList<GPlayer> gPlayers;
	ArrayList<GMicroMachine> gRobot;
	Controller controller;
	HashMap<ImageType,BufferedImage> imgMap;
	
	
	
	public View(){
		init();
		// Billentyuk miatt kell
		setFocusable(true); 
	}
	
	public void init(){
		imgMap = new HashMap<ImageType,BufferedImage>();
		frame = new JFrame("Proto");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		
		
		gMapElements = new ArrayList<GCell>();
		gPlayers = new ArrayList<GPlayer>();
		gRobot = new ArrayList<GMicroMachine>();
		
		try {
			loadImages();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}		
	}
	
	public void addController(Controller c){
		controller = c;
	}
	
	private void loadImages() throws FileNotFoundException, IOException {
		BufferedImage img;
		img = ImageIO.read(new FileInputStream("res/Goo.png"));
		imgMap.put(ImageType.GOO, img);
		img = ImageIO.read(new FileInputStream("res/Oil.png"));
		imgMap.put(ImageType.OIL, img);
		img = ImageIO.read(new FileInputStream("res/Lava Cracks.png"));
		imgMap.put(ImageType.LAVA_CRACKS, img);
		img = ImageIO.read(new FileInputStream("res/Eve.png"));
		imgMap.put(ImageType.EVE, img);
		img = ImageIO.read(new FileInputStream("res/MicroMachine.png"));
		imgMap.put(ImageType.MICRO_MACHINE, img);
		img = ImageIO.read(new FileInputStream("res/MS Icon.png"));
		imgMap.put(ImageType.MS_ICON, img);
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
                //TODO EREDMÉNYTÁBLA
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
	    c.gridx = 1;
	    c.weightx = 0.5;
		c.insets = new Insets(0, 0, 100, 0);
		
	    JLabel label1 = new JLabel("New Game");
		label1.setFont(new Font("Arial", Font.PLAIN, 48));
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridy = 0;
		panel.add(label1,c);
		

		c.insets = new Insets(0, 0, 50, 0);
		JLabel label2 = new JLabel("Player Count:");
		label2.setFont(new Font("Arial", Font.PLAIN, 36));
		c.gridy = 1;
		c.gridx = 0;
		panel.add(label2,c);
		
		final JRadioButton rbtn1 = new JRadioButton("2");
		rbtn1.setFont(new Font("Arial", Font.PLAIN, 36));
		rbtn1.setBackground(new Color(194,194,194));
		c.gridy = 2;
		c.gridx = 0;
		rbtn1.doClick();
		panel.add(rbtn1,c);
		
		final JRadioButton rbtn2 = new JRadioButton("3");
		rbtn2.setFont(new Font("Arial", Font.PLAIN, 36));
		rbtn2.setBackground(new Color(194,194,194));
		c.gridy = 2;
		c.gridx = 1;
		panel.add(rbtn2,c);

		ButtonGroup group = new ButtonGroup();
		group.add(rbtn1);
		group.add(rbtn2);
		
		JLabel label3 = new JLabel("Map Select:");
		label3.setFont(new Font("Arial", Font.PLAIN, 36));
		c.gridy = 3;
		c.gridx = 0;
		panel.add(label3,c);

		JButton map1 = new JButton("");
		map1.setBorder(new LineBorder(Color.BLACK, 3));
		map1.setIcon(new ImageIcon(imgMap.get(ImageType.MS_ICON)));
		c.gridy = 4;
		c.gridx = 0;
		map1.addActionListener(new ActionListener() {
	    	 
            public void actionPerformed(ActionEvent e)
            {
            	if(rbtn1.isSelected()){
            		controller.startGame(2, "Test1.map");	//elso terkep
            	}else if(rbtn2.isSelected()){
            		controller.startGame(3, "Test1.map");
            	}
            }
        });  
		panel.add(map1, c);

		c.ipady = 175;
		c.ipadx = 250;
		JButton map2 = new JButton("");
		map2.setBorder(new LineBorder(Color.BLACK, 3));
		map2.setBackground(Color.white);
		c.gridy = 4;
		c.gridx = 1;
		map2.addActionListener(new ActionListener() {
	    	 
            public void actionPerformed(ActionEvent e)
            {
            	if(rbtn1.isSelected()){
            		controller.startGame(2, "Test1.map");	//terkep 2-vel majd...
            	}else if(rbtn2.isSelected()){
            		controller.startGame(3, "Test1.map");
            	}
            }
        });  
		panel.add(map2, c);
		
		JButton map3 = new JButton("");
		map3.setBorder(new LineBorder(Color.BLACK, 3));
		map3.setBackground(Color.white);
		c.gridy = 4;
		c.gridx = 2;
		map3.addActionListener(new ActionListener() {
	    	 
            public void actionPerformed(ActionEvent e)
            {
            	if(rbtn1.isSelected()){
            		controller.startGame(2, "Test1.map");	//terkep 3-mal majd...
            	}else if(rbtn2.isSelected()){
            		controller.startGame(3, "Test1.map");
            	}
            }
        });  
		panel.add(map3, c);
		
		frame.setVisible(true);
	}
	public void drawGame(){
		frame.setContentPane(this);
		this.grabFocus();
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
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
					
					g.drawImage(imgMap.get(ImageType.GOO), x*150, y*150, 150, 150, null);
					
				}
				else{
					g.drawImage(imgMap.get(ImageType.OIL), x*150, y*150, 150, 150, null);
				}
			}
		}
		
		public void draw(Graphics g) throws IOException {

			g.drawImage(imgMap.get(ImageType.LAVA_CRACKS), x*150, y*150, 150, 150, null);
			
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
				
				g.drawImage(imgMap.get(ImageType.EVE), x*150, y*150, 150, 150, null);
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
			
				g.drawImage(imgMap.get(ImageType.MICRO_MACHINE), x*150, y*150, 150, 150, null);
			}
		}
	}
	

}
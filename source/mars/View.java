package mars;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class View extends JPanel{
	JFrame frame;
	
	ArrayList<GCell> gMapElements;
	ArrayList<GPlayer> gPlayers;
	ArrayList<GMicroMachine> gRobot;
	Controller controller;
	HashMap<ImageType,BufferedImage> imgMap;
	
	JRadioButton rbtn1;
	JRadioButton rbtn2;

	private final static int cellSize = 100;

	public View(){
		init();
		// Billentyuk miatt kell
		setFocusable(true); 
	}
	
	public void init(){
		imgMap = new HashMap<ImageType,BufferedImage>();
		frame = new JFrame("’R‹LTROBOTAN¡LSZEX W¡OW¡¡¡¡¡¡¡");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1506, 1029);
		frame.setResizable(false);
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
		img = ImageIO.read(new FileInputStream("res/Eve_Gold.png"));
		imgMap.put(ImageType.EVE_GOLD, img);
		img = ImageIO.read(new FileInputStream("res/Eve_Blue.png"));
		imgMap.put(ImageType.EVE_BLUE, img);
		img = ImageIO.read(new FileInputStream("res/MicroMachine.png"));
		imgMap.put(ImageType.MICRO_MACHINE, img);
		img = ImageIO.read(new FileInputStream("res/MS_Icon_1.png"));
		imgMap.put(ImageType.MS_ICON_1, img);
		img = ImageIO.read(new FileInputStream("res/MS_Icon_2.png"));
		imgMap.put(ImageType.MS_ICON_2, img);
		img = ImageIO.read(new FileInputStream("res/Background_lava2.png"));
		imgMap.put(ImageType.BACKGROUND, img);
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
		newgamebtn.setName("menu_ng_btn");
		newgamebtn.setBorder(new LineBorder(Color.BLACK, 3));
		newgamebtn.setBackground(Color.WHITE);
		newgamebtn.setFont(new Font("Arial", Font.PLAIN, 26));
	    c.gridy = 1;
	    newgamebtn.addActionListener(controller);
		panel.add(newgamebtn, c);
		
		JButton highscorebtn = new JButton("Highscores");
		highscorebtn.setName("menu_hs_btn");
		highscorebtn.setBorder(new LineBorder(Color.BLACK, 3));
		highscorebtn.setBackground(Color.WHITE);
		highscorebtn.setFont(new Font("Arial", Font.PLAIN, 26));
		c.gridy = 2;
		highscorebtn.addActionListener(controller);
		panel.add(highscorebtn, c);
		
		JButton exitbtn = new JButton("Exit");
		exitbtn.setName("menu_ex_btn");
		exitbtn.setBorder(new LineBorder(Color.BLACK, 3));
		exitbtn.setBackground(Color.WHITE);
		exitbtn.setFont(new Font("Arial", Font.PLAIN, 26));
		c.gridy = 3;
		exitbtn.addActionListener(controller);
		panel.add(exitbtn, c);
		
		frame.setVisible(true);
	}
	
	public void drawNewGameMenu(){
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
		
		rbtn1 = new JRadioButton("2");
		rbtn1.setFont(new Font("Arial", Font.PLAIN, 36));
		rbtn1.setBackground(new Color(194,194,194));
		c.gridy = 2;
		c.gridx = 0;
		rbtn1.doClick();
		panel.add(rbtn1,c);
		
		rbtn2 = new JRadioButton("3");
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
		map1.setName("ng_ms_1");
		map1.setBorder(new LineBorder(Color.BLACK, 3));
		map1.setIcon(new ImageIcon(imgMap.get(ImageType.MS_ICON_1)));
		c.gridy = 4;
		c.gridx = 0;
		map1.addActionListener(controller);
		panel.add(map1, c);

		JButton map2 = new JButton("");
		map2.setName("ng_ms_2");
		map2.setBorder(new LineBorder(Color.BLACK, 3));
		map2.setIcon(new ImageIcon(imgMap.get(ImageType.MS_ICON_2)));
		map2.setBackground(Color.white);
		c.gridy = 4;
		c.gridx = 1;

		map2.addActionListener(controller);
		panel.add(map2, c);

		c.ipady = 175;
		c.ipadx = 250;
		JButton map3 = new JButton("");
		map3.setName("ng_ms_3");
		map3.setBorder(new LineBorder(Color.BLACK, 3));
		map3.setBackground(Color.white);
		c.gridy = 4;
		c.gridx = 2;
		
		map3.addActionListener(controller);
		panel.add(map3, c);
		
		frame.setVisible(true);
	}
	
	public void drawHighscoreMenu(){
		JDialog dialog = new JDialog(frame, "Highscores");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JPanel panel_1 = new JPanel(new GridLayout(11,3));
		panel_1.setBackground(new Color(194,194,194));
		
		JPanel panel_2 = new JPanel(new FlowLayout());
		panel_2.setBackground(new Color(194,194,194));
		
		JButton clear = new JButton("Clear");
		clear.setName("hs_clr_btn");
		clear.setBackground(Color.white);
		clear.addActionListener(controller);
		panel_2.add(clear);
		JButton exit = new JButton("Exit");
		exit.setName("hs_ex_btn");
		exit.setBackground(Color.white);
		exit.addActionListener(controller);
		panel_2.add(exit);

		JLabel hlabel1 = new JLabel("Rank");
		hlabel1.setFont(new Font("Arial", Font.BOLD, 18));
		panel_1.add(hlabel1);
		JLabel hlabel2 = new JLabel("Name");
		hlabel2.setFont(new Font("Arial", Font.BOLD, 18));
		panel_1.add(hlabel2);
		JLabel hlabel3 = new JLabel("Distance");
		hlabel3.setFont(new Font("Arial", Font.BOLD, 18));
		panel_1.add(hlabel3);
		
		for(int i = 0; i < 10; i++){
			if(i < Highscore.list.size()){
				JLabel label1 = new JLabel(Integer.toString(i+1));
				label1.setFont(new Font("Arial", Font.PLAIN, 18));
				panel_1.add(label1);
				JLabel label2 = new JLabel(Highscore.list.get(i).getName());
				label2.setFont(new Font("Arial", Font.PLAIN, 18));
				panel_1.add(label2);
				JLabel label3 = new JLabel(Integer.toString(Highscore.list.get(i).getDistance()));
				label3.setFont(new Font("Arial", Font.PLAIN, 18));
				panel_1.add(label3);
			}
			else{
				panel_1.add(new JLabel("-"));
				panel_1.add(new JLabel("-"));
				panel_1.add(new JLabel("-"));
			}
		}

		panel.add(panel_1);
		panel.add(panel_2);
		
		dialog.add(panel);
		
		dialog.pack();
		dialog.setSize(450,320);
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
	}
	
	public void drawNameDialog(final int distance){
		JDialog dialog = new JDialog(frame, "New Highscore");

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.VERTICAL;
		panel_1.setBackground(new Color(194,194,194));
		
		JLabel label = new JLabel("Your Name: ");
		label.setFont(new Font("Arial", Font.PLAIN, 18));
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 10, 230);
		panel_1.add(label, c);
		
		final JTextField textfield = new JTextField(30);
		c.gridy = 1;
		c.insets = new Insets(0, 30, 10, 30);
		panel_1.add(textfield, c);
		
		JPanel panel_2 = new JPanel(new FlowLayout());
		panel_2.setBackground(new Color(194,194,194));
		
		JButton ok = new JButton("Ok");
		ok.setName("nd_ok_btn");
		ok.setBackground(Color.white);
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.highscore.add(textfield.getText(),distance);
				Window w = SwingUtilities.getWindowAncestor((Component) e.getSource());
				w.dispose();
				drawHighscoreMenu();
			}
		});
		panel_2.add(ok);
		
		panel.add(panel_1);
		panel.add(panel_2);
		
		dialog.add(panel);
		
		dialog.pack();
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
	}
	
	public void drawGame(){
		frame.setContentPane(this);
		this.grabFocus();
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.setColor(Color.black);
		//g.fillRect(0, 0, 1000, 1000);
		g.drawImage(imgMap.get(ImageType.BACKGROUND), 0,0,1500,1000, null);
		//g.translate(100,250);
		for(GCell gme : gMapElements){
			try {
				gme.draw(g);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for(GPlayer gpe : gPlayers){
			try {
				gpe.draw(g);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for(GMicroMachine mm : gRobot){
			try {
				mm.draw(g);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
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
		int i=1;
		for(Player p : players){
			GPlayer gpe = new GPlayer();
			gpe.addPlayerRef(p);
			gPlayers.add(gpe);
			
			if(i == 1){
				gpe.addImage(ImageType.EVE);
			}
			else if( i == 2){
				gpe.addImage(ImageType.EVE_GOLD);
			}
			else
				gpe.addImage(ImageType.EVE_BLUE);
			
			i++;
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
			if(mapElement.hasSpot())			
				g.drawImage(imgMap.get(mapElement.getSpot().getType()), x*cellSize, y*cellSize, cellSize, cellSize, null);	
		}
		
		public void draw(Graphics g) throws IOException {

			g.drawImage(imgMap.get(ImageType.LAVA_CRACKS), x*cellSize, y*cellSize, cellSize, cellSize, null);
			
			drawSpot(g);
		}
	}
	
	public class GPlayer{
		private Player player;
		private ImageType img;
		
		public void addPlayerRef(Player p){
			player = p;
		}
		
		public void addImage(ImageType imgType){
			img = imgType;
		}
		
		public void draw(Graphics g) throws FileNotFoundException, IOException {
			if(player.isAlive()){
				Position pos = player.getPosition();
				
				g.drawImage(imgMap.get(img), pos.getX()*cellSize, pos.getY()*cellSize, cellSize,cellSize, null);
			}
		}
	}
	
	public class GMicroMachine{
		private MicroMachine robot;
		
		public void addMMRef(MicroMachine mm){
			robot = mm;
		}
		
		public void draw(Graphics g) throws FileNotFoundException, IOException {
			if(robot.isAlive()){
				Position pos = robot.getPosition();
			
				g.drawImage(imgMap.get(ImageType.MICRO_MACHINE), pos.getX()*cellSize, pos.getY()*cellSize, cellSize,cellSize, null);
			}
		}
	}
	

}
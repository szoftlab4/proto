package mars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Controller implements KeyListener, ActionListener{

	View view;
	Game game;
	int playerCount;
	boolean p2enabled = false;
	boolean p3enabled = false;
	
	public Controller(){
		view = new View();
		view.addKeyListener(this);

	}
	
	private void init(){
		//*****Elrakom, hatha kell meg kesobb
		playerCount = game.getPlayerCount();
		
		if(playerCount <= 2)
			p2enabled = true;
		else if(playerCount == 3)
			p2enabled = true;
		else
			System.err.println("HIBA, tobb mint 3 jatekos");
	
	}
	
	public void drawGame(){
		view.repaint();
	}
	
	public void drawMenu(){
		view.drawMenu();
	}
	
	public void addMap(ArrayList<MapElement> map){
		view.initMap(map);
	}
	public void addPlayers(ArrayList<Player> players){
		view.initPlayers(players);
	}
	public void addMachine(MicroMachine mm){
		view.addMachine(mm);
	}
	
	public void addGameReference(Game gRef){
		this.game = gRef;
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		int keycode = arg0.getKeyCode();
		switch(keycode){
			case KeyEvent.VK_UP:
				break;
			case KeyEvent.VK_DOWN:
				break;
			case KeyEvent.VK_LEFT:
				break;
			case KeyEvent.VK_RIGHT:
				break;
				
			case KeyEvent.VK_W:
				if(p2enabled){
					
				}
				break;
			case KeyEvent.VK_A:
				if(p2enabled){
					
				}
				break;
			case KeyEvent.VK_S:
				if(p2enabled){
					
				}
				break;
			case KeyEvent.VK_D:
				if(p2enabled){
					
				}
				break;
				
			case KeyEvent.VK_U:
				if(p3enabled){
					
				}
				break;
			case KeyEvent.VK_H:
				if(p3enabled){
					
				}
				break;
			case KeyEvent.VK_J:
				if(p3enabled){
					
				}
				break;
			case KeyEvent.VK_K:
				if(p3enabled){
					
				}
				break;
			default:
				break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

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
	
	Player p1;
	Player p2;
	Player p3;
	
	public Controller(){
		view = new View();
		view.addKeyListener(this);

	}
	
	private void init(){
		//*****Elrakom, hatha kell meg kesobb
		playerCount = game.getPlayerCount();
		
		p1 = game.getPlayers().get(0);
		
		if(playerCount == 2){
			p2enabled = true;
			p2 = game.getPlayers().get(1);
		}
		else if(playerCount == 3){
			p2enabled = true;
			p3 = game.getPlayers().get(2);
		}
		else if(playerCount > 3)
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
		//System.out.println("KEYDOWN");
		switch(keycode){
			case KeyEvent.VK_UP:
				p1.command(Direction.FORWARD);
				break;
			case KeyEvent.VK_DOWN:
				p1.command(Direction.BACKWARD);
				break;
			case KeyEvent.VK_LEFT:
				p1.command(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				p1.command(Direction.RIGHT);
				break;
			case KeyEvent.VK_O:
				//TODO KICSIT FURA EZ IGY MOST
				p1.setSpotCommand(SpotCommand.GOO);
				break;
			case KeyEvent.VK_P:
				//TODO NYILVAN EZ IS
				p1.setSpotCommand(SpotCommand.OIL);
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

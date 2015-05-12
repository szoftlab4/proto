package mars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;

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
	
	public void startGame(int number,String mapName){
		game = new Game(number,mapName);
		game.addController(this);
		init();
		view.drawGame();
		game.start();
		
		
	}
	public void addView(View view){
		this.view=view;
		view.addKeyListener(this);
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String str = ((AbstractButton) arg0.getSource()).getName();
		
		if(str.equals("menu_ng_btn")){
			view.drawNewGameMenu();
		}
		else if(str.equals("menu_hs_btn")){
			view.drawHighscoreMenu();
		}
		else if(str.equals("menu_ex_btn")){
			Main.highscore.serialize();
			System.exit(0);
		}
		else if(str.equals("ng_ms_1")){
			if(view.rbtn1.isSelected()){
        		startGame(2, "Test1.map");
        	}else if(view.rbtn2.isSelected()){
        		startGame(3, "Test1.map");
        	}
		}
		else if(str.equals("")){
		}
		else if(str.equals("")){
		}
		else if(str.equals("")){
		}
		else if(str.equals("")){
		}
		else if(str.equals("")){
		}
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
				
			/* PLAYER 2 */
			case KeyEvent.VK_W:
				if(p2enabled){
					p2.command(Direction.FORWARD);
				}
				break;
			case KeyEvent.VK_A:
				if(p2enabled){
					p2.command(Direction.LEFT);
				}
				break;
			case KeyEvent.VK_S:
				if(p2enabled){
					p2.command(Direction.BACKWARD);
				}
				break;
			case KeyEvent.VK_D:
				if(p2enabled){
					p2.command(Direction.RIGHT);
				}
				break;
				
			case KeyEvent.VK_Q:
				if(p2enabled){
					p2.setSpotCommand(SpotCommand.GOO);
				}
				break;
			case KeyEvent.VK_E:
				if(p2enabled){
					p2.setSpotCommand(SpotCommand.OIL);
				}
				break;
			
			/* PLAYER 3 */
			case KeyEvent.VK_U:
				if(p3enabled){
					p3.command(Direction.FORWARD);
				}
				break;
			case KeyEvent.VK_H:
				if(p3enabled){
					p3.command(Direction.LEFT);
				}
				break;
			case KeyEvent.VK_J:
				if(p3enabled){
					p3.command(Direction.BACKWARD);
				}
				break;
			case KeyEvent.VK_K:
				if(p3enabled){
					p3.command(Direction.RIGHT);
				}
				break;
			case KeyEvent.VK_Z:
				if(p3enabled){
					p3.setSpotCommand(SpotCommand.GOO);
				}
				break;
			case KeyEvent.VK_I:
				if(p3enabled){
					p3.setSpotCommand(SpotCommand.OIL);
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

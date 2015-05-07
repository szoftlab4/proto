package mars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Controller implements KeyListener, ActionListener{

	View view;
	
	public Controller(){
		view = new View();
		view.addKeyListener(this);
	}
	
	public void drawGame(){
		view.repaint();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int keycode = arg0.getKeyCode();
		switch(keycode){
			case KeyEvent.VK_UP:
				System.out.println("up");
				break;
			case KeyEvent.VK_DOWN:
				System.out.println("down");
				break;
			case KeyEvent.VK_LEFT:
				System.out.println("left");
				break;
			case KeyEvent.VK_RIGHT:
				System.out.println("right");
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

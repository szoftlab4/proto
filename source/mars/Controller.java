package mars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Controller implements MouseListener, ActionListener{

	View view;
	
	public Controller(){
		view = new View();
		view.addKeyListener(this);
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
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
<<<<<<< HEAD
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
=======
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
				break;
			case KeyEvent.VK_A:
				break;
			case KeyEvent.VK_S:
				break;
			case KeyEvent.VK_D:
				break;
				
			case KeyEvent.VK_U:
				break;
			case KeyEvent.VK_H:
				break;
			case KeyEvent.VK_J:
				break;
			case KeyEvent.VK_K:
				break;
			default:
				break;
		}
>>>>>>> origin/master
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

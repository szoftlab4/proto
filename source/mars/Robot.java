package mars;

import java.util.Observable;
import java.util.Observer;

public class Robot extends Observable implements Observer {
	private boolean alive;
	private Direction dir;
	private Position pos;
	private HeadDirection headDir;

	public Direction getDirection() {
		return dir;
		 
	}

	public Position getPosition() {
		return pos;
		
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void reset() {
		
	}

	public void setAlive(boolean value) {
		
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
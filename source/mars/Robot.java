package mars;

import java.util.Observable;
import java.util.Observer;

/**
 * 
 *          NAGYJÁBÓL KÉSZEN VAN....................................................!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 */

public class Robot extends Observable implements Observer {
	private boolean alive;
	protected Position pos;
	protected HeadDirection headDir;	// koordináta-rendszerhez képest
	protected Direction dir;			// játékoshoz képest
	
	public Direction getDirection() {
		return dir;
	}
	
	public HeadDirection getHeadDirection() {
		return headDir;
	}
	
	public Position getPosition() {
		return pos;
		
	}

	public boolean isAlive() {
		return this.alive;
	}

	// TODO
	public void reset() {
		alive = true;
	}

	public void setAlive(boolean value) {
		this.alive = value;
	}

	@Override
	public void update(Observable arg0, Object arg1) { }
	
	protected HeadDirection convertDir() {
		if(dir == Direction.STAY)
			return headDir;
		else if(headDir == HeadDirection.LEFT){
			if(dir == Direction.LEFT)
				return HeadDirection.DOWN;
			else if(dir == Direction.RIGHT)
				return HeadDirection.UP;
			else if(dir == Direction.FORWARD)
				return HeadDirection.LEFT;
			else
				return HeadDirection.RIGHT;
		}
		else if(headDir == HeadDirection.UP){
			if(dir == Direction.LEFT)
				return HeadDirection.LEFT;
			else if(dir == Direction.RIGHT)
				return HeadDirection.RIGHT;
			else if(dir == Direction.FORWARD)
				return HeadDirection.UP;
			else
				return HeadDirection.DOWN;
		}
		else if(headDir == HeadDirection.RIGHT){
			if(dir == Direction.LEFT)
				return HeadDirection.UP;
			else if(dir == Direction.RIGHT)
				return HeadDirection.DOWN;
			else if(dir == Direction.FORWARD)
				return HeadDirection.RIGHT;
			else
				return HeadDirection.LEFT;
		}
		else {
			if(dir == Direction.LEFT)
				return HeadDirection.RIGHT;
			else if(dir == Direction.RIGHT)
				return HeadDirection.LEFT;
			else if(dir == Direction.FORWARD)
				return HeadDirection.DOWN;
			else
				return HeadDirection.UP;
		}
	}
}
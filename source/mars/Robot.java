package mars;

import java.util.Observable;
import java.util.Observer;

/**
 * Absztrakt õsosztálya a játékos- és kisrobotoknak. Közös tulajdonságaikat valósítja meg.
 */
public class Robot extends Observable implements Observer {
	private boolean alive;
	protected Position pos;
	protected HeadDirection headDir;	// koordináta-rendszerhez képest
	protected Direction dir;			// játékoshoz képest

	/**
	 * Visszaadja, hogy él-e még a robot.
	 */
	public boolean isAlive() {
		return this.alive;
	}

	/**
	 * Alapértelmezett értékre állítja az állapotot.
	 */
	public void reset() {
		alive = true;
	}

	/**
	 * Beállítja a játékos állapotát a paraméterben megadott értékkel.
	 */
	public void setAlive(boolean value) {
		this.alive = value;
	}

	/**
	 * A gyerekosztályokban felül lesz definiálva.
	 */
	@Override
	public void update(Observable arg0, Object arg1) { }
	
	/**
	 * Konvertáljuk a irányt a koordinátarendszerhez és a játékoshoz képest. A headDir jelöli,
	 * hogy melyik irányba halad a pályán koordináta-szemszögbõl (up, down, left, right), 
	 * a dir pedig azt, hogy ehhez képest milyen irányban fordult el (left, right), így a 
	 * függvény visszaadja az új headDir értékét.
	 */
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
	
	/**
	 * Getterek, setterek.
	 */
	public Direction getDirection() {
		return dir;
	}
	
	public HeadDirection getHeadDirection() {
		return headDir;
	}
	
	public void setHeadDirection(HeadDirection hd) {
		headDir = hd;
	}
	
	public Position getPosition() {
		return pos;
	}

}
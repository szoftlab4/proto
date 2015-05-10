package mars;

import java.util.Observable;
import java.util.Observer;

/**
 * Absztrakt ososztalya a jatekos- es kisrobotoknak. Kozos tulajdonsagaikat valositja meg.
 */
public class Robot extends Observable implements Observer {
	private boolean alive;
	protected Position pos;
	protected HeadDirection headDir;	// koordinata-rendszerhez kepest
	protected Direction dir;			// jatekoshoz kepest

	/**
	 * Visszaadja, hogy el-e meg a robot.
	 */
	public boolean isAlive() {
		return this.alive;
	}

	/**
	 * Alapertelmezett ertekre allija az allapotot.
	 */
	public void reset() {
		alive = true;
	}

	/**
	 * Beallitja a jatekos allapotat a parameterben megadott ertekkel.
	 */
	public void setAlive(boolean value) {
		this.alive = value;
	}

	/**
	 * A gyerekosztolyokban felul lesz definialva.
	 */
	@Override
	public void update(Observable arg0, Object arg1) { }
	
	/**
	 * Konvertaljuk a iranyt a koordinatarendszerhez es a jatekoshoz kepest. A headDir jeloli,
	 * hogy melyik iranyba halad a palyan koordinata-szemszogbol (up, down, left, right), 
	 * a dir pedig azt, hogy ehhez kepest milyen iranyban fordult el (left, right), igy a 
	 * fuggveny visszaadja az uj headDir erteket.
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
package mars;

import java.util.Observable;
import java.util.Observer;

/**
 * Absztrakt �soszt�lya a j�t�kos- �s kisrobotoknak. K�z�s tulajdons�gaikat val�s�tja meg.
 */
public class Robot extends Observable implements Observer {
	private boolean alive;
	protected Position pos;
	protected HeadDirection headDir;	// koordin�ta-rendszerhez k�pest
	protected Direction dir;			// j�t�koshoz k�pest

	/**
	 * Visszaadja, hogy �l-e m�g a robot.
	 */
	public boolean isAlive() {
		return this.alive;
	}

	/**
	 * Alap�rtelmezett �rt�kre �ll�tja az �llapotot.
	 */
	public void reset() {
		alive = true;
	}

	/**
	 * Be�ll�tja a j�t�kos �llapot�t a param�terben megadott �rt�kkel.
	 */
	public void setAlive(boolean value) {
		this.alive = value;
	}

	/**
	 * A gyerekoszt�lyokban fel�l lesz defini�lva.
	 */
	@Override
	public void update(Observable arg0, Object arg1) { }
	
	/**
	 * Konvert�ljuk a ir�nyt a koordin�tarendszerhez �s a j�t�koshoz k�pest. A headDir jel�li,
	 * hogy melyik ir�nyba halad a p�ly�n koordin�ta-szemsz�gb�l (up, down, left, right), 
	 * a dir pedig azt, hogy ehhez k�pest milyen ir�nyban fordult el (left, right), �gy a 
	 * f�ggv�ny visszaadja az �j headDir �rt�k�t.
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
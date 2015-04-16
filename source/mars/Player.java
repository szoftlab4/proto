package mars;

/**
 * 
 *          NAGYJ�B�L K�SZEN VAN....................................................!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *			4 TODO van h�tra
 */

public class Player extends Robot {
	private int distance;
	private boolean oilFlag;
	private int speed;
	private int spotCount;
	private Position nextPos;
	private Direction nextDir; // el kell int�zni.... 
	
	public final static int MAX_SPOT = 5;
	public final static int START_SPEED = 1;
	
	public Player(Position pos, HeadDirection headDir) {
		this.pos = pos;
		this.headDir = headDir;
		speed = START_SPEED;
		distance = 0;
		oilFlag = false;
		spotCount = MAX_SPOT;
		setAlive(true);
		dir = Direction.FORWARD;
	}
	
	public int getDistance() {
		return this.distance;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setOilFlag(boolean value) {
		this.oilFlag = value;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Position getNextPos() {
		return this.nextPos;
	}

	// TODO position
	public void reset() {
		speed = START_SPEED;
		distance = 0;
		oilFlag = false;
		spotCount = MAX_SPOT;
		setAlive(true);
		dir = Direction.FORWARD;
		
	}

	// TODO
	public void update(Object observable, Object object) {
		
	}
	
	// TODO
	private void step() {
		
	}
	
	private void setNewDir() {
		if(nextDir == Direction.RIGHT || nextDir == Direction.LEFT)
			this.dir = this.nextDir;  // ???????? ezzel is majd lesz egy kis baszakod�s :S
	}
	
	private void calculateNewSpeed() {
		if(!oilFlag){
			if(nextDir == Direction.FORWARD)
				setSpeed(getSpeed() + 1);
			else if(nextDir == Direction.BACKWARD && speed > 1)
				setSpeed(getSpeed() - 1);
		}		
		setOilFlag(false);
	}
	
	private void calculateNewPos() {
		switch (headDir) {
			case UP:
				pos.setPosition(pos.getX(), pos.getY() - getSpeed());
				break;
			case RIGHT:
				pos.setPosition(pos.getX() + getSpeed(), pos.getY());
				break;
			case DOWN:
				pos.setPosition(pos.getX(), pos.getY() + getSpeed());
				break;
			case LEFT:
				pos.setPosition(pos.getX() - getSpeed(), pos.getY());
				break;
			default:
				break;
		}
	}
	
	// TODO
	private void putOilSpot() {
		
	}
	
	// TODO
	private void putGooSpot() {
		
	}
}
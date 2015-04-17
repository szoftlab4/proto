package mars;

/**
 * 
 *          NAGYJÁBÓL KÉSZEN VAN....................................................!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *			4 TODO van hátra
 */

public class Player extends Robot {
	private int distance;
	private boolean oilFlag;
	private int speed;
	private int spotCount;
	private Position nextPos;
	private Direction nextDir; // el kell intézni.... 
	
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
		//itt hívjuk  notifyObservers()/putOilSpot()/putGooSpot()attól függõen hogy mit akar játékos
		if(this.isAlive()){
			this.step();
		}
	}
	
	// TODO
	private void step() {
		this.setNewDir();
		this.calculateNewSpeed();
		this.calculateNewPos();
	}
	
	private void setNewDir() {
		if(nextDir == Direction.RIGHT || nextDir == Direction.LEFT)
			this.dir = this.nextDir;  // ???????? ezzel is majd lesz egy kis baszakodás :S
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
		headDir=this.convertDir();
		switch (headDir) {
			case UP:
				nextPos.setPosition(pos.getX(), pos.getY() - getSpeed());
				break;
			case RIGHT:
				nextPos.setPosition(pos.getX() + getSpeed(), pos.getY());
				break;
			case DOWN:
				nextPos.setPosition(pos.getX(), pos.getY() + getSpeed());
				break;
			case LEFT:
				nextPos.setPosition(pos.getX() - getSpeed(), pos.getY());
				break;
			default:
				break;
		}
	}
	
	private void putOilSpot() {
		this.notifyObservers(new Oil());
	}
	
	private void putGooSpot() {
		this.notifyObservers(new Goo());
	}
}
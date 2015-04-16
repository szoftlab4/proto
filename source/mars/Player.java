package mars;

public class Player extends Robot {
	private int distance;
	private boolean oilFlag;
	private int speed;
	private int spotCount;
	private Position nextPos;
	
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

	public void update(Object observable, Object object) {
		
	}
	
	private void step() {
		
	}
	
	private void setNewDir() {
		
	}
	
	private void calculateNewSpeed() {
		
	}
	
	private void calculateNewPos() {
		
	}
	
	private void putOilSpot() {
		
	}
	
	private void putGooSpot() {
		
	}
}
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
	private SpotCommand spotCommand;
	private String name;
	
	public final static int MAX_SPOT = 5;
	public final static int START_SPEED = 1;
	
	public Player(String name, Position pos, HeadDirection headDir) {
		this.name = name;
		this.pos = pos;
		this.headDir = headDir;
		speed = START_SPEED;
		distance = 0;
		oilFlag = false;
		spotCount = MAX_SPOT;
		setAlive(true);
		dir = Direction.FORWARD;
		spotCommand = SpotCommand.NOSPOT;
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
	
	public void setPosition(Position pos){
		this.pos = pos;
	}
	
	public void setDirection(Direction dir){
		this.dir = dir;
	}

	public void setSpotCommand(SpotCommand sc){
		this.spotCommand = sc;
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
			switch(spotCommand){
				case NOSPOT:
					notifyObservers();
					break;
				case GOO:
					this.putGooSpot();
					break;
				case OIL:
					this.putOilSpot();
					break;
			}
			
			setSpotCommand(SpotCommand.NOSPOT);
			
		}
	}
	
	// TODO
	private void step() {
		//this.setNewDir();
		this.calculateNewSpeed();
		this.calculateNewPos();
		this.dir = Direction.STAY;
	}
	
	private void calculateNewSpeed() {
		if(!oilFlag){
			switch(dir){
				case FORWARD: 
					setSpeed(speed + 1);
					break;
				case BACKWARD:
					if(speed > 1)
						setSpeed(speed - 1);
					break;
				default:
					break;	
			}
		}		
		setOilFlag(false);
	}
	
	private void calculateNewPos() {
		headDir=this.convertDir();
		switch (headDir) {
			case UP:
				nextPos.setPosition(pos.getX(), pos.getY() - speed);
				break;
			case RIGHT:
				nextPos.setPosition(pos.getX() + speed, pos.getY());
				break;
			case DOWN:
				nextPos.setPosition(pos.getX(), pos.getY() + speed);
				break;
			case LEFT:
				nextPos.setPosition(pos.getX() - speed, pos.getY());
				break;
			default:
				break;
		}
	}
	
	private void putOilSpot() {
		if(spotCount > 0){
			spotCount--;
			this.notifyObservers(new Oil());
		}
		else
			this.notifyObservers();
		
	}
	
	private void putGooSpot() {
		if(spotCount > 0){
		spotCount--;
		this.notifyObservers(new Goo());
		}
		this.notifyObservers();
	}
}
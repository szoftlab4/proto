package mars;

public class MicroMachine extends Robot {
	private boolean doneCleaning;
	private int progress;
	private int index;
	private static int cntr = 0;
	
	public MicroMachine(Position pos,HeadDirection headDir){
		doneCleaning=false;
		progress=0;
		this.setAlive(true);
		this.pos=pos;
		this.headDir=headDir;
		dir = Direction.FORWARD;
		cntr++;
		index = cntr;
	}

	public void update(Object Obs, Object Obj) {
		if(dir == Direction.STAY){
			this.checkProgress();
		}
		else
			this.step();
		
	}
	
	public void testupdate() {
		if(dir == Direction.STAY){
			this.checkProgress();
		}
		else
			this.step();
		
	}
	
	public void reset() {
		doneCleaning = false;
		progress = 0;
		dir = Direction.FORWARD;
		
	}

	public Oil getOilSpot() {
		return new Oil();
	}
	
	public HeadDirection getHeadDir(){
		return headDir;
	}

	public void setDirection(Direction D) {
		dir = D;
	}

	public boolean isDoneCleaning() {
		return this.doneCleaning;
	}
	
	public void invertDir(){
		switch(dir){
		case LEFT:
			dir=Direction.RIGHT;
			break;
		case RIGHT:
			dir=Direction.LEFT;
			break;
		case FORWARD:
			dir=Direction.BACKWARD;
			break;
		case BACKWARD:
			dir=Direction.FORWARD;
			break;
		case STAY:
			dir=Direction.STAY;
			break;
		default:
			break;
		} 
	}
	
	//ez private
	public void step(){
		headDir=this.convertDir();
		switch(headDir){
		case UP:
			pos.setPosition(this.getPosition().getX(), this.getPosition().getY()-1);
			break;
		case DOWN:
			pos.setPosition(this.getPosition().getX(), this.getPosition().getY()+1);
			break;
		case LEFT:
			pos.setPosition(this.getPosition().getX()-1, this.getPosition().getY());
			break;
		case RIGHT:
			pos.setPosition(this.getPosition().getX()+1, this.getPosition().getY());
			break;
		default:
			break;
		}
	} 
	
	private void checkProgress(){
		progress++;
		if(progress == 2){
			doneCleaning = true;
			progress = 0;
		}
	}
	
	public int getIndex(){
		return index;
	}
}
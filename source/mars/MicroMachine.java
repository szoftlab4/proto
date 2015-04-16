package mars;

public class MicroMachine extends Robot {
	private boolean doneCleaning;
	private int progress;
	
	public MicroMachine(Position pos,HeadDirection headDir){
		doneCleaning=false;
		progress=0;
		this.setAlive(true);
		this.pos=pos;
		this.headDir=headDir;
		dir = Direction.FORWARD;
		
	}

	public void update(Object Obs, Object Obj) {
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

	public void setDirection(Direction D) {
		dir = D;
	}

	public boolean isDoneCleaning() {
		return this.doneCleaning;
	}
	private void step(){
		switch(this.convertDir()){
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
		}
	} 
	
	private void checkProgress(){
		progress++;
		if(progress == 2){
			doneCleaning = true;
			progress = 0;
		}
	}
}
package mars;

/**
 * Pályán lévõ foltok takarításáért felelõs kisrobot. Létrehozza az olajfoltot, ami majd halálakor a helyére kerül.
 */
public class MicroMachine extends Robot {
	/**
	 * takaritas befejeyesenek jelzese
	 */
	private boolean doneCleaning;
	/**
	 * takaritas allapota
	 */
	private int progress;
	/**
	 * Micromachine ID szama
	 */
	private int index;
	/**
	 * kovetkezo MicroMachine ID szama
	 */
	private static int cntr = 0;
	/**
	 * Micromachine utkozesenek syamon tartasa
	 */
	private boolean collided;
	/**
	 * MicroMachine konstruktora
	 * @param pos
	 * @param headDir
	 */
	public MicroMachine(Position pos,HeadDirection headDir){
		doneCleaning=false;
		progress=0;
		this.setAlive(true);
		this.pos=pos;
		this.headDir=headDir;
		dir = Direction.FORWARD;
		cntr++;
		index = cntr;
		collided=false;
	}
	/**
	 * cntr szamlalo settere,a MicroMachine ID szamainak
	 * @param i
	 */
	public static void setCntr(int i){
		cntr = i;
	}
	/**
	 * Ha a kisrobot STAY parancsot kapott akkor helyben takarit,egyebkent lep
	 * @param Obs
	 * @param Obj
	 */
	public void update(Object Obs, Object Obj) {
		if(dir == Direction.STAY){
			this.checkProgress();
		}
		else
			this.step();
		
	}
	/**
	 * update metodus tesztelesi celokra publikusan duplikalva
	 */
	public void testupdate() {
		if(dir == Direction.STAY){
			this.checkProgress();
			this.setCollided(false);
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
		if(dir==Direction.STAY && doneCleaning == false){
			dir=Direction.STAY;
		}
		else
			dir=Direction.BACKWARD;

	}
	
	//ez private
	public void step(){
		this.setCollided(false);

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

	public boolean isCollided() {
		return collided;
	}

	public void setCollided(boolean collided) {
		this.collided = collided;
	}
}
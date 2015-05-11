package mars;

import java.util.Observable;

/**
 * Palyan levo foltok takaritasaert felelos kisrobot. Letrehozza az olajfoltot, ami majd halalakor a helyere kerul.
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
	 * Micromachine utkozesenek szamon tartasa
	 */
	private boolean collided;
	/**
	 * A MicroMachine regi pozicioja elmentve ha invertaljuk az iranyt nem kell szamolni az utkozes poziciojat
	 */
	private Position oldPos;
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
		this.oldPos = new Position(pos.getX(),pos.getY());
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
	public void update(Observable Obs, Object Obj) {
		if(dir == Direction.STAY){
			this.checkProgress();
			this.setCollided(false);
		}
		else
			this.step();
		
	}
	
	/**
	 * update metodus tesztelesi celokra publikusan duplikalva
	 */
	/*
	public void testupdate() {
		if(dir == Direction.STAY){
			this.checkProgress();
			this.setCollided(false);
		}
		else
			this.step();
		
	}
	*/
	
	/**
	 * nem hasznalt fuggveny
	 */
	public void reset() {
		doneCleaning = false;
		progress = 0;
		dir = Direction.FORWARD;
		collided=false;
		
	}
	/**
	 * olajfolt letrehozasa es visszaadasa
	 * @return new Oil()
	 */
	public Oil getOilSpot() {
		return new Oil();
	}
	/**
	 * HeadDir getter fuggveny
	 * @return headDir
	 */
	public HeadDirection getHeadDir(){
		return headDir;
	}
	/**
	 * visszater a MicroMachine old poziciojaval
	 * @return oldpos
	 */
	public Position getOldPos(){
		return oldPos;
	}
	/**
	 * Direction setter fuggveny
	 * @param D
	 */
	public void setDirection(Direction D) {
		dir = D;
	}
	/**
	 * doneCleaning boolean lekerdezese
	 * @return doneCleaning
	 */
	public boolean isDoneCleaning() {
		return this.doneCleaning;
	}
	/**
	 * Micromachine iranyanak meginvertalasa
	 */
	public void invertDir(){
		if(dir==Direction.STAY && doneCleaning == false){
			dir=Direction.STAY;
		}
		else
			dir=Direction.BACKWARD;

	}
	/**
	 * MicroMachine leptetese,kiszamoljuk milyen iranyba kell mennunk a dir parancs alapjan es atallitjuk a pozicionkat,az utkozest jelzo flaget visszaallitjuk
	 */
	private void step(){
		this.setCollided(false);
		oldPos.setPosition(pos.getX(),pos.getY());
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
	/**
	 * a takaritas allapotanak ellenorzese,es lekezelese ha kesz(jelzese)
	 */
	private void checkProgress(){
		progress++;
		if(progress == 3){
			doneCleaning = true;
			progress = 0;
		}
	}
	/**
	 * ID getter
	 * @return index
	 */
	public int getIndex(){
		return index;
	}
	/**
	 * Collided boolean ellenorzese
	 * @return collided
	 */
	public boolean isCollided() {
		return collided;
	}
	/**
	 * collided setter
	 * @param collided
	 */
	public void setCollided(boolean collided) {
		this.collided = collided;
	}
}
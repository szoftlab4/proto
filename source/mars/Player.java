package mars;

import java.util.Observable;

/**
 * A játékosok adatait és állapotát tárolja. Kiszámolja az új pozíciót a sebessége és
 * az iránya alapján(ugrás). A MapHandlerrel a notifyObservers metódusán keresztül
 * kommunikál(a Player referencia átadása a MapHandler update függvényének, illetve
 * folt átadása ha foltot akar a játékos letenni).
 */
public class Player extends Robot {
	private int distance;
	private boolean oilFlag;
	private int speed;
	
	/**
	 * A jatekos maradek leteheto foltjainak 
	 */
	private int spotCount;
	
	/**
	 * A jatekosok lepes utani uj pozicioja
	 */
	private Position nextPos;
	
	/**
	 * Enum ami jelzi akar-e foltot letenni a jatekos.
	 */
	private SpotCommand spotCommand;
	private String name;
	
	/** 
	 * A jatekosok altal leteheto foltok maximalis szama.
	 */
	public final static int MAX_SPOT = 5;
	
	/**
	 * Jatekosok kezdo sebessege.
	 */
	public final static int START_SPEED = 1;
	
	/**
	 * Inicializálás.
	 */
	public Player(String name, Position pos, HeadDirection headDir) {
		this.name = name;
		this.pos = pos;
		this.headDir = headDir;
		speed = START_SPEED;
		distance = 0;
		oilFlag = false;
		spotCount = MAX_SPOT;
		setAlive(true);
		dir = Direction.STAY;
		spotCommand = SpotCommand.NOSPOT;
		nextPos = new Position(0, 0);
	}

	/**
	 * Nem használjuk a prototipusban.
	 */
	public void reset() {
		speed = START_SPEED;
		distance = 0;
		oilFlag = false;
		spotCount = MAX_SPOT;
		setAlive(true);
		dir = Direction.FORWARD;
		
	}
	
	/**
	 * teszt metodus a jatekos leptetesere
	 */
	public void testStep(){
		//this.step();
		//this.calculateNewPos();
		//this.pos = this.nextPos;
		//A parametereket ugy se hasznaljuk es ez elvileg a timertol jonne
		//Nem kene kulon olajat lerakni, hanem a SpotCommandot beallitani es ezt a fuggvenyt hivni
		//Ez majd leptetni fogja a jatekost es lerakja a spotot ha kell
		//update(null,null);
		testUpdate();
	}
	
	/**
	 * Az update metodus timer nelkuli tesztelesere
	 * Ellenorizzik hogy el-e a jatekos lepes elott
	 * A spotCommand valtozonk alapjan tudjuk hogy a jatekos lepeskor akart-e foltot letenni
	 * Ha nem akkor csak lep,ha akart Goo-t vagy oil-t tenni akkor aszerint hivjuk a folt lerako fuggvenyeket 
	 */
	private void testUpdate(){
		if(this.isAlive()){
			this.step();
			switch(spotCommand){
				case NOSPOT:
					setChanged();
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
	
	/**
	 * Ellenorizzik hogy el-e a jatekos lepes elott
	 * A spotCommand valtozonk alapjan tudjuk hogy a jatekos lepeskor akart-e foltot letenni
	 * Ha nem akkor csak lep,ha akart Goo-t vagy oil-t tenni akkor aszerint hivjuk a folt lerako fuggvenyeket
	 * spotCommand alaphelyzetbe allitasa (NOSPOT)
	 * @param observable
	 * @param object
	 */
	public void update(Observable observable, Object object) {
		
		if(this.isAlive()){
			this.step();
			switch(spotCommand){
				case NOSPOT:
					setChanged();
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
	
	/**
	 * Játékos léptetésének a lebonyolítása, megfelelõ függvénnyek meghívása
	 */
	private void step() {
		
		this.calculateNewSpeed();
		this.distance+=speed;
		this.calculateNewPos();
		this.dir = Direction.STAY;
	}
	
	/**
	 * A játékos inputja alapján új sebesség kiszámítása,oilFlag ellenõrzése 
	 * (ha az oilFlag aktív akkor a sebesség módosítás nem lehetséges ebben a
	 *  körben,visszabillentjük false értékre).
	 */
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
	
	/**
	 * Felul kell irni a Robot implementaciojat mivel BACKWARD-ra nem kell csinalni semmit
	 */
	@Override
	protected HeadDirection convertDir(){
		//Ha BACKWARD akkor nem csinalunk semmit
		if(dir == Direction.BACKWARD)
			return headDir;
		
		//Egyebkent pedig jo az ososztaly implementacio
		return super.convertDir();
	}
	
	/**
	 *  A sebesség és irány függvényében(az alkalmazott irány számításáról lásd 
	 *  bõvebben a Robot õsosztályt) kiszámoljuk a nextPos értékét(az ugrás végpontja).
	 */
	private void calculateNewPos() {
		headDir = this.convertDir();
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
	
	/**
	 * Lerak egy olaj foltot a jelenlegi pozícióra.
	 */
	public void putOilSpot() {
		setChanged();
		if(spotCount > 0){
			spotCount--;
			this.notifyObservers(new Oil());
		}
		else
			this.notifyObservers();
		
	}
	
	/**
	 * Lerak egy ragacs foltot a jelenlegi pozícióra.
	 */
	public void putGooSpot() {
		setChanged();
		if(spotCount > 0){
			spotCount--;
			this.notifyObservers(new Goo());
		}
		else
			this.notifyObservers();
	}
	
	/**
	 * Visszaadja az oilFlag értékét. 
	 */
	public boolean getOilFlag(){
		return oilFlag;
	}
	
	/**
	 * Lerakható foltok számával tér vissza.
	 * @return
	 */
	public int getSpotCount(){
		return spotCount;
	}
	
	/**
	 * Name getter fuggveny
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Distance getter fuggveny
	 * @return distance
	 */
	public int getDistance() {
		return this.distance;
	}
	
	/**
	 * Speed getter fuggveny
	 * @return speed
	 */
	public int getSpeed() {
		return this.speed;
	}
	
	/**
	 * Oilflag setter fuggveny
	 * @param value
	 */
	public void setOilFlag(boolean value) {
		this.oilFlag = value;
	}
	
	/**
	 * Speed setter fuggveny
	 * @param speed
	 */
	public void setSpeed(int speed) {
		if(!oilFlag)
			this.speed = speed;
	}
	
	/**
	 * Position setter fuggveny (jelenlegi position)
	 * @param pos
	 */
	public void setPosition(Position pos){
		this.pos = pos;
	}
	
	/**
	 * Direction setter fuggveny
	 * @param dir
	 */
	public void setDirection(Direction dir){
		this.dir = dir;
	}
	
	/**
	 * Spotcommand setter fuggveny
	 * @param sc
	 */
	public void setSpotCommand(SpotCommand sc){
		this.spotCommand = sc;
	}
	
	/**
	 * Nextpos getter fuggveny(lepes utani position) 
	 * @return
	 */
	public Position getNextPos() {

		
		return this.nextPos;
	}
	
	/*************************************************************
	 * IRANYITASHOZ KAPCSOLODO METHODUSOK
	 ************************************************************/
	
	
	public void command(Direction dir){
		//Igy most nem lehet egyszerre iranyt es sebesseget valtoztatni
		this.dir = dir;
	}
}
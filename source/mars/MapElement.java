package mars;

import java.util.ArrayList;

/**
 * Tárolja a saját pozícióját és az esetlegesen rajta lévõ foltot. Képes új folt hozzáadására/törlésére és
 *  a benne lévõ folt valamint a MapHandler közötti interakcióra. Tárolja az odalépett játékosok és kisrobotok 
 *  referenciáját, valamint ezek alapján ütközteti õket. Ellenõrzi, hogy kell-e törölni a rajta foltot.
 */
public class MapElement {
	private Position pos;
	private Spot spot;
	private ArrayList<Player> refPlayer;
	private ArrayList<MicroMachine> refMM;
	
	/**
	 * Inicializálás.
	 * @param pos: új mapElement pozíciója
	 * @param spot: új mapElementen lévõ spot
	 */
	public MapElement(Position pos, Spot spot){
		this.pos = pos;
		this.spot = spot;
		refPlayer = new ArrayList<Player>();
		refMM = new ArrayList<MicroMachine>();
	}

	/**
	 * Új folt hozzáadása a pályaelemhez.
	 * @param spot: új folt
	 */
	public void addSpot(Spot spot) {
		this.spot = spot;
	}
	
	/*
	 * Ideiglenesen eltárolja a játékos referenciáját, hogy az ütközésnél
	 *  ellenõrizni tudja, hogy állnak-e rajta játékosok.
	 */
	public void addPlayerRef(Player p){
		refPlayer.add(p);
	}
	
	/**
	 * Ideiglenesen eltárolja a kisrobot referenciáját, hogy az ütközésnél
	 *  ellenõrizni tudja, hogy állnak-e rajta kisrobotok.
	 */
	public void addMMRef(MicroMachine mm){
		refMM.add(mm);
	}

	/**
	 * Ellenõrzi, hogy van-e a pályaelemen folt és ha van akkor meghívja a
	 *  folt handlePlayer metódusát.
	 */
	public void handle(Player player) {
		if(hasSpot()){
			spot.handlePlayer(player);
		}
		addPlayerRef(player);
	}

	/**
	 *  Megnézi, hogy kell-e törölni a rajta lévõ foltot és ha kell, akkor törli.
	 */
	public void checkSpot() {
		if(spot != null && spot.isDeletable()){
			deleteSpot();
		}
	}

	/**
	 * Törli a rajta lévõ foltot.
	 */
	public void deleteSpot() {
		spot = null;
	}
	
	/**
	 * Van-e folt, játékos, vagy kisrobot az elemen.
	 */
	public boolean isFree(){
		return (refPlayer.isEmpty() && refMM.isEmpty() && spot==null);
	}
	
	/**
	 * A játékosok ütközésének lekezelése.
	 */
	private void collidePlayers(){
		System.out.println("collide players");
		int maxspeed = 0;
		double avg = 0;
		int x = 0;
		int y = 0;
		boolean moreThanOneMax = false;
		for(Player p : refPlayer){
			int temp = p.getSpeed();
			if(temp>maxspeed){
				maxspeed = temp;
				moreThanOneMax = false;
			}
			else if(temp == maxspeed)
				moreThanOneMax = true;
			
			switch(p.headDir){
				case UP:
					y += temp;
					break;
				case DOWN:
					y -= temp;
					break;
				case RIGHT:
					x += temp;
					break;
				case LEFT:
					x -= temp;
					break;
			}
			y = Math.abs(y);
			x = Math.abs(x);
			avg = Math.sqrt(x*x + y*y);
		}
		
		int intavg = (int) avg;
		
		if(intavg < 1)
			intavg = 1;
		for(Player p : refPlayer){
			if(moreThanOneMax){
				p.setAlive(false);
			}
			else{
				if(p.getSpeed() == maxspeed)
					p.setSpeed(intavg);
				else
					p.setAlive(false);
			}
		}
		
		/*
		System.out.println("Maxspeed: " + maxspeed + " ,vertoratlag: " + intavg + " , more than 1 max: " + moreThanOneMax);
		System.out.println("x: " + x + " y: " + y + " utkozott jatekosok szama: " + refPlayer.size());
		*/		
	}
	
	/**
	 * A helyben lévõ ütköztetéseket ellenõrzi, majd ütközteti.
	 */
	public void handleCollision() {
		//Van-e jatekos
		if(!refPlayer.isEmpty()){
			//Ha van akkor mindenkeppen meghalnak a kisrobotok
			for(MicroMachine mm : refMM){
				mm.setAlive(false);
				this.spot=mm.getOilSpot();
			}
			//Utkoztetjuk a jatekosokat, egy jatekosra is mukodik
			collidePlayers();
			//Torolni kell a referenciakat, az else ag miatt
			clearRobotRefs();
		}
		//Nem volt jatekos
		else{
			//Van-e kisrobot
			if(!refMM.isEmpty()){
				//Csak akkor van utkozes ha legalabb 2 kisrobot van
				if(refMM.size()>1)
					//Ha van legalabb 2, akkor mindenkit visszaforditunk (stay eseten se lesz gond)
					for(MicroMachine mm : refMM){
						mm.invertDir();
						mm.setCollided(true);
					}
			}
			//Toroljuk mindenkeppen a referenciakat
			clearRobotRefs();
		}
	}

	/**
	 * Visszaadja, hogy van-e rajta folt.
	 */
	public boolean hasSpot() {
		return spot != null;
		
	}

	/**
	 * Törli az ideiglenes referenciákat az ütközés megvizsgálása után.
	 */
	private void clearRobotRefs(){
		refPlayer.clear();
		refMM.clear();
	}
	
	/**
	 * Visszaadja, hogy a mapElement dummy elem-e (rajta van-e a pályán).
	 */
	public boolean isDummy(){
		return (pos.getX() == -1 && pos.getY() == -1);
	}
	
	/**
	 * Getterek.
	 */
	
	public Spot getSpot(){
		return spot;
	}

	public Position getPos() {
		return pos;
	}
}
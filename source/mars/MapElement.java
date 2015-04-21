package mars;

import java.util.ArrayList;

/**
 * T�rolja a saj�t poz�ci�j�t �s az esetlegesen rajta l�v� foltot. K�pes �j folt hozz�ad�s�ra/t�rl�s�re �s
 *  a benne l�v� folt valamint a MapHandler k�z�tti interakci�ra. T�rolja az odal�pett j�t�kosok �s kisrobotok 
 *  referenci�j�t, valamint ezek alapj�n �tk�zteti �ket. Ellen�rzi, hogy kell-e t�r�lni a rajta foltot.
 */
public class MapElement {
	private Position pos;
	private Spot spot;
	private ArrayList<Player> refPlayer;
	private ArrayList<MicroMachine> refMM;
	
	/**
	 * Inicializ�l�s.
	 * @param pos: �j mapElement poz�ci�ja
	 * @param spot: �j mapElementen l�v� spot
	 */
	public MapElement(Position pos, Spot spot){
		this.pos = pos;
		this.spot = spot;
		refPlayer = new ArrayList<Player>();
		refMM = new ArrayList<MicroMachine>();
	}

	/**
	 * �j folt hozz�ad�sa a p�lyaelemhez.
	 * @param spot: �j folt
	 */
	public void addSpot(Spot spot) {
		this.spot = spot;
	}
	
	/*
	 * Ideiglenesen elt�rolja a j�t�kos referenci�j�t, hogy az �tk�z�sn�l
	 *  ellen�rizni tudja, hogy �llnak-e rajta j�t�kosok.
	 */
	public void addPlayerRef(Player p){
		refPlayer.add(p);
	}
	
	/**
	 * Ideiglenesen elt�rolja a kisrobot referenci�j�t, hogy az �tk�z�sn�l
	 *  ellen�rizni tudja, hogy �llnak-e rajta kisrobotok.
	 */
	public void addMMRef(MicroMachine mm){
		refMM.add(mm);
	}

	/**
	 * Ellen�rzi, hogy van-e a p�lyaelemen folt �s ha van akkor megh�vja a
	 *  folt handlePlayer met�dus�t.
	 */
	public void handle(Player player) {
		if(hasSpot()){
			spot.handlePlayer(player);
		}
		addPlayerRef(player);
	}

	/**
	 *  Megn�zi, hogy kell-e t�r�lni a rajta l�v� foltot �s ha kell, akkor t�rli.
	 */
	public void checkSpot() {
		if(spot != null && spot.isDeletable()){
			deleteSpot();
		}
	}

	/**
	 * T�rli a rajta l�v� foltot.
	 */
	public void deleteSpot() {
		spot = null;
	}
	
	/**
	 * Van-e folt, j�t�kos, vagy kisrobot az elemen.
	 */
	public boolean isFree(){
		return (refPlayer.isEmpty() && refMM.isEmpty() && spot==null);
	}
	
	/**
	 * A j�t�kosok �tk�z�s�nek lekezel�se.
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
	 * A helyben l�v� �tk�ztet�seket ellen�rzi, majd �tk�zteti.
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
	 * T�rli az ideiglenes referenci�kat az �tk�z�s megvizsg�l�sa ut�n.
	 */
	private void clearRobotRefs(){
		refPlayer.clear();
		refMM.clear();
	}
	
	/**
	 * Visszaadja, hogy a mapElement dummy elem-e (rajta van-e a p�ly�n).
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
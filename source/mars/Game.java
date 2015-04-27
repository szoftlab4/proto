package mars;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Létrehozza a játékhoz szükséges objektumokat.
 * Regisztrálja az observereket.
 */
public class Game {
	
	public static Object syncObject = new Object();
	
	private MyTimerTask myTimerTask;
	private ArrayList<Player> players;
	private MapHandler mapHandler;
	private ArrayList<MicroMachine> microMashines;
	private Timer timer;
	@SuppressWarnings("unused")
	private Supervisor supervisor; // A prototípusnak még nincs rá szüksége
	private int playerCount;	
	
	/**
	 * Game konstruktora, inicializál.
	 * @param cnt: A létrehozandó játékosok száma.
	 */
	public Game(int cnt){
		playerCount = cnt;
		mapHandler = new MapHandler();
		players = new ArrayList<Player>();
		microMashines = new ArrayList<MicroMachine>();
		init();
	}

	/**
	 * Objektumok létrehozása és inicializálása.
	 */
	public void init() {
		myTimerTask = new MyTimerTask(1000,20000);
		timer = new Timer();
		
		//Meg ezt meg kell nezni
		timer.schedule(myTimerTask,0);
		
		//Pályabetöltés
		mapHandler.loadMap("Test1.map");
		
		//addPlayers();
		
		//El kell majd inditani
		//supervisor = new Supervisor();
	}
	
	/**
	 * Hozzáadja a paraméterben átadott Playert a players-hez és regisztrálja a timertaskra observerként.
	 * @param player
	 */
	public void addPlayer(Player player){
		players.add(player);
		myTimerTask.registerObserver(player);
		player.addObserver(mapHandler);
	}
	
	/**
	 * Hozzáadja a paraméterben átadott MicroMachinet a micromashines-hez és regisztrálja a timertaskra observerként.
	 * @param mm
	 */
	public void addMicroMachine(MicroMachine mm){
		microMashines.add(mm);
		myTimerTask.registerObserver(mm);
	}
	
	
	/**
	 * Getterek, setterek.
	 */

	public MapHandler getMapHandler(){
		return mapHandler;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public ArrayList<MicroMachine> getMicroMachine(){
		return microMashines;
	}
	
	
	
	
	/**************************************
	 * Nincs használva a prototípusban
	 */
	@SuppressWarnings("unused")
	private void addPlayers(){
		for(int i=0; i<playerCount; i++){
			Position freePos = mapHandler.getAvailablePos();
			HeadDirection dir = mapHandler.getValidHeadDir(freePos);
			Player p = new Player("Player " + (i+1),freePos,dir);
			addPlayer(p);
		}
	}
	
	public class Supervisor  implements Runnable{

		private int mmCreateCounter;
		private final static int mmCreateFreq = 5;
		
		
		public Supervisor(){
			this.mmCreateCounter = 0;
		}
		
		public void checkMachines() {

			for(MicroMachine mm : microMashines){
				if(mm.isDoneCleaning()){
					mapHandler.deleteSpot(mm.pos);
				}
			}
			
			for(MicroMachine mm : microMashines){
				mapHandler.setMMDirection(mm);
			}
		}

		public void checkGameEnd() {
			
			int alivePlayers = 0;
			@SuppressWarnings("unused")
			boolean gameEnd = true;
			for(Player player : players){
				if(player.isAlive()){
					gameEnd = false;
					alivePlayers++;
				}
				mapHandler.setAlivePlayers(alivePlayers);
			}
			if(!myTimerTask.isOver())
				gameEnd = false;
			
			
		}

		private void createMicroMachine(){
			mmCreateCounter++;
			if(mmCreateCounter == mmCreateFreq){
				mmCreateCounter = 0;
				Position pos = mapHandler.getAvailablePos();
				MicroMachine mm = new MicroMachine(pos, HeadDirection.UP);
				microMashines.add(mm);
				myTimerTask.registerObserver(mm);
			}
		}
		
		@Override
		public void run(){
			while(true){
				synchronized (Game.syncObject) {
					try {
						Game.syncObject.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				createMicroMachine();
				mapHandler.startCollisions();
				checkGameEnd();
				checkMachines();
			}
		}
	}
	
	
}
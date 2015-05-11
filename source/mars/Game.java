package mars;

import java.util.ArrayList;
import java.util.Timer;


/**
 * Letrehozza a jatekhoz szukseges objektumokat.
 * Regisztralja az observereket.
 */
public class Game {
	
	public static Object syncObject = new Object();
	
	private MyTimerTask myTimerTask;
	private ArrayList<Player> players;
	private MapHandler mapHandler;
	private ArrayList<MicroMachine> microMachines;
	private Timer timer;
	private Supervisor supervisor;
	private int playerCount;	
	private static final int gameSpeed = 1500;
	private String mapName;
	private Thread superThread;
	private Controller controller;
	
	/**
	 * Game konstruktora, inicializál.
	 * @param cnt: A letrehozando jatekosok szama.
	 */
	public Game(int cnt,String mapName){
		playerCount = cnt;
		this.mapName = mapName;
		mapHandler = new MapHandler();
		players = new ArrayList<Player>();
		microMachines = new ArrayList<MicroMachine>();
		
		init();
	}

	/**
	 * Objektumok letrehozasa es inicializalasa.
	 */
	public void init() {
		myTimerTask = new MyTimerTask(gameSpeed,30000);
		timer = new Timer();
		
		//Meg ezt meg kell nezni
		
		
		//Palyabetoltese
		mapHandler.loadMap(mapName);
		mapHandler.setPlayerCount(playerCount);
		
		addPlayers();
		
		supervisor = new Supervisor();
		
		superThread = new Thread(supervisor);
		
	}
	
	public void start(){
		timer.schedule(myTimerTask,0,gameSpeed);
		
		superThread.start();
	}
	
	/**
	 * Hozzadja a parameterben atadott Playert a players-hez es regisztralja a timertaskra observerket.
	 * @param player
	 */
	public void addPlayer(Player player){
		players.add(player);
		myTimerTask.registerObserver(player);
		player.addObserver(mapHandler);
	}
	
	/**
	 * Hozzadja a parameterben atadott MicroMachinet a micromashines-hez es regisztralja a timertaskra observerket.
	 * @param mm
	 */
	public void addMicroMachine(MicroMachine mm){
		microMachines.add(mm);
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
		return microMachines;
	}
	
	public int getPlayerCount(){
		return playerCount;
	}
	
	public void addController(Controller controller){
		this.controller = controller;
		controller.addMap(mapHandler.getMap());
		controller.addPlayers(players);

	}
	
	
	private void addPlayers(){
		for(int i=0; i<playerCount; i++){
			Position freePos = mapHandler.getAvailablePos();
			HeadDirection dir = mapHandler.getValidHeadDir(freePos);
			Player p = new Player("Player " + (i+1),freePos,dir);
			addPlayer(p);
			
			//TODO: ne lehessen ugyanoda tenni
		}
	}
	
	public class Supervisor  implements Runnable{

		private int mmCreateCounter;
		private int mmCreateFreq = 4;
		
		
		public Supervisor(){
			this.mmCreateCounter = 0;
		}
		
		public void checkMachines() {

			for(MicroMachine mm : microMachines){
				if(mm.isDoneCleaning()){
					mapHandler.deleteSpot(mm.pos);
				}
			}
			
			for(MicroMachine mm : microMachines){
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
				mapHandler.setPlayerCount(alivePlayers);
			}
			if(!myTimerTask.isOver())
				gameEnd = false;
			
			
		}

		private void createMicroMachine(){
			mmCreateCounter++;
			if(mmCreateCounter == mmCreateFreq){
				mmCreateCounter = 0;
				mmCreateFreq++;
				Position pos = mapHandler.getAvailablePos();
				MicroMachine mm = new MicroMachine(pos, HeadDirection.UP);
				microMachines.add(mm);
				myTimerTask.registerObserver(mm);
				controller.addMachine(mm);
			}
		}
		
		@Override
		public void run(){
			while(true){
				/*
				synchronized (Game.syncObject) {
					try {
						Game.syncObject.wait();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				*/
				try {
					Thread.sleep(gameSpeed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("Supervisor felebredt");
				createMicroMachine();
				mapHandler.startCollisions();
				checkGameEnd();
				checkMachines();
				mapHandler.checkSpots();
				controller.drawGame();
			}
		}
	}
	
	
}
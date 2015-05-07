package mars;

import java.util.ArrayList;
import java.util.Timer;

/**
 * L�trehozza a j�t�khoz sz�ks�ges objektumokat.
 * Regisztr�lja az observereket.
 */
public class Game {
	
	public static Object syncObject = new Object();
	
	private MyTimerTask myTimerTask;
	private ArrayList<Player> players;
	private MapHandler mapHandler;
	private ArrayList<MicroMachine> microMachines;
	private Timer timer;
	@SuppressWarnings("unused")
	private Supervisor supervisor; // A protot�pusnak m�g nincs r� sz�ks�ge
	private int playerCount;	
	@SuppressWarnings("unused")
	private Thread superThread;
	private Controller controller;
	
	/**
	 * Game konstruktora, inicializ�l.
	 * @param cnt: A l�trehozand� j�t�kosok sz�ma.
	 */
	public Game(int cnt){
		playerCount = cnt;
		mapHandler = new MapHandler();
		players = new ArrayList<Player>();
		microMachines = new ArrayList<MicroMachine>();
		init();
	}

	/**
	 * Objektumok l�trehoz�sa �s inicializ�l�sa.
	 */
	public void init() {
		myTimerTask = new MyTimerTask(1000,20000);
		timer = new Timer();
		
		//Meg ezt meg kell nezni
		
		
		//P�lyabet�lt�s
		mapHandler.loadMap("Test1.map");
		mapHandler.setPlayerCount(playerCount);
		
		addPlayers();
		
		supervisor = new Supervisor();
		
		superThread = new Thread(supervisor);
		
	}
	
	public void start(){
		timer.schedule(myTimerTask,0,1000);
		
		controller.drawMenu();
		
		superThread.start();
	}
	
	/**
	 * Hozz�adja a param�terben �tadott Playert a players-hez �s regisztr�lja a timertaskra observerk�nt.
	 * @param player
	 */
	public void addPlayer(Player player){
		players.add(player);
		myTimerTask.registerObserver(player);
		player.addObserver(mapHandler);
	}
	
	/**
	 * Hozz�adja a param�terben �tadott MicroMachinet a micromashines-hez �s regisztr�lja a timertaskra observerk�nt.
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
		private final static int mmCreateFreq = 3;
		
		
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
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("Supervisor felebredt");
				//createMicroMachine();
				mapHandler.startCollisions();
				checkGameEnd();
				checkMachines();
				controller.drawGame();
			}
		}
	}
	
	
}
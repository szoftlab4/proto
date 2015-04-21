package mars;

import java.util.ArrayList;
import java.util.Timer;


//KAPJATOK BE

public class Game {
	private MyTimerTask myTimerTask;
	private ArrayList<Player> players;
	private MapHandler mapHandler;
	private ArrayList<MicroMachine> microMashines;
	private Timer timer;
	private Supervisor supervisor;
	private int playerCount;	
	
	public Game(int cnt){
		playerCount = cnt;
		mapHandler = new MapHandler();
		players = new ArrayList<Player>();
		microMashines = new ArrayList<MicroMachine>();
		init();
	}

	public void init() {
		myTimerTask = new MyTimerTask(1000,20000);
		timer = new Timer();
		
		//Meg ezt meg kell nezni
		timer.schedule(myTimerTask,0);
		
		//mapHandler.loadMap("IDE KELL A PALYA ELERESI UTVONALA");
		
		//addPlayers();
		
		//El kell majd inditani
		//supervisor = new Supervisor();
	}
	
	public MapHandler getMapHandler(){
		return mapHandler;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public ArrayList<MicroMachine> getMicroMachine(){
		return microMashines;
	}

	private void addPlayers(){
		for(int i=0; i<playerCount; i++){
			Position freePos = mapHandler.getAvailablePos();
			HeadDirection dir = mapHandler.getValidHeadDir(freePos);
			Player p = new Player("Player " + (i+1),freePos,dir);
			addPlayer(p);
		}
	}
	
	public void reset() {
		
	}
	
	public void addPlayer(Player player){
		players.add(player);
		myTimerTask.registerObserver(player);
		player.addObserver(mapHandler);
	}
	
	public void addMicroMachine(MicroMachine mm){
		microMashines.add(mm);
		myTimerTask.registerObserver(mm);
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
			boolean gameEnd = true;
			for(Player player : players){
				if(player.isAlive())
					gameEnd = false;
			}
			if(!myTimerTask.isOver())
				gameEnd = false;
			
			//TODO Lekezelni a jatek veget
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
			checkGameEnd();
			createMicroMachine();
			mapHandler.startCollisions();
			checkMachines();
		}
	}
	
	
}
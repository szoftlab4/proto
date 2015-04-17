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

	public void init() {
		myTimerTask = new MyTimerTask(1000,20000);
		timer = new Timer();
		//Meg ezt meg kell nezni
		timer.schedule(myTimerTask,0);
		
		mapHandler = new MapHandler();
		mapHandler.loadMap("IDE KELL A PALYA ELERESI UTVONALA");
		
		//El kell majd inditani
		supervisor = new Supervisor();
		
		//Almos vagyok
		
		registerObservers();
	}

	public void reset() {
		
	}
	
	//EZT KIZAROLAG INIT LEFUTASA UTAN LEHET
	public void addPlayer(Player player){
		players.add(player);
		myTimerTask.registerObserver(player);
		player.addObserver(mapHandler);
	}
	
	private void registerObservers(){
		for(Player p : players){
			p.addObserver(mapHandler);
			myTimerTask.registerObserver(p);
		}
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
			checkMachines();
		}
	}
	
	
}
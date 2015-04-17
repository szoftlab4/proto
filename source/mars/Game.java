package mars;

import java.util.ArrayList;
import java.util.Timer;

public class Game {
	private MyTimerTask myTimerTask;
	private ArrayList<Player> players;
	private MapHandler mapHandler;
	private ArrayList<MicroMachine> microMashines;
	private Timer timer;
	private Supervisor supervisor;

	public void init() {
		registerObservers();
	}

	public void reset() {
		
	}
	
	public void addPlayer(Player player){
		players.add(player);
	}
	
	private void registerObservers(){
		for(Player p : players){
			p.addObserver(mapHandler);
			myTimerTask.registerObserver(p);
		}
	}
	
	public class Supervisor  implements Runnable{

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

		
		@Override
		public void run(){
			checkGameEnd();
			checkMachines();
		}
	}
	
	
}
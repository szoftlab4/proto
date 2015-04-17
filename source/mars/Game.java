package mars;

import java.util.ArrayList;
import java.util.Timer;
import java.util.Vector;

public class Game {
	private MyTimerTask myTimerTask;
	private ArrayList<Player> players;
	private MapHandler mapHandler;
	private ArrayList<MicroMachine> microMashines;
	private Timer timer;

	public void init() {
		
	}

	public void reset() {
		
	}
	public class Supervisor  implements Runnable{

		public void checkMachines() {
			for(MicroMachine mm : microMashines){
				mapHandler.setMMDirection(mm);
			}
		}

		public void detectGameEnd() {
			boolean gameEnd = true;
			for(Player player : players){
				if(player.isAlive())
					gameEnd = false;
			}
			if(!myTimerTask.isOver())
				gameEnd = false;
			
			//TODO Lekezelni a jatek veget
		}
		
		private void registerObservers(){
			
		}
		
		@Override
		public void run(){
			detectGameEnd();
			checkMachines();
		}
	}
}
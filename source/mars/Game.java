package mars;

import java.util.ArrayList;
import java.util.Timer;
import java.util.Vector;

public class Game {
	private MyTimerTask myTimerTask;
	private ArrayList<Player> players;
	private MapHandler mapHandler;
	private MicroMachine microMashines;
	private Timer timer;

	public void init() {
		
	}

	public void reset() {
		
	}
	public class Supervisor  extends Thread{

		public void checkMachines() {
			
		}

		public void detectGameEnd() {
			
		}
		
		public void run(){
			//TODO ezt most akkor hogy
		}
	}
}
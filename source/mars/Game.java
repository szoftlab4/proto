package mars;

import java.util.Vector;

public class Game {
	private MyTimerTask _myTimerTask;
	private java.util.Vector<Player> _players;
	private MapHandler _mapHandler;
	private MicroMachine _microMashines;
	private Timer _timer;
	public Vector<MicroMachine> _unnamed_MicroMachine_ = new Vector<MicroMachine>();
	public Timer _unnamed_Timer_;

	public void init() {
		throw new UnsupportedOperationException();
	}

	public void reset() {
		throw new UnsupportedOperationException();
	}
	public class Supervisor {
		public MicroMachine _unnamed_MicroMachine_;

		public void checkMachines() {
			throw new UnsupportedOperationException();
		}

		public void detectGameEnd() {
			throw new UnsupportedOperationException();
		}

		public void run() {
			throw new UnsupportedOperationException();
		}
	}
}
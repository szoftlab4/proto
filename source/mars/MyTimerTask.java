package mars;

import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
	private long gametime;
	private long interval;
	private long timeElapsed;
	private Observable notifier;

	public MyTimerTask(long interval, long gametime) {
		this.interval = interval;
		this.gametime = gametime;
	
		timeElapsed=0;
		notifier=new Observable();
	}
	
	public boolean isOver() {
		if((gametime-timeElapsed)<0)
			return true;
		else 
			return false;
	}

	public void reset() {
		timeElapsed=0;
	}


	public void setGameTime(int gt) {
		gametime=gt;
	}

	public void setInterval(int interval) {
		this.interval=interval;
	}

	public void registerObserver(Observer robot) {
		notifier.addObserver(robot);
	}

	@Override
	public void run() {

		timeElapsed+=interval;
		notifier.notifyObservers();
	}
}
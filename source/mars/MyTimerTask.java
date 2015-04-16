package mars;

import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
	private boolean enabled;
	private int gametime;
	private int interval;
	private int timeElapsed;
	private Observable notifier;

	public boolean isOver() {
		if((gametime-timeElapsed)<0)
			return true;
		else 
			return false;
	}

	public void reset() {
		enabled=true;
		timeElapsed=0;
	}

	public void setEnabled(boolean value) {
		enabled=value;
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
		//timeElapsed++; //Ez még nem igazán biztos
		notifier.notifyAll();
	}
}
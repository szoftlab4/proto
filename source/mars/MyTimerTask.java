package mars;

import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
	private boolean enabled;
	private long gametime;
	private long interval;
	private long timeElapsed;
	private Observable notifier;

	public MyTimerTask(/*int time*/) {
	enabled=true;
	//interval=1; //random number,majd kiderül hogy használjuk amikor odajutunk
	//gametime=time; nem biztos hogy konstruktornál adjuk át?térkép betöltésnél eldõl(vagy default time)
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
		//timeElapsed++; //Ez még nem igazán biztos,long 
		notifier.notifyObservers();
	}
}
package mars;

import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

/**
 * Periodikusan ertestit a robotokat, hogy lepniuk kell.
 *
 */
public class MyTimerTask extends TimerTask {
	/**
	 * A jatek hosszat hatarozza meg.
	 */
	private long gametime;
	/**
	 * Ennyi idokozonkent ertesiti a jatekosokat.
	 */
	private long interval;
	/**
	 * A jatwek inditasa ota eltelt ido.
	 */
	private long timeElapsed;
	/**
	 * Ezen keresztul ertesitjuk a robotokat.
	 */
	private Observable notifier;

	public MyTimerTask(long interval, long gametime) {
		this.interval = interval;
		this.gametime = gametime;
	
		timeElapsed=0;
		notifier=new Observable();
	}
	
	/**
	 * Visszaadja, hogy letelt-e a játékidõ.
	 * @return
	 */
	public boolean isOver() {
		if((gametime-timeElapsed)<0)
			return true;
		else 
			return false;
	}

	/**
	 * Alaphelyzetbe állítja az objektumot
	 */
	public void reset() {
		timeElapsed=0;
	}

	/**
	 * Beállítja a játékidõt.
	 * @param gt
	 */
	public void setGameTime(int gt) {
		gametime=gt;
	}
	
	/**
	 * 	Beállítja az értesítések idõközét.
	 * @param interval
	 */
	public void setInterval(int interval) {
		this.interval=interval;
	}

	/**
	 * Notifier objektumának átadja a regisztálandó Observert.
	 * @param robot
	 */
	public void registerObserver(Observer robot) {
		notifier.addObserver(robot);
	}
	
	/**
	 * A TimeTask oszályból örökölt függvény,itt hívjuk meg a notifier notifyObservers() 
	 * metódusát.(Robotok értesítése az update függvényen keresztül a kör kezdetérõl 
	 */
	@Override
	public void run() {

		timeElapsed+=interval;
		notifier.notifyObservers();
	}
}
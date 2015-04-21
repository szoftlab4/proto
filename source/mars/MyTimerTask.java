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
	 * Visszaadja, hogy letelt-e a j�t�kid�.
	 * @return
	 */
	public boolean isOver() {
		if((gametime-timeElapsed)<0)
			return true;
		else 
			return false;
	}

	/**
	 * Alaphelyzetbe �ll�tja az objektumot
	 */
	public void reset() {
		timeElapsed=0;
	}

	/**
	 * Be�ll�tja a j�t�kid�t.
	 * @param gt
	 */
	public void setGameTime(int gt) {
		gametime=gt;
	}
	
	/**
	 * 	Be�ll�tja az �rtes�t�sek id�k�z�t.
	 * @param interval
	 */
	public void setInterval(int interval) {
		this.interval=interval;
	}

	/**
	 * Notifier objektum�nak �tadja a regiszt�land� Observert.
	 * @param robot
	 */
	public void registerObserver(Observer robot) {
		notifier.addObserver(robot);
	}
	
	/**
	 * A TimeTask osz�lyb�l �r�k�lt f�ggv�ny,itt h�vjuk meg a notifier notifyObservers() 
	 * met�dus�t.(Robotok �rtes�t�se az update f�ggv�nyen kereszt�l a k�r kezdet�r�l 
	 */
	@Override
	public void run() {

		timeElapsed+=interval;
		notifier.notifyObservers();
	}
}
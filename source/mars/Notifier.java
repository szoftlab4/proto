package mars;

import java.util.Observable;

public class Notifier extends Observable{
	public void notifyRobots(){
		setChanged();
		notifyObservers();
	}
}

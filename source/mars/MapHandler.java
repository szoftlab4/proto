package mars;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MapHandler implements Observer {
	private ArrayList<MapElement> _map;
	private ArrayList<Position> _road;

	public void loadMap() {
		throw new UnsupportedOperationException();
	}

	public void reset() {
		throw new UnsupportedOperationException();
	}

	public void checkSpots() {
		throw new UnsupportedOperationException();
	}

	public Direction getMMDirection(Object aMicroMachine) {
		throw new UnsupportedOperationException();
	}

	public void deleteSpot(Object aPos) {
		throw new UnsupportedOperationException();
	}

	public void startCollisions() {
		throw new UnsupportedOperationException();
	}

	public void update(Object aObservable, Object aObject) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
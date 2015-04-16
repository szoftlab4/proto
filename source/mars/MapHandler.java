package mars;

import java.util.Vector;

public class MapHandler implements Observer {
	private java.util.Vector<List<MapElement>> _map;
	private List<Position> _road;
	public Game _unnamed_Game_;
	public Vector<MapElement> _unnamed_MapElement_ = new Vector<MapElement>();

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
}
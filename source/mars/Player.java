package mars;

public class Player extends Robot {
	private int _distance;
	private boolean _oilFlag;
	private int _speed;
	private int _spotCount;
	private Position _nextPos;
	public Game _unnamed_Game_;
	private Position _pos;
	public MyTimerTask _unnamed_MyTimerTask_;

	public int getDistance() {
		return this._distance;
	}

	public int getSpeed() {
		return this._speed;
	}

	public void setOilFlag(boolean aValue) {
		throw new UnsupportedOperationException();
	}

	public void setSpeed(int aSpeed) {
		throw new UnsupportedOperationException();
	}

	public Position getNextPos() {
		return this._nextPos;
	}

	public void reset() {
		throw new UnsupportedOperationException();
	}

	public void update(Object aObservable, Object aObject) {
		throw new UnsupportedOperationException();
	}
}
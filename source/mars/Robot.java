package mars;

import java.util.Observable;
import java.util.Observer;

public class Robot extends Observable implements Observer {
	private boolean _alive;
	private Direction _dir;
	private Position _pos;
	private HeadDirection _headDir;
	public Direction _unnamed_Direction_;
	public HeadDirection _unnamed_HeadDirection_;

	public Direction getDirection() {
		throw new UnsupportedOperationException();
	}

	public Position getPosition() {
		throw new UnsupportedOperationException();
	}

	public boolean isAlive() {
		return this._alive;
	}

	public void reset() {
		throw new UnsupportedOperationException();
	}

	public void setAlive(boolean aValue) {
		throw new UnsupportedOperationException();
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
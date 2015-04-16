package mars;

public class MicroMachine extends Robot {
	private boolean doneCleaning;
	private int progress;

	public void update(Object Obs, Object Obj) {
		
	}

	public void reset() {
		
	}

	public Oil getOilSpot() {
		return new Oil();
	}

	public void setDirection(Direction D) {
		
	}

	public boolean isDoneCleaning() {
		return this.doneCleaning;
	}
}
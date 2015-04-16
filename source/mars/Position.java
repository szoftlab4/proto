package mars;

/**
 * 
 *          NAGYJÁBÓL KÉSZEN VAN....................................................!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 */

public class Position {
	private int x;
	private int y;

	public int getY() {
		return this.y;
	}

	public int getX() {
		return this.x;
	}

	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
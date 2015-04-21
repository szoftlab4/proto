package mars;

/**
 * A koordináták tárolására szolgáló egyszerû osztály.
 */
public class Position {
	private int x;
	private int y;

	/**
	 * Inicializálás.
	 */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getterek, setterek.
	 */
	
	public int getY() {
		return this.y;
	}

	public int getX() {
		return this.x;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
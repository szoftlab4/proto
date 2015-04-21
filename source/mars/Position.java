package mars;

/**
 * A koordin�t�k t�rol�s�ra szolg�l� egyszer� oszt�ly.
 */
public class Position {
	private int x;
	private int y;

	/**
	 * Inicializ�l�s.
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
package mars;

/**
 * A játékos sebességét felezi meg.
 */
public class Goo implements Spot {
	private int durability;
	private static final int maxDurability= 4;
	
	/**
	 * Beállítja a kopás maximális állapotainak számát.
	 */
	public Goo(){
		durability = maxDurability;
	}
	
	/**
	 * A referenciául kapott játékos sebességét megfelez és csökkenti a folt állapotát.
	 */
	public void handlePlayer(Player player) {
		
		durability--;
		
		int speed = player.getSpeed()/2;
		if(speed <= 1)
			speed = 1;
		player.setSpeed(speed);
	}

	/**
	 * Visszaadja, hogy kopott-e a folt.
	 */
	public boolean isDeletable() {
		return durability <= 0;
	}
	public ImageType getType(){
		return ImageType.GOO;
	}
}
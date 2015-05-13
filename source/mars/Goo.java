package mars;

/**
 * A j�t�kos sebess�g�t felezi meg.
 */
public class Goo implements Spot {
	private int durability;
	private static final int maxDurability= 4;
	
	/**
	 * Be�ll�tja a kop�s maxim�lis �llapotainak sz�m�t.
	 */
	public Goo(){
		durability = maxDurability;
	}
	
	/**
	 * A referenci�ul kapott j�t�kos sebess�g�t megfelez �s cs�kkenti a folt �llapot�t.
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
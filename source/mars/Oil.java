package mars;

/**
 * Megakadalyozza a jatekos sebessegvaltoztatasat es iranyvaltoztatasat egy korig.
 */
public class Oil implements Spot {
	private long timeCreated;
	private static final long expireTime = 30000;
	
	/**
	 * Inicializalas.
	 */
	public Oil() {
		timeCreated = System.currentTimeMillis();
	}
	
	/**
	 *  A referenciaul kapott jatekos sebesseg-es iranyvaltoztatasat megakadalyozza a Player oilFlag valtozojanak beallitasaval.
	 */
	public void handlePlayer(Player player) {
		player.setOilFlag(true);
	}

	/**
	 * Visszaadja, hogy felszaradt-e a folt.(ellenorzese a timeCreated,jelenlegi ido es expireTime felhasznalasaval)
	 */
	public boolean isDeletable() {
		if(System.currentTimeMillis() - timeCreated >= expireTime)
			return true;
		else
			return false;
	}
	
	public ImageType getType(){
		return ImageType.OIL;
	}
	
	/**
	 * Visszaadja, hogy milyen idos az oil.		//Csak a protohoz kellettek azthiszem
	 */
	
	/*
	public long getExpiredTime(){
		return (System.currentTimeMillis() - timeCreated) / 1000;
		
	}*/
	
	/**
	 * ToString feluldefinialasa.
	 *//*
	public String toString(){
		return "oil";		
	}*/
}
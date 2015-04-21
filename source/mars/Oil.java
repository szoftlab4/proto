package mars;

/**
 * Megakadályozza a játékos sebességváltoztatását és irányváltoztatását egy körig.
 */
public class Oil implements Spot {
	@SuppressWarnings("unused")
	private long timeCreated;
	@SuppressWarnings("unused")
	private static final long expireTime = 10000;
	/**
	 * test_time: Tesztesetek esetén használatos. Az oil korát adja meg.
	 */
	private int test_time;

	/**
	 * Inicializálás.
	 */
	public Oil() {
		//timeCreated = System.currentTimeMillis();
		test_time = 0;
	}
	
	/**
	 *  A referenciául kapott játékos sebesség-és irányváltoztatását megakadályozza a Player oilFlag változójának beállításával.
	 */
	public void handlePlayer(Player player) {
		player.setOilFlag(true);
	}

	/**
	 * Visszaadja, hogy felszáradt-e a folt.(ellenõrzése a timeCreated,jelenlegi idõ és expireTime felhasználásával)
	 */
	public boolean isDeletable() {
		//if(System.currentTimeMillis() - timeCreated >= expireTime){
		if(test_time > 9){
			return true;
		}
		else
			return false;
	}
	
	/**
	 * A paraméterben megadott egész számmal(sec) növeli az oil korát.
	 */
	public void inc(int i){
		test_time += i;
	}
	
	/**
	 * Visszaadja, hogy milyen idõs az oil.
	 */
	public long getExpiredTime(){
		//return (System.currentTimeMillis() - timeCreated) / 1000;
		return test_time;
	}
	
	/**
	 * ToString felüldefiniálása.
	 */
	public String toString(){
		return "oil";		
	}
}
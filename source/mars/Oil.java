package mars;

/**
 * Megakad�lyozza a j�t�kos sebess�gv�ltoztat�s�t �s ir�nyv�ltoztat�s�t egy k�rig.
 */
public class Oil implements Spot {
	@SuppressWarnings("unused")
	private long timeCreated;
	@SuppressWarnings("unused")
	private static final long expireTime = 10000;
	/**
	 * test_time: Tesztesetek eset�n haszn�latos. Az oil kor�t adja meg.
	 */
	private int test_time;

	/**
	 * Inicializ�l�s.
	 */
	public Oil() {
		//timeCreated = System.currentTimeMillis();
		test_time = 0;
	}
	
	/**
	 *  A referenci�ul kapott j�t�kos sebess�g-�s ir�nyv�ltoztat�s�t megakad�lyozza a Player oilFlag v�ltoz�j�nak be�ll�t�s�val.
	 */
	public void handlePlayer(Player player) {
		player.setOilFlag(true);
	}

	/**
	 * Visszaadja, hogy felsz�radt-e a folt.(ellen�rz�se a timeCreated,jelenlegi id� �s expireTime felhaszn�l�s�val)
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
	 * A param�terben megadott eg�sz sz�mmal(sec) n�veli az oil kor�t.
	 */
	public void inc(int i){
		test_time += i;
	}
	
	/**
	 * Visszaadja, hogy milyen id�s az oil.
	 */
	public long getExpiredTime(){
		//return (System.currentTimeMillis() - timeCreated) / 1000;
		return test_time;
	}
	
	/**
	 * ToString fel�ldefini�l�sa.
	 */
	public String toString(){
		return "oil";		
	}
}
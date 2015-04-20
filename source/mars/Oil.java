package mars;

public class Oil implements Spot {
	private long timeCreated;
	private static final long expireTime = 10000;
	private int test_time;

	public Oil() {
		//timeCreated = System.currentTimeMillis();
		test_time = 0;
	}
	
	public void handlePlayer(Player player) {
		player.setOilFlag(true);
	}

	public boolean isDeletable() {
		//if(System.currentTimeMillis() - timeCreated >= expireTime){
		if(test_time > 9){
			return true;
		}
		else
			return false;
	}
	
	public void inc(int i){
		test_time += i;
	}
	
	public long getExpiredTime(){
		//return (System.currentTimeMillis() - timeCreated) / 1000;
		return test_time;
	}
	
	public String toString(){
		return "oil";		
	}
}
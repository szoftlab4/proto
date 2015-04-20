package mars;

public class Oil implements Spot {
	private long timeCreated;
	private static final long expireTime=10000;

	public Oil() {
		timeCreated = System.currentTimeMillis();
	}
	
	public void handlePlayer(Player player) {
		player.setOilFlag(true);
	}

	public boolean isDeletable() {
		if(System.currentTimeMillis()-timeCreated>=expireTime){
			return true;
		}
		else
			return false;
	}
	
	public long getExpiredTime(){
		return (System.currentTimeMillis() - timeCreated) * 1000;
	}
	
	public String toString(){
		return "oil";		
	}
}
package mars;

public class Oil implements Spot {
	private long timeCreated;
	private long expireTime;

	public Oil() {
	timeCreated=System.currentTimeMillis();
	expireTime=10000;
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
}
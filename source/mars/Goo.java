package mars;

public class Goo implements Spot {
	private int durability;
	private static final int maxDurability= 4;
	
	public Goo(){
		durability=maxDurability;
	}
	
	public void handlePlayer(Player player) {
		
		durability--;
		
		int speed = player.getSpeed()/2;
		if(speed <= 1)
			speed = 1;
		player.setSpeed(speed);
	}

	public boolean isDeletable() {
		
		return durability == 0;
	}
	
	public String toString(){
		return "goo";		
	}
}
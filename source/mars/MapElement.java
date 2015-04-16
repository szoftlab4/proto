package mars;

import java.util.ArrayList;

public class MapElement {
	private Position pos;
	private Spot spot;
	private ArrayList<Player> refPlayer;
	private ArrayList<MicroMachine> refMM;

	public void addSpot(Spot spot) {
		this.spot = spot;
	}

	public void handle(Player player) {
		if(hasSpot()){
			spot.handlePlayer(player);
		}
	}

	public void checkSpot() {
		if(spot.isDeletable()){
			deleteSpot();
		}
	}

	public void deleteSpot() {
		spot = null;
	}

	public void handleCollision() {
		//TODO
	}

	public boolean hasSpot() {
		return spot != null;
		
	}


	public Position getPos() {
		return pos;
	}
}
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
	
	public boolean isFree(){
		return (refPlayer.isEmpty() && refMM.isEmpty() && spot==null);
	}

	private void playerPlayer(){
		
	}
	
	private void playerMM(){
		
	}
	
	private void mmMM(){
		
	}
	
	public void handleCollision() {
		//TODO
		clearRobotRefs();
	}

	public boolean hasSpot() {
		return spot != null;
		
	}

	private void clearRobotRefs(){
		refPlayer.clear();
		refMM.clear();
	}

	public Position getPos() {
		return pos;
	}
}
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
	
	public void addPlayerRef(Player p){
		refPlayer.add(p);
	}
	
	public void addMMRef(MicroMachine mm){
		refMM.add(mm);
	}

	public void handle(Player player) {
		if(hasSpot()){
			spot.handlePlayer(player);
		}
		addPlayerRef(player);
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
	
	private void collidePlayers(){
		int maxspeed = 0;
		int sum = 0;
		for(Player p : refPlayer){
			int temp = p.getSpeed();
			sum += temp;
			if(temp>maxspeed)
				maxspeed = temp;
		}
		int avg = sum/refPlayer.size();
		for(Player p : refPlayer){
			if(p.getSpeed() != maxspeed)
				p.setAlive(false);
			else
				p.setSpeed(avg);
		}
	}
	
	public void handleCollision() {
		//Van-e jatekos
		if(!refPlayer.isEmpty()){
			//Ha van akkor mindenkeppen meghalnak a kisrobotok
			for(MicroMachine mm : refMM){
				mm.setAlive(false);
			}
			//Utkoztetjuk a jatekosokat, egy jatekosra is mukodik
			collidePlayers();
			//Torolni kell a referenciakat, az else ag miatt
			clearRobotRefs();
		}
		//Nem volt jatekos
		else{
			//Van-e kisrobot
			if(!refMM.isEmpty()){
				//Csak akkor van utkozes ha legalabb 2 kisrobot van
				if(refMM.size()>1)
					//Ha van legalabb 2, akkor mindenkit visszaforditunk (stay eseten se lesz gond)
					for(MicroMachine mm : refMM){
						mm.invertDir();
					}
			}
			//Toroljuk mindenkeppen a referenciakat
			clearRobotRefs();
		}
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
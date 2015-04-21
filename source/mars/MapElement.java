package mars;

import java.util.ArrayList;

public class MapElement {
	private Position pos;
	private Spot spot;
	private ArrayList<Player> refPlayer;
	private ArrayList<MicroMachine> refMM;
	
	public MapElement(Position pos, Spot spot){
		this.pos = pos;
		this.spot = spot;
		refPlayer = new ArrayList<Player>();
		refMM = new ArrayList<MicroMachine>();
	}

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
		if(spot != null && spot.isDeletable()){
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
		double sum = 0;
		int x = 0;
		int y = 0;
		boolean moreThanOneMax = false;
		for(Player p : refPlayer){
			int temp = p.getSpeed();
			if(temp>maxspeed){
				maxspeed = temp;
				moreThanOneMax = false;
			}
			else if(temp == maxspeed)
				moreThanOneMax = true;
			
			switch(p.headDir){
				case UP:
					y += temp;
					break;
				case DOWN:
					y -= temp;
					break;
				case RIGHT:
					x += temp;
					break;
				case LEFT:
					x -= temp;
					break;
			}
			y = Math.abs(y);
			x = Math.abs(x);
			sum = Math.sqrt(x*x + y*y);
		}
		int avg = (int) sum/refPlayer.size();
		if(avg < 1)
			avg = 1;
		for(Player p : refPlayer){
			if(moreThanOneMax){
				p.setAlive(false);
			}
			else{
				if(p.getSpeed() == maxspeed)
					p.setSpeed(avg);
				else
					p.setAlive(false);
			}
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
	
	public Spot getSpot(){
		return spot;
	}

	public Position getPos() {
		return pos;
	}
	
	public boolean isDummy(){
		return (pos.getX() == -1 && pos.getY() == -1);
	}
}
package mars;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**********
 * 
 * @author Khongi
 *
 * Meg kozel sincs kesz...
 *
 */

public class MapHandler implements Observer {
	private ArrayList<MapElement> map;
	private ArrayList<Position> road;
	private int mapWidth;
	
	public void loadMap(String filepath) {
		//TODO palyabetoltes
		//TODO palyaszelesseg meghatarozas
	}

	public void reset() {
		//TODO
	}

	public void checkSpots() {
		for(Position pos : road){
			map.get(posToIndex(pos)).checkSpot();
		}
		
	}

	public Direction setMMDirection(MicroMachine microMachine) {
		//TODO ez szopas lesz...
		
		return null;
		
	}

	public void deleteSpot(Position pos) {
		map.get(posToIndex(pos)).deleteSpot();
	}

	public void startCollisions() {
		for(Position pos : road){
			map.get(posToIndex(pos)).handleCollision();
		}
	}
	
	private int posToIndex(Position pos){
		//Atmagiceli pos-t indexelheto alakba
		return pos.getY()*mapWidth + pos.getX();
	}
	
	private ArrayList<Position> mapSpots(){
		ArrayList<Position> spotPositions = new ArrayList<Position>();
		
		for(Position pos : road){
			if(map.get(posToIndex(pos)).hasSpot())
				spotPositions.add(pos);
		}
		
		return spotPositions;
	}
	
	private void buildMap(InputStream mapFile){
		//TODO
	}
	
	public Position getAvailablePos(){
		
		//Ha -1,-1 el terne vissza akkor nincs szabad hely
		//Elso szabad helyet adja vissza
		Position freePos = new Position(-1,-1);
		
		for(Position pos : road){
			MapElement mapElement = map.get(posToIndex(pos));
			if(mapElement.isFree())
				freePos = pos;
		}
		
		return freePos;
	}

	public HeadDirection getValidHeadDir(Position pos){
		return null;
	}
	
	private void checkPosition(Player player){
		Position playerPos = player.getNextPos();
		boolean playerIsAlive = false;
		for(Position pos : road){
			if(playerPos == pos)
				playerIsAlive = true;
		}
		
		if(playerIsAlive){
			map.get(posToIndex(playerPos)).handle(player);
		}
		else{
			player.setAlive(false);
		}
	}
	
	public void addSpot(Position pos, Spot spot){
		map.get(posToIndex(pos)).addSpot(spot);
	}

	@Override
	public void update(Observable o, Object arg) {
		
		//Elvileg kesz
		Player player = (Player) o;
		Spot spot = (Spot) arg;
		
		checkPosition(player);
		
		if(spot != null){
			addSpot(player.getPosition(),spot);
		}
			
	}
}
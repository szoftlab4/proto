package mars;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MapHandler implements Observer {
	private ArrayList<MapElement> map;
	private ArrayList<Position> road;
	private int mapWidth;
	
	//ASDSDASDFA
	
	public void loadMap() {
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

	private void checkPosition(Player player){
		//TODO
	}
	
	public void addSpot(Position pos, Spot spot){
		//TODO
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
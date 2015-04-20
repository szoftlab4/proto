package mars;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

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
	private int mapHeight;
	
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
		Position freePos = new Position(-1,-1);
		boolean done = false;
		int range = map.size();
		Random rnd = new Random();
		int tryCount = 0;
		while(!done){
			int idx = rnd.nextInt(range);
			freePos = road.get(idx);
			MapElement me = map.get(posToIndex(freePos));
			if(me.isFree())
				done = true;
			tryCount++;
			//Ha 99x probalkoztunk es igy se talal akkor vegigmegyunk rendesen a listan
			if(tryCount > 99){
				for(Position pos : road){
					me = map.get(posToIndex(pos));
					if(me.isFree()){
						done = true;
						freePos = pos;
					}
				}
			}
		}

		return freePos;
	}

	private boolean isValidCoordinate(int x, int y){
		return !((y<0) || (y>mapHeight) || (x<0) || (x>mapWidth));
	}
	
	public HeadDirection getValidHeadDir(Position pos){
		int x = pos.getX();
		int y = pos.getY();
		if(isValidCoordinate(x,y+1))
			if(!map.get(posToIndex(new Position(x,y+1))).isDummy())
				return HeadDirection.UP;
		if(isValidCoordinate(x+1,y))
			if(!map.get(posToIndex(new Position(x+1,y))).isDummy())
				return HeadDirection.RIGHT;
		if(isValidCoordinate(x-1,y))
			if(!map.get(posToIndex(new Position(x-1,y))).isDummy())
				return HeadDirection.LEFT;
		if(isValidCoordinate(x,y-1))
			if(!map.get(posToIndex(new Position(x,y-1))).isDummy())
				return HeadDirection.DOWN;
		return HeadDirection.DOWN;
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
package mars;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	private String mapName; //p�lya neve
	private int mapHeight;

	public MapHandler(){
		map = new ArrayList<MapElement>();
		road = new ArrayList<Position>();
		mapWidth = 0;
	}

	public void loadMap(String filepath){
		try {
			File file = new File("res\\" + filepath);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			
			doc.getDocumentElement().normalize();
			
			NodeList listOfMapElement = doc.getElementsByTagName("mapelement");
			NodeList name = doc.getElementsByTagName("name");
			
			mapName = ((Element) name.item(0)).getTextContent();
			
			//System.out.println(mapName);
			
			int x, y;
			String spot;
			ArrayList<MapElement> unsorted = new ArrayList<MapElement>();
			
			for (int i = 0; i < listOfMapElement.getLength(); i++) {
				
				Node node = listOfMapElement.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element mapElement = (Element) node;
					
					NodeList elementList = mapElement.getElementsByTagName("x");
					Element element = (Element) elementList.item(0);
					x =  Integer.parseInt(element.getTextContent());
					
					elementList = mapElement.getElementsByTagName("y");
					element = (Element) elementList.item(0);
					y =  Integer.parseInt(element.getTextContent());
					
					elementList = mapElement.getElementsByTagName("spot");
					element = (Element) elementList.item(0);
					spot =  element.getTextContent().toString();	
					
					if(x > mapWidth)
						mapWidth = x;
					
					if(y > mapHeight)
						mapHeight = y;
					
					road.add(new Position(x, y));
					
					if (spot.equalsIgnoreCase("goo"))
						unsorted.add(new MapElement(new Position(x, y), new Goo()));
					else if (spot.equalsIgnoreCase("oil"))
						unsorted.add(new MapElement(new Position(x, y), new Oil()));
					else
						unsorted.add(new MapElement(new Position(x, y), null));
				}
			}
			
			Boolean rowEnd = false;
			
			for(int i = 0; i <= mapWidth; i++){
				for(int j = 0; j <= mapHeight; j++){
					for(int l = 0; l < unsorted.size(); l++){
						if(unsorted.get(l).getPos().getX() == i && unsorted.get(l).getPos().getY() == j ){
							map.add(unsorted.get(l));
							break;
						}
						else if(l == unsorted.size()-1)
							rowEnd = true;
						else if (rowEnd){
							map.add(new MapElement(new Position(-1, -1), null));
							rowEnd = false;
						}
					}
				}
			}
			
			/*for(int i = 0; i < map.size(); i++)
				System.out.println("x: " + map.get(i).getPos().getX() + " y: " + map.get(i).getPos().getY() + " spot: " + map.get(i).getSpot());*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reset() {
		//TODO
	}

	public void checkSpots() {
		for(Position pos : road){
			map.get(posToIndex(pos)).checkSpot();
		}
		
	}

	private int findPosIndexOnRoad(Position pos){
		for(int i=0; i<road.size();i++)
			if(pos == road.get(i))
				return i;
		return -1;
	}
	
	private int searchLeft(int posIndex){
		int cnt = 0;
		for(int i = posIndex; i>=0 ; i--){
			MapElement m = map.get(posToIndex(road.get(i)));
			if(!m.hasSpot())
				cnt++;
			return cnt;
		}
		for(int i = road.size()-1 ; posIndex < i; i-- ){
			MapElement m = map.get(posToIndex(road.get(i)));
			if(!m.hasSpot())
				cnt++;
			return cnt;
		}
		return -1;
	}
	
	private int searchRight(int posIndex){
		int cnt = 0;
		for(int i = posIndex; i<road.size() ; i++){
			MapElement m = map.get(posToIndex(road.get(i)));
			if(!m.hasSpot())
				cnt++;
			return cnt;
		}
		for(int i = 0; i< posIndex ; i++ ){
			MapElement m = map.get(posToIndex(road.get(i)));
			if(!m.hasSpot())
				cnt++;
			return cnt;
		}
		return -1;
	}
	
	private HeadDirection newPosDirection(Position o, Position n){
		HeadDirection hdir;
		int x = n.getX() - o.getX();
		int y = n.getY() - o.getY();
		if( x == 0 && y == 1){
			hdir = HeadDirection.UP;
		}
		else if( x == 0 && y == -1) {
			hdir = HeadDirection.DOWN;
		}
		else if( x == 1 && y == 0 ){
			hdir = HeadDirection.RIGHT;
		}
		else
			hdir = HeadDirection.LEFT;
		return hdir;
	}
	private Direction headDirToDir(HeadDirection o,HeadDirection n){
		if(o == n){
			return Direction.FORWARD;
		}
		else if((o==HeadDirection.DOWN&&n==HeadDirection.RIGHT)||(o==HeadDirection.UP&&n==HeadDirection.LEFT)||(o==HeadDirection.LEFT&&n==HeadDirection.DOWN)||(o==HeadDirection.RIGHT&&n==HeadDirection.UP)) {
			return Direction.LEFT;
		}
		else if((o==HeadDirection.DOWN&&n==HeadDirection.LEFT)||(o==HeadDirection.UP&&n==HeadDirection.RIGHT)||(o==HeadDirection.LEFT&&n==HeadDirection.UP)||(o==HeadDirection.RIGHT&&n==HeadDirection.DOWN)) {
			return Direction.RIGHT;
		}
		else  {
			return Direction.BACKWARD;
		
		}
	}
	
	public void setMMDirection(MicroMachine microMachine) {
		Position pos = microMachine.getPosition();
		HeadDirection hdir;
		int idx = findPosIndexOnRoad(pos);
		int left = searchLeft(idx);
		int right = searchRight(idx);
		if(left>right){
			int nextIdx = idx + 1;
			if(nextIdx == road.size())
				nextIdx = 0;
			hdir = newPosDirection(road.get(idx), road.get(nextIdx));
			microMachine.setDirection(this.headDirToDir(microMachine.getHeadDir(), hdir));
		}
		else if(left<right){
			int prevIdx = idx - 1;
			if(prevIdx < 0)
				prevIdx = road.size() - 1;
			hdir = newPosDirection(road.get(idx), road.get(prevIdx));
			microMachine.setDirection(this.headDirToDir(microMachine.getHeadDir(), hdir));
		}
		else if(left == 0 && right == 0){
			microMachine.setDirection(Direction.STAY);
		}
		else{
			int nextIdx = idx + 1;
			if(nextIdx == road.size())
				nextIdx = 0;
			hdir = newPosDirection(road.get(idx), road.get(nextIdx));
			microMachine.setDirection(this.headDirToDir(microMachine.getHeadDir(), hdir));
		}
			
			
		
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
			player.setPosition(playerPos);
		}
		else{
			player.setAlive(false);
		}
	}
	
	public void addSpot(Position pos, Spot spot){
		if(!map.get(posToIndex(pos)).isDummy())
			map.get(posToIndex(pos)).addSpot(spot);
	}

	@Override
	public void update(Observable o, Object arg) {
		
		//Elvileg kesz
		Player player = (Player) o;
		if(arg != null)
			addSpot(player.getPosition(),(Spot)arg);
		
		checkPosition(player);

	}
}
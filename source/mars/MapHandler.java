package mars;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
			
			int x, y;
			String spot;
			int max_x = 0, max_y = 0;
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
					
					if(x > max_x)
						max_x = x;
					
					if(y > max_y)
						max_y = y;
					
					
					
					if (spot.equalsIgnoreCase("goo"))
						unsorted.add(new MapElement(new Position(x, y), new Goo()));
					else if (spot.equalsIgnoreCase("oil"))
						unsorted.add(new MapElement(new Position(x, y), new Oil()));
					else
						unsorted.add(new MapElement(new Position(x, y), null));
				}
			}
			
			for(int i = 0, k = 0; i <= max_x; i++){
				for(int j = 0; j <= max_y; j++){
					if(unsorted.get(k).getPos().getX() == i && unsorted.get(k).getPos().getY() == j){
						map.add(unsorted.get(k));
						k++;
					}
					else
						map.add(new MapElement(new Position(-1, -1), null));
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
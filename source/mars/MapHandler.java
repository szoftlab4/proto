package mars;

import java.io.File;
import java.io.FileNotFoundException;
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

/**
 * Itt t�lt�dik be a p�lya. Tartalmazza a p�lyaelemeket egy m�trix adatszerkezetben �s 
 * a p�lya alakj�t (csak poz�ci�k) egy list�ban. Att�l f�gg�en, hogy hova l�pett, lekezeli 
 * a j�t�kost. Ellen�rzi, hogy a j�t�kos a p�ly�ra l�pett-e, ha nem akkor �meg�li� a j�t�kost.
 * �tadja a j�t�kos referenci�j�t a p�lyaelemnek. Kisz�molja �s meg�llap�tja egy kisrobot eset�n,
 * hogy merre van a legk�zelebbi folt. V�gigiter�l a p�lyelemeken �s megh�vja az ellen�rz� f�ggv�nyeiket.
 */
public class MapHandler implements Observer {
	/**
	 * map: Ebben vannak a mapElementek
	 * road: Ebben csak a p�lyaelemet tartalmaz� Positionok vannak
	 */
	private ArrayList<MapElement> map;
	private ArrayList<Position> road;
	private int mapWidth;
	private String mapName; //p�lya neve
	private int mapHeight;

	/**
	 * Inicializ�l�s.
	 */
	public MapHandler(){
		map = new ArrayList<MapElement>();
		road = new ArrayList<Position>();
		mapWidth = 0;
		mapName = null;
	}

	/**
	 * Bet�lti a p�ly�t a param�terben kapott f�jln�v szerint.
	 * @param filepath: f�jln�v, amit bet�lt
	 */
	public void loadMap(String filepath){
		try {
			File file = new File("res\\" + filepath);
			
			/**
			 * XML feldolgoz�.
			 */
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			
			doc.getDocumentElement().normalize();
			
			NodeList listOfMapElement = doc.getElementsByTagName("mapelement");
			NodeList name = doc.getElementsByTagName("name");
			
			mapName = ((Element) name.item(0)).getTextContent();
			
			int x, y;
			String spot;
			ArrayList<MapElement> unsorted = new ArrayList<MapElement>();
			
			/**
			 * Felt�ltj�k az unsorted list�t a p�lyaelemekkel.
			 */
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
			
			mapHeight++;
			mapWidth++;
			
			Boolean rowEnd = false;
			
			/**
			 * A map-et felt�lt� algoritmus, ami az unsorted szerint t�lti fel p�lyaelemekkel, illetve dummy elemekkel.
			 */
			for(int j = 0; j < mapHeight; j++){
				for(int i = 0; i < mapWidth; i++){
					for(int l = 0; l < unsorted.size(); l++){
						if(unsorted.get(l).getPos().getX() == i && unsorted.get(l).getPos().getY() == j){
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
			
			System.out.println("\nSikeresen bet�lt�tt�k a p�ly�t.");
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
			System.out.println("\nSikertelen a p�lya bet�lt�se.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\nSikertelen a p�lya bet�lt�se.");
		}
	}
	/**
	 * Vegigiteralulnk a palyan es elinditjuk a foltok ellenorzeset/torleset
	 */
	public void checkSpots() {
		for(Position pos : road){
			map.get(posToIndex(pos)).checkSpot();
		}
	}
	/**
	 * Egy megadott pozicio megkerese a Road listben es annak indexenek visszaadasa
	 * @param pos
	 * @return i
	 */
	private int findPosIndexOnRoad(Position pos){
		for(int i=0; i<road.size();i++)
			if(pos.getX() == road.get(i).getX() && pos.getY() == road.get(i).getY())
				return i;
		return -1;
	}
	/**
	 * Utvonalkereses a legkozelebbi folthoz a Road listaban az adott indextol balra,visszaadja milyen tavolsagra van balfele indulva a legkozelebbi folt (ha nincs folt -1)
	 * @param posIndex
	 * @return cnt
	 */
	private int searchLeft(int posIndex){
		int cnt = 0;
		for(int i = posIndex; i>=0 ; i--){
			MapElement m = map.get(posToIndex(road.get(i)));
			if(!m.hasSpot())
				cnt++;
			else
				return cnt;
		}
		for(int i = road.size()-1 ; posIndex < i; i-- ){
			MapElement m = map.get(posToIndex(road.get(i)));
			if(!m.hasSpot())
				cnt++;
			else
				return cnt;
		}
		return -1;
	}
	/**
	 * Utvonalkereses a legkozelebbi folthoz a Road listaban az adott indextol jobbra,visszaadja milyen tavolsagra van jobbfele indulva a legkozelebbi folt(ha nincs folt -1)
	 * @param posIndex
	 * @return cnt
	 */
	private int searchRight(int posIndex){
		int cnt = 0;
		for(int i = posIndex; i<road.size() ; i++){
			MapElement m = map.get(posToIndex(road.get(i)));
			if(!m.hasSpot())
				cnt++;
			else
				return cnt;
		}
		for(int i = 0; i< posIndex ; i++ ){
			MapElement m = map.get(posToIndex(road.get(i)));
			if(!m.hasSpot())
				cnt++;
			else
				return cnt;
		}
		return -1;
	}
	/**
	 * A jelenlegi pozicionkbol megadja milyen iranyban helyeykedik el a a megadott (szomszedos!) pozicio
	 * @param o
	 * @param n
	 * @return hdir
	 */
	private HeadDirection newPosDirection(Position o, Position n){
		HeadDirection hdir;
		
		//System.out.println("old: " + o.getX() +" " + o.getY() + " ,new: " + n.getX() + " " + n.getY());
		
		int x = n.getX() - o.getX();
		int y = n.getY() - o.getY();
		if( x == 0 && y == 1){
			hdir = HeadDirection.DOWN;
		}
		else if( x == 0 && y == -1) {
			hdir = HeadDirection.UP;
		}
		else if( x == 1 && y == 0 ){
			hdir = HeadDirection.RIGHT;
		}
		else
			hdir = HeadDirection.LEFT;
		
		//System.out.println("hdir: " + hdir);
		return hdir;
	}
	/**
	 * Az regi HeadDir es az uj HeadDir alapjan megadja milyen Direction parancsot kell kiadni a MicroMachine-nek hogy az uj HeadDir fele mozduljon
	 * @param o
	 * @param n
	 * @return Direction
	 */
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
	/**
	 * MicroMachinek iranyito fuggvenye itt szamoljuk ki merre kell indulni a legkozelebbi folt fele a Direction megadasaval a MicroMachinnek.
	 * A MM poziciojatol mindket iranyban vegignezzuk az utat a legkozelebbi foltok tavolsagaert,majd amelyik iranyban volt kozelebb arra inditjuk a dir valtozojanak beallitasaval,
	 * ha folton all akkor STAY parancsal jelezzuk hogy helyben van es takaritson,ha ugyannolyan tavra van mindket irany,vagy nincsenek foltok,akkor orajarasaval megeggyezo iranyba indulunk az uton.
	 * A kisrobotok referenciait hozzaadjuk a 
	 * Ha kisrobot utkozik kisrobottal akkor nem adunk uj iranyt,mert akkor a regi iranya mar meginvertalodott,es az alapjan fog lepni.
	 * @param microMachine
	 */
	public void setMMDirection(MicroMachine microMachine) {
		Position pos = microMachine.getPosition();
		HeadDirection hdir;
		int idx = findPosIndexOnRoad(pos);
		//System.out.println("Pos: " + pos.getX() + " " + pos.getY() + " ,Index: " + idx);
		int left = searchLeft(idx);
		//System.out.println("Balra ennyi lepesben talalt: " + left);
		int right = searchRight(idx);
		//System.out.println("Jobbra ennyi lepesben talalt: " + right);
		if(left>right){
			int nextIdx = idx + 1;
			if(nextIdx == road.size())
				nextIdx = 0;
			hdir = newPosDirection(road.get(idx), road.get(nextIdx));
			map.get(this.posToIndex(road.get(nextIdx))).addMMRef(microMachine);
			if(!microMachine.isCollided())
				microMachine.setDirection(this.headDirToDir(microMachine.getHeadDir(), hdir));
		}
		else if(left<right){
			int prevIdx = idx - 1;
			if(prevIdx < 0)
				prevIdx = road.size() - 1;
			hdir = newPosDirection(road.get(idx), road.get(prevIdx));
			map.get(this.posToIndex(road.get(prevIdx))).addMMRef(microMachine);
			if(!microMachine.isCollided())
				microMachine.setDirection(this.headDirToDir(microMachine.getHeadDir(), hdir));
		}
		else if(left == 0 && right == 0){
			if(!microMachine.isCollided())
				microMachine.setDirection(Direction.STAY);
			map.get(this.posToIndex(pos)).addMMRef(microMachine);
		}
		else{
			int nextIdx = idx + 1;
			if(nextIdx == road.size())
				nextIdx = 0;
			hdir = newPosDirection(road.get(idx), road.get(nextIdx));
			map.get(this.posToIndex(road.get(nextIdx))).addMMRef(microMachine);
			if(!microMachine.isCollided())
				microMachine.setDirection(this.headDirToDir(microMachine.getHeadDir(), hdir));
		}
	}

	/**
	 * A param�terben megadott poz�ci�n l�v� spotot t�rli.
	 */
	public void deleteSpot(Position pos) {
		map.get(posToIndex(pos)).deleteSpot();
	}

	/**
	 * �tk�ztet�s elind�t�sa.
	 */
	public void startCollisions() {
		//System.out.println("elindult az utkoztetes...");
		for(Position pos : road){
			map.get(posToIndex(pos)).handleCollision();
		}
	}
	
	/**
	 * Visszaadja a p�lya nev�t.
	 * @return
	 */
	public String getMapName(){
		return mapName;
	}
	
	/**
	 * A param�terben megadott poz�ci�b�l egy indexet hoz l�tre. (�gy tudjuk kezelni a
	 * sorfolytonos m�trixunkat (Lista))
	 */
	private int posToIndex(Position pos){
		//Atmagiceli pos-t indexelheto alakba
		return pos.getY() * mapWidth + pos.getX();
	}
	
	/**
	 * Visszaad egy olyan v�letlenszer� poz�ci�t, amelyre tudunk robotot rakni.
	 */
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

	/**
	 * Visszaadja, hogy a koordin�ta a j�t�kt�ren bel�l van-e.
	 */
	private boolean isValidCoordinate(int x, int y){
		return !((y<0) || (y>mapHeight) || (x<0) || (x>mapWidth));
	}
	
	/**
	 * Keres egy ir�nyt, amerre van egy �rv�nyes p�lyaelem.
	 */
	public HeadDirection getValidHeadDir(Position pos){
		int x = pos.getX();
		int y = pos.getY();
		if(isValidCoordinate(x,y+1))
			if(!map.get(posToIndex(new Position(x,y-1))).isDummy())
				return HeadDirection.UP;
		if(isValidCoordinate(x+1,y))
			if(!map.get(posToIndex(new Position(x+1,y))).isDummy())
				return HeadDirection.RIGHT;
		if(isValidCoordinate(x-1,y))
			if(!map.get(posToIndex(new Position(x-1,y))).isDummy())
				return HeadDirection.LEFT;
		if(isValidCoordinate(x,y-1))
			if(!map.get(posToIndex(new Position(x,y+1))).isDummy())
				return HeadDirection.DOWN;
		return HeadDirection.DOWN;
	}
	
	/**
	 * Ez h�v�dik meg a mapHandlerben. Megn�zi, hogy p�lyaelemre l�pett-e. Ha igen, akkor �l
	 * �s tov�bb adja a player referenci�j�t, ha nem, akkor meghalt.
	 */
	public void checkPosition(Player player){
		Position playerPos = player.getNextPos();
		boolean playerIsAlive = false;
		for(Position pos : road){
			if(playerPos.getX() == pos.getX() && playerPos.getY() == pos.getY() )
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
	
	/**
	 * Megvizsg�lja, hogy a kisrobot k�szen van-e a takar�t�ssal. Ha igen, akkor
	 * a kisrobot hely�n l�v� spotot kit�rli.
	 */
	public void testMMCleaningStatus(MicroMachine mm){
		if(mm.isDoneCleaning()){
			this.deleteSpot(mm.pos);
		}
	}
	
	/**
	 * A megadott poz�ci�ra leteszi a megadott foltot.
	 * @param pos: poz�ci�
	 * @param spot: folt t�pusa
	 */
	public void addSpot(Position pos, Spot spot){
		//if(!map.get(posToIndex(pos)).isDummy())
			map.get(posToIndex(pos)).addSpot(spot);
	}
	
	/**
	 * Player l�p�sekor fut le, ha a player le akar tenni egy foltot, akkor azt is megkapja az argumentumban,
	 * �s megvizsg�lja, hogy hova ugrott.
	 */
	@Override
	public void update(Observable o, Object arg) {
		//Elvileg kesz
		Player player = (Player) o;
		if(arg != null)
			addSpot(player.getPosition(),(Spot)arg);
		
		checkPosition(player);
	}
	
	/**
	 * Getterek.
	 */
	
	public ArrayList<MapElement> getMap(){
		return map;
	}
	
	/**
	 * Visszaadja azokat a mapElementeket, melyeken van spot.
	 * @param console: ha true, akkor ki is �rja �ket.
	 */
	public ArrayList<MapElement> getSpots(Boolean console){
		ArrayList<MapElement> spots = new ArrayList<MapElement>();
		for(MapElement mapelement : map){
			if(mapelement.hasSpot() && !mapelement.isDummy()){
				
				if(mapelement.getSpot().toString().equalsIgnoreCase("oil") && console)
					System.out.println("(" + mapelement.getPos().getX() + ";" + mapelement.getPos().getY() + "), oil, " + ((Oil) mapelement.getSpot()).getExpiredTime());
				else if(mapelement.getSpot().toString().equalsIgnoreCase("goo") && console)
					System.out.println("(" + mapelement.getPos().getX() + ";" + mapelement.getPos().getY() + "), goo, " + ((Goo) mapelement.getSpot()).getDurability());
				
				spots.add(mapelement);
			}
		}
		return spots;
	}
}
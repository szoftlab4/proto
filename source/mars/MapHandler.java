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
  * Itt toltodik be a palya. Tartalmazza a palyaelemeket egy matrix adatszerkezetben es 
 * a pelya alakjat (csak poziciok) egy listaban. Attol fuggoen, hogy hova lepett, lekezeli 
 * a jatekost. Ellenorzi, hogy a jatekos a palyara lepett-e, ha nem akkor megoli a jatekost.
 * Atadja a jatekos referenciajat a palyaelemnek. Kiszamolja es megallapitja egy kisrobot eseten,
 * hogy merre van a legkozelebbi folt. Vegigiteral a palyelemeken es meghivja az ellenorzo fuggvenyeiket.
 */
public class MapHandler implements Observer {
	/**
	 * map: Ebben vannak a mapElementek
	 * road: Ebben csak a palyaelemet tartalmazo Positionok vannak
	 */
	private ArrayList<MapElement> map;
	private ArrayList<Position> road;
	private int mapWidth;
	private String mapName; //palya neve
	private int mapHeight;
	private int playerCount;
	private int alivePlayersSoFar;

	/**
	 * Inicializalas.
	 */
	public MapHandler(){
		map = new ArrayList<MapElement>();
		road = new ArrayList<Position>();
		mapWidth = 0;
		mapHeight = 0;
		mapName = null;
		playerCount = 0;
		alivePlayersSoFar = 0;
	}

	/**
	 * Betolti a palyat a parameterben kapott fajlnev szerint
	 * @param filepath: fajlnev, amit betolt
	 */
	public void loadMap(String filepath){
		try {
			File file = new File("res\\" + filepath);
			
			/**
			 * XML feldolgozo.
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
			 * Feltoltjuk az unsorted listat a palyaelemekkel.
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
					//System.out.println("road x,y: " + x +"," + y);
					
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
			 * A map-et feltolto algoritmus, ami az unsorted szerint tolti fel palyaelemekkel, illetve dummy elemekkel.
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
			
			//System.out.println("\nSikeresen betoltottuk a palyat.");
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
			//System.out.println("\nSikertelen a palya betoltese.");
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("\nSikertelen a palya betoltese.");
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
		System.out.println("Hat itt bizony szopas lesz (findPosIndexOnRoad(-1)");
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
			//TODO try catch eltuntetese
			try{
				Position roadPos = road.get(i);
				int idx = posToIndex(roadPos);
				//System.out.println("searchRight roadIdx: " + i + " roadPos: " + roadPos.getX() + "," + roadPos.getY() +" mapIdx: " + idx);
				MapElement m = map.get(idx);
				
				if(!m.hasSpot())
					cnt++;
				else
					return cnt;
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
				System.err.println("Szarsag a search rightnal");
			}catch(Exception ex){
				ex.printStackTrace();
				System.err.println("Varatlan hiba");
			}
		}
		for(int i = 0; i< posIndex ; i++ ){
			MapElement m = map.get(posToIndex(road.get(i)));
			if(!m.hasSpot())
				cnt++;
			else
				return cnt;
		}
		
		System.out.println("-1 return a searchRightnal");
		
		return -1;
	}
	/**
	 * A jelenlegi pozicionkbol megadja milyen iranyban helyezkedik el a a megadott (szomszedos!) pozicio
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
	 * A kisrobotok referenciait hozzaadjuk az utkozes elvegzesere
	 * Ha kisrobot utkozik kisrobottal akkor nem adunk uj iranyt,mert akkor a regi iranya mar meginvertalodott,es az alapjan fog lepni.
	 * @param microMachine
	 */
	public void setMMDirection(MicroMachine microMachine) {
		Position pos = microMachine.getPosition();
		HeadDirection hdir;
		int idx = findPosIndexOnRoad(pos);
		
		if(idx == -1){
			microMachine.setAlive(false);		//TODO NEM TUDTUK REPRODUKÁLNI A PROBLÉMÁT TODO
			return;
		}
		//System.out.println("Pos: " + pos.getX() + " " + pos.getY() + " ,Index: " + idx);
		int left = searchLeft(idx);
		//System.out.println("Balra ennyi lepesben talalt: " + left);
		int right = searchRight(idx);
		//System.out.println("Jobbra ennyi lepesben talalt: " + right);
		try{
			if(left>right){
				int nextIdx = idx + 1;
				if(nextIdx == road.size())
					nextIdx = 0;
				hdir = newPosDirection(road.get(idx), road.get(nextIdx));
				
				if(!microMachine.isCollided()){
					microMachine.setDirection(this.headDirToDir(microMachine.getHeadDir(), hdir));
					map.get(this.posToIndex(road.get(nextIdx))).addMMRef(microMachine);
				}else
					map.get(this.posToIndex(microMachine.getOldPos())).addMMRef(microMachine);
			}
			else if(left<right){
				int prevIdx = idx - 1;
				if(prevIdx < 0)
					prevIdx = road.size() - 1;
				hdir = newPosDirection(road.get(idx), road.get(prevIdx));
				
				if(!microMachine.isCollided()){
					microMachine.setDirection(this.headDirToDir(microMachine.getHeadDir(), hdir));
					map.get(this.posToIndex(road.get(prevIdx))).addMMRef(microMachine);
				}else
					map.get(this.posToIndex(microMachine.getOldPos())).addMMRef(microMachine);
					
			}
			else if(left == 0 && right == 0){
				if(!microMachine.isCollided()){
					microMachine.setDirection(Direction.STAY);
					map.get(this.posToIndex(pos)).addMMRef(microMachine);
				}else
					map.get(this.posToIndex(microMachine.getOldPos())).addMMRef(microMachine);
			}
			else{

				//System.out.println("Elmeletileg default jobbra megy ez a kisrobot: (" + microMachine.getPosition().getX() + ":" + microMachine.getPosition().getY() + ")");
				int nextIdx = idx + 1;
				if(nextIdx == road.size())
					nextIdx = 0;
				hdir = newPosDirection(road.get(idx), road.get(nextIdx));
				map.get(this.posToIndex(road.get(nextIdx))).addMMRef(microMachine);
				if(!microMachine.isCollided()){
					microMachine.setDirection(this.headDirToDir(microMachine.getHeadDir(), hdir));
					map.get(this.posToIndex(road.get(nextIdx))).addMMRef(microMachine);
				}else
					map.get(this.posToIndex(microMachine.getOldPos())).addMMRef(microMachine);
			
			}}catch(Exception e){
				e.printStackTrace();
				System.out.println("mmdirexcept");
			}

	}

	/**
	 * A parameterben megadott pozicion levo spotot torli.
	 */
	public void deleteSpot(Position pos) {
		try{
			map.get(posToIndex(pos)).deleteSpot();
		} catch (Exception e){
			System.out.println("asdasdasdasd");
		}
	}

	/**
	 * Utkoztetes elinditasa.
	 */
	public void startCollisions() {
		//System.out.println("elindult az utkoztetes...");
		for(Position pos : road){
			try{
				int index = posToIndex(pos);
				map.get(index).handleCollision();
			}
			catch(IndexOutOfBoundsException e){
				e.printStackTrace();
				System.err.println("Szarsag van megint az utkozesnel...");
			}
		}
	}	
	
	/**
	 * Visszaadja a palya nevet.
	 * @return
	 */
	public String getMapName(){
		return mapName;
	}
	
	/**
	 * A parameterben megadott poziciobol egy indexet hoz letre. (Igy tudjuk kezelni a
	 * sorfolytonos matrixunkat (Lista))
	 */
	private int posToIndex(Position pos){
		//Atmagiceli pos-t indexelheto alakba
		return pos.getY() * mapWidth + pos.getX();
	}
	
	/**
	 * Visszaad egy olyan veletlenszeru poziciot, amelyre tudunk robotot rakni.
	 */
	public Position getAvailablePos(){
		Position freePos = new Position(-1,-1);
		boolean done = false;
		int range = road.size();
		Random rnd = new Random();
		int tryCount = 0;
		while(!done){
			int idx = rnd.nextInt(range);
			freePos = new Position(road.get(idx).getX(),road.get(idx).getY());
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
						freePos = new Position(pos.getX(),pos.getY());
					}
				}
			}
		}

		//System.out.println("A talalt ures pozicio: " + freePos.getX() + " " + freePos.getY());
		
		return freePos;
	}

	/**
	 * Visszaadja, hogy a koordinata a jatekteren belul van-e.
	 */
	private boolean isValidCoordinate(int x, int y){
		return !((y<0) || (y>=mapHeight) || (x<0) || (x>=mapWidth));
	}
	
	/**
	 * Keres egy iranyt, amerre van egy ervenyes palyaelem.
	 */
	public HeadDirection getValidHeadDir(Position pos){
		int x = pos.getX();
		int y = pos.getY();
		if(isValidCoordinate(x,y-1))
			if(!map.get(posToIndex(new Position(x,y-1))).isDummy()){
				//System.out.println("UP lesz a " + x + "," + y + " helyen levonek az iranya");
				return HeadDirection.UP;
			}
		if(isValidCoordinate(x+1,y))
			if(!map.get(posToIndex(new Position(x+1,y))).isDummy()){
				//System.out.println("RIGHT lesz a " + x + "," + y + " helyen levonek az iranya");
				return HeadDirection.RIGHT;
			}
		if(isValidCoordinate(x-1,y))
			if(!map.get(posToIndex(new Position(x-1,y))).isDummy()){
				//System.out.println("LEFT lesz a " + x + "," + y + " helyen levonek az iranya");
				return HeadDirection.LEFT;
			}
		if(isValidCoordinate(x,y+1))
			if(!map.get(posToIndex(new Position(x,y+1))).isDummy()){
				//System.out.println("DOWN lesz a " + x + "," + y + " helyen levonek az iranya");
				return HeadDirection.DOWN;
			}
		
		return HeadDirection.DOWN;
	}
	
	/**
	 * Ez hivodik meg a mapHandlerben. Megnezi, hogy palyaelemre lepett-e. Ha igen, akkor el
	 * es tovabb adja a player referenciajat, ha nem, akkor meghalt.
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
			//TODO kivenni a kommentet
			player.setAlive(false);
			playerCount--;
			
		}
	}
	
	/**
	 * Megvizsgalja, hogy a kisrobot keszen van-e a takaritassal. Ha igen, akkor
	 * a kisrobot helyen levo spotot kitorli.
	 */
	public void testMMCleaningStatus(MicroMachine mm){
		if(mm.isDoneCleaning()){
			this.deleteSpot(mm.pos);
		}
	}
	
	/**
	 * A megadott poziciora leteszi a megadott foltot.
	 * @param pos: pozicio
	 * @param spot: folt tipusa
	 */
	public void addSpot(Position pos, Spot spot){
		//if(!map.get(posToIndex(pos)).isDummy())
			map.get(posToIndex(pos)).addSpot(spot);
	}
	
	/**
	  * Player lepesekor fut le, ha a player le akar tenni egy foltot, akkor azt is megkapja az argumentumban,
	 * es megvizsgalja, hogy hova ugrott.
	 */
	@Override
	public void update(Observable o, Object arg) {
		//System.out.println("MapHandler update");
		boolean notifySuperVisor = false;
		alivePlayersSoFar++;
		
		if(alivePlayersSoFar == playerCount)
			notifySuperVisor = true;
		
		Player player = (Player) o;
		if(arg != null)
			addSpot(player.getPosition(),(Spot)arg);
		
		checkPosition(player);
		
		if(notifySuperVisor){
			//System.out.println("Teljesult a notify feltetel");
			synchronized (Game.syncObject) {
				//System.out.println("Game Monitoraban vagyunk");
				alivePlayersSoFar = 0;
				Game.syncObject.notify();
			}
		}
	}
	
	/**
	 * Getterek.
	 */
	
	public ArrayList<MapElement> getMap(){
		return map;
	}
	
	/**
	 * Visszaadja azokat a mapElementeket, melyeken van spot.
	 * @param console: ha true, akkor ki is irja oket.					//Ha minden igaz ez csak a protohoz kellett
	 */
	/*
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
*/
	
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}
}
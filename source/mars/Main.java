package mars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
	
	/**
	 * br az XML fájlból való olvasás miatt.
	 * currentline az aktuális parancs sora.
	 */
	private static BufferedReader br;
	private static String currentLine;
	private static Game game;
	
	
	/**
	 * 
	 * @param in: beol
	 * @throws IOException: IO kivétel
	 */
	public static void init(InputStream in) throws IOException {
		br = new BufferedReader(new InputStreamReader(in));
		game = new Game(0);
	}
	
	
	/**
	 * Egy stringbõl csinál HeadDirection-t.
	 * @param s: Ezt a stringet alakítsuk át.
	 * @return Visszaadjuk a stringnek megfelelõ HeadDirection-t.
	 */
	public static HeadDirection setDirection(String s){
		if(s.equalsIgnoreCase("right"))
			return HeadDirection.RIGHT;
		else if(s.equalsIgnoreCase("up"))
			return HeadDirection.UP;
		else if(s.equalsIgnoreCase("left"))
			return HeadDirection.LEFT;
		else
			return HeadDirection.DOWN;
	}
	
	
	/**
	 * Megkeresi a paraméterben megadott nevû játékost a game-ben, és visszaadja.
	 * @param name: Keresendõ játékos neve.
	 * @return Megtalált játékos referenciája (vagy ha nincs, akkor null).
	 */
	public static Player getFindPlayer(String name){
		for (Player player : game.getPlayers()) {
			if(player.getName().equalsIgnoreCase(name))
				return player;
		}
		return null;
	}
	
	
	/**
	 * Megkeresi a paraméterben megadott nevû játékost a game-ben, és igazzal tér vissza, ha megtalálta.
	 * @param name: Keresendõ játékos neve.
	 * @return Találat esetén true, különben false.
	 */
	public static boolean findPlayer(String name){
		Boolean findEquals = false;
		for (Player player : game.getPlayers()) {
			if(player.getName().equalsIgnoreCase(name))
				findEquals = true;
		}
		return findEquals;
	}
	
	
	/**
	 * Parancsok kezelése.
	 * @return Igaz, ha sikeresen lefutott, különben false.
	 */
	public static boolean getNextCommand(){
		try {
			
			/**
			 * Betesszük a currentLineba az elsõ sort.
			 */
			if (currentLine == null) {
				currentLine = br.readLine();
			}
			
			/**
			 * Ha nem érvényes, kilép.
			 */
			if (currentLine == null)
				return false;
			
			/**
			 * words: Egy sorban lévõ parancsot, és a paramétereit tároló tömb. 
			 */
			String[] words = currentLine.split(" ");
			
			/**
			 * Ha nincs érték, akkor nem történik semmi.
			 */
			if (words.length < 1 ) {
				currentLine = null;
				return true;
			}
			
			/**
			 *  Ha #-gel kezdõdik, akkor az a sor komment, és kiíródik.
			 */
			if(words[0].charAt(0) == '#') {
				System.out.println(currentLine.substring(0,1));
				currentLine = null;
				return true;
			}
			
			/**
			 * Parancsok feldolgozása
			 */
			
			/**
			 * A megadott koordinátának megfelelõ kezdõpozícióra lerak egy megadott nevû új játékost,
			 *  a megadott kezdõiránnyal és egységnyi sebességgel
			 */
			if (words[0].equalsIgnoreCase("addPlayer") && words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "" &&  words.length > 3 && words[3] != ""){
				if(!findPlayer(words[1])){
					if(words[4].equalsIgnoreCase("up"))
							game.addPlayer(new Player(words[1], new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])), HeadDirection.UP));
					else if(words[4].equalsIgnoreCase("right"))
							game.addPlayer(new Player(words[1], new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])), HeadDirection.RIGHT));
					else if(words[4].equalsIgnoreCase("down"))
							game.addPlayer(new Player(words[1], new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])), HeadDirection.DOWN));
					else if(words[4].equalsIgnoreCase("left"))
							game.addPlayer(new Player(words[1], new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])), HeadDirection.LEFT));
				}
				else
					System.out.println("Ez a név már foglalt.");
			}
			
			/**
			 * A megadott koordinátának megfelelõ kezdõpozícióra lerak egy kis robotot, a megadott
			 *  kezdõiránnyal, illetve (megváltoztathatatlan) egységnyi sebességgel.
			 */
			else if (words[0].equalsIgnoreCase("addRobot") && words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "" &&  words.length > 3 && words[3] != "") {
				
				if(words[3].equalsIgnoreCase("up"))
					game.addMicroMachine(new MicroMachine(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), HeadDirection.UP));
				else if(words[3].equalsIgnoreCase("right"))
					game.addMicroMachine(new MicroMachine(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), HeadDirection.RIGHT));
				else if(words[3].equalsIgnoreCase("down"))
					game.addMicroMachine(new MicroMachine(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), HeadDirection.DOWN));
				else if(words[3].equalsIgnoreCase("left"))
					game.addMicroMachine(new MicroMachine(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), HeadDirection.LEFT));
			}
			
			/**
			 * A megadott koordinátának megfelelõ pályaelemre lerak egy megadott típusú foltot.
			 */
			else if (words[0].equalsIgnoreCase("addSpot") && words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "" &&  words.length > 3 && words[3] != "") {
				if(words[3].equalsIgnoreCase("oil"))
					game.getMapHandler().addSpot(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), new Oil());
				else if(words[3].equalsIgnoreCase("goo"))
					game.getMapHandler().addSpot(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), new Goo());
			}
			
			/**
			 * A játékos jelenlegi pozíciójára lerak egy megadott típusú foltot, miután a játékos tovább lépett.
			 */
			else if (words[0].equalsIgnoreCase("addSpotPlayer") &&  words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "") {
				if(findPlayer(words[1])){
					Player player = getFindPlayer(words[1]);
					if(words[2].equalsIgnoreCase("oil"))
						player.setSpotCommand(SpotCommand.OIL);
					else if(words[2].equalsIgnoreCase("goo"))
						player.setSpotCommand(SpotCommand.GOO);
				}
				else
					System.out.println("Nincs ilyen játékos.");
			}
			
			/**
			 * A megadott játékos irányát megváltoztatja a megadott értékre.
			 */
			else if (words[0].equalsIgnoreCase("changeDirection") && words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "") {
				if(findPlayer(words[1])){
					Player player = getFindPlayer(words[1]);
					if(words[2].equalsIgnoreCase("left"))				
						player.setHeadDirection(HeadDirection.LEFT);
					else if(words[2].equalsIgnoreCase("right"))
						player.setHeadDirection(HeadDirection.RIGHT);
					else if(words[2].equalsIgnoreCase("up"))
						player.setHeadDirection(HeadDirection.UP);
					else if(words[2].equalsIgnoreCase("down"))
						player.setHeadDirection(HeadDirection.DOWN);
				}
				else
					System.out.println("Nincs ilyen játékos.");
			}
			
			/**
			 * A megadott játékos sebességét megváltoztatja a megadott értékre.
			 */
			else if (words[0].equalsIgnoreCase("changeSpeed") && words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "") {
				if(findPlayer(words[1])){
					Player player = getFindPlayer(words[1]);
					player.setSpeed(Integer.parseInt(words[2]));
				}
				else
					System.out.println("Nincs ilyen játékos.");
			}
			
			/**
			 * Kilép a konzolból.
			 */
			else if (words[0].equalsIgnoreCase("exit")) {
				br.close();												
				System.exit(0);
			}
			
			/**
			 * Az eltelt idõ a megadott mennyiséggel nõ.
			 */
			else if (words[0].equalsIgnoreCase("incTime") && words.length > 1 && words[1] != "") {				
				for (MapElement mapElement : game.getMapHandler().getSpots(false)) {
					if(mapElement.getSpot().toString().equalsIgnoreCase("oil"))
						((Oil) mapElement.getSpot()).inc(Integer.parseInt(words[1]));						
				}
				game.getMapHandler().checkSpots();
			}
			
			/**
			 * Help kiírása.
			 */
			else if (words[0].equalsIgnoreCase("help")) {
				info();
			}
			
			/**
			 * Kiírja a képernyõre külön sorba a játékosok nevét, koordinátáit, irányát, sebességét, a 
			 * lerakható foltok számát, az oilflag értékét, illetve azt, hogy él-e még
			 */
			else if (words[0].equalsIgnoreCase("listPlayers")) {
				System.out.println("----------------------------------------------------------------------");
				if(game.getPlayers().size() == 0)
					System.out.println("Nincsenek játékosok a pályán.");
				else{
					for (Player player : game.getPlayers()) {
						if(player.isAlive())
							System.out.println(player.getName() + ", ("	+ player.getPosition().getX() + ";" + player.getPosition().getY() + "), " + player.getHeadDirection() + ", " + player.getSpeed() + ", " + player.getSpotCount() + ", " + player.getOilFlag() + ", alive");
						else
							System.out.println(player.getName() + ", (" + player.getPosition().getX() + ";" + player.getPosition().getY() + "), " + player.getHeadDirection() + ", " + player.getSpeed() + ", " + player.getSpotCount() + ", " + player.getOilFlag() + ", dead");
					}
				}
			}
			
			/**
			 * Kiírja a képernyõre soronként a kis robotok pozícióját, illetve aktuális irányát.
			 */
			else if (words[0].equalsIgnoreCase("listRobots")) {
				System.out.println("----------------------------------------------------------------------");
				for (int i = 0; i < game.getMicroMachine().size(); i++) {
					if(game.getMicroMachine().get(i).isAlive())
						System.out.println((i + 1) + "., (" + game.getMicroMachine().get(i).getPosition().getX() + ";" + game.getMicroMachine().get(i).getPosition().getY() + "), " + game.getMicroMachine().get(i).getDirection() + ", alive");
					else
						System.out.println((i + 1) + "., (" + game.getMicroMachine().get(i).getPosition().getX() + ";" + game.getMicroMachine().get(i).getPosition().getY() + "), " + game.getMicroMachine().get(i).getDirection() + " dead");

					
				}
			}
			
			/**
			 * Kilistázza a pályán található összes foltot, azok koordinátáját, fajtáját, illetve ha olaj,
			 *  akkor azt, hogy mennyien léptek rá(db), ha ragacs, akkor azt, hogy mennyi idõ telt el(sec).
			 */
			else if (words[0].equalsIgnoreCase("listSpots")) {
				System.out.println("----------------------------------------------------------------------");
				game.getMapHandler().getSpots(true);
			}
			
			/**
			 * A bementeten megadott nevû pályát betölti, ha létezik olyan, illetve kiírja, hogy sikeres volt-e a betöltés, különben kiírja, hogy sikertelen.
			 */
			else if (words[0].equalsIgnoreCase("loadMap") && words.length > 1 && words[1] != "") {
				if(game.getMapHandler().getMapName() == null)
					game.getMapHandler().loadMap(words[1]);
				else
					System.out.println("Van már pálya betöltve.");
			}
			
			/**
			 * Resetel mindent.
			 */
			else if (words[0].equalsIgnoreCase("reset")) {
				game = null;
				game = new Game(0);
			}
			
			/**
			 * Egy megadott játékosnak beállítja a pozícióját a megadott koordinátákra.
			 */
			else if (words[0].equalsIgnoreCase("setPlayerPosition") && words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "" &&  words.length > 3 && words[3] != "") {
				if(findPlayer(words[1])){
					Player player = getFindPlayer(words[1]);
					player.setPosition(new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])));
				}
				else
					System.out.println("Nincs ilyen játékos.");
			}
			
			/**
			 * Ütközésdetektálás indítása.
			 */
			else if (words[0].equalsIgnoreCase("startCollisions")) {
				game.getMapHandler().startCollisions();
			}
			
			/**
			 * Lép egyet a paraméterben megadott nevû játékos.
			 */
			else if (words[0].equalsIgnoreCase("stepPlayer") && words.length > 1 && words[1] != "") {
				if(findPlayer(words[1])){
					for (Player player : game.getPlayers()) {
						if(player.getName().equalsIgnoreCase(words[1])){
							player.testStep();
						}
					}
					game.getMapHandler().checkSpots();
				}
				else
					System.out.println("Nincs ilyen játékos.");
			}
			
			/**
			 * Lép egyet a paraméterben megadott indexû robot.
			 */
			else if (words[0].equalsIgnoreCase("stepRobot")) {
				for (MicroMachine robot : game.getMicroMachine()) {
					if(robot.getIndex() == Integer.parseInt(words[1])){
						game.getMapHandler().testMMCleaningStatus(robot);
						game.getMapHandler().setMMDirection(robot);
						robot.testupdate();
					}
				}
			}
			
			/**
			 * Hibás parancs kezelése.
			 */
			else
				System.out.println("Hibás parancs: " + words[0]);
		
		} catch (IOException e) {
			System.out.println("A bemeneti fájl olvasása nem sikerült!");
			return false;
		}
		
		currentLine = null;
		return true;
	}
	
	/**
	 * Információt ad a teszttel kapcsolatban, illetve kilistázza a használható parancsokat, leírásukat és használatuk módját.
	 */
	public static void info(){
		System.out.println("**********************************************************************");
		System.out.println("A kommenteket #-gel kell kezdeni.\n\n");
		System.out.println("Használható parancsok:\n");
		System.out.println("addPlayer: \n\t Leírás: Elhelyez egy új játékost a pályán \n\t Opciók: Az új játékos neve, pozíciója, iránya\n");
		System.out.println("addRobot: \n\t Leírás: Egy új kisrobot rakunk le a pályára \n\t Opciók: A kisrobot neve, és kezdõpozíciója\n");
		System.out.println("addSpot: \n\t Leírás: Folt lerakása \n\t Opciók: Lerakandó folt koordinátái, folt típusa\n");
		System.out.println("addSpotPlayer: \n\t Leírás: Folt lerakása játékos mögé \n\t Opciók: Játékos neve, folt típusa\n");
		System.out.println("changeDirection: \n\t Leírás: A megadott játékos irányát megváltoztatja \n\t Opciók: A játékos neve, új iránya\n");
		System.out.println("changeSpeed: \n\t Leírás: A megadott játékos sebességét megváltoztatja \n\t Opciók: A játékos neve, illetve sebességének iránya, nagysága\n");
		System.out.println("exit: \n\t Leírás: Kilép a programból \n\t Opciók: - \n");
		System.out.println("incTime: \n\t Leírás: Paraméterben megadott idõ eltelése \n\t Opciók: Idõ (sec)\n");
		System.out.println("help: \n\t Leírás: Kilistázza a használható parancsokat\n");
		System.out.println("listPlayers: \n\t Leírás: Kilistázza a játékosokat, és adataikat \n\t Opciók: - \n\t Kimenet: <név><koordináták><irány><sebesség><lerakható foltok száma><Oilflag><állapot(él-e)>\n");
		System.out.println("listRobots: \n\t Leírás: Kilistázza a kis robotok azonosítóját, pozícióját és irányát \n\t Opciók: - \n\t Kimenet: <azonosító><koordináták><irány>\n");
		System.out.println("listSpots: \n\t Leírás: Kilistázza a pályán lévõ foltokat, és pozíciójukat \n\t Opciók: - \n\t Kimenet: <koordináták><fajta(olaj, vagy ragacs)><ha ragacs:mennyien léptek rá(db), ha olaj, akkor mennyi idõ telt el(sec)>\n");
		System.out.println("loadMap: \n\t Leírás: Egy pálya betöltése \n\t Opciók: A betöltendõ pálya neve \n\t Kimenet: <sikeres/sikertelen>\n");
		System.out.println("reset: \n\t Leírás: Reseteli a pályát \n\t Opciók: - \n");
		System.out.println("setPlayerPosition: \n\t Leírás: Beállítja egy játékos pozícióját \n\t Opciók: Játékos neve, új koordinátái\n");
		System.out.println("startCollisions: \n\t Leírás: Ütközésdetektálás indítása. \n\t Opciók: -\n");
		System.out.println("stepPlayer: \n\t Leírás: Lép egyet a paraméterben megadott játékos. \n\t Opciók: játékos neve\n");
		System.out.println("stepRobot: \n\t Leírás: Lép egyet a paraméterben megadott robot. \n\t Opciók: Robot azonosítója\n");
		System.out.println("**********************************************************************");
	}
	
	public static void main(String[] args) throws IOException {
		
		/**
		 * Inicializálás.
		 */
		init(System.in);
		
		//info();
		
		/**
		 * Betöltjük elõre a pályát.
		 */
		game.getMapHandler().loadMap("test1.map");
		
		/**
		 * Várakozunk a beérkezõ parancsokra.
		 */
		while (getNextCommand()) {
			;
		}
	}

}

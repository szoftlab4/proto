package mars;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;


public class Main {
	private static BufferedWriter bw;
	private static BufferedReader br;
	private static String currentLine;
	private static Game game;
	
	//bemenet kimenet init
	public static void init(InputStream in, PrintStream out) throws IOException {
		br = new BufferedReader(new InputStreamReader(in)); // TODO filereader, writer...
		bw = new BufferedWriter(new OutputStreamWriter(out));
		game = new Game(0);
	}
	
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
	
	public static Player getFindPlayer(String name){
		for (Player player : game.getPlayers()) {
			if(player.getName().equalsIgnoreCase(name))
				return player;
		}
		return null;
	}
	
	public static boolean findPlayer(String name){
		Boolean findEquals = false;
		for (Player player : game.getPlayers()) {
			if(player.getName().equalsIgnoreCase(name))
				findEquals = true;
		}
		return findEquals;
	}
	
	public static boolean getNextCommand(){
		try {
			if (currentLine == null) {
				currentLine = br.readLine();
			}
			
			if (currentLine == null)
				return false;
			
			String[] words = currentLine.split(" ");
			
			try{
				// TODO kommentet ki�rni, h #-gel kezd�dj�n.....
				if (words[0].length() < 1 || words[0].charAt(0) == '#') {
					currentLine = null;
					return true;
				}
			}catch(Exception e){}
			
			// parancsok feldolgoz�sa
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
					System.out.println("Ez a n�v m�r foglalt.");
			}
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
			//k�sz
			else if (words[0].equalsIgnoreCase("addSpot") && words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "" &&  words.length > 3 && words[3] != "") {
				if(words[3].equalsIgnoreCase("oil"))
					game.getMapHandler().addSpot(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), new Oil());
				else if(words[3].equalsIgnoreCase("goo"))
					game.getMapHandler().addSpot(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), new Goo());
			}
			//k�sz
			else if (words[0].equalsIgnoreCase("addSpotPlayer") &&  words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "") {
				if(findPlayer(words[1])){
					Player player = getFindPlayer(words[1]);
					if(words[2].equalsIgnoreCase("oil"))
						player.setSpotCommand(SpotCommand.OIL);
					else if(words[2].equalsIgnoreCase("goo"))
						player.setSpotCommand(SpotCommand.GOO);
				}
				else
					System.out.println("Nincs ilyen j�t�kos.");
			}
			//k�sz
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
					System.out.println("Nincs ilyen j�t�kos.");
			}
			//k�sz
			else if (words[0].equalsIgnoreCase("changeSpeed") && words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "") {
				if(findPlayer(words[1])){
					Player player = getFindPlayer(words[1]);
					player.setSpeed(Integer.parseInt(words[2]));
				}
				else
					System.out.println("Nincs ilyen j�t�kos.");
			}
			
			//k�sz
			else if (words[0].equalsIgnoreCase("exit")) {
				br.close();												
				bw.close();
				System.exit(0);
			}
			
			else if (words[0].equalsIgnoreCase("incTime") && words.length > 1 && words[1] != "") {				
				for (MapElement mapElement : game.getMapHandler().getSpots(false)) {
					if(mapElement.getSpot().toString().equalsIgnoreCase("oil"))
						((Oil) mapElement.getSpot()).inc(Integer.parseInt(words[1]));						
				}
				game.getMapHandler().checkSpots();
			}
			else if (words[0].equalsIgnoreCase("help")) {
				info();
			}
			else if (words[0].equalsIgnoreCase("listPlayers")) {
				System.out.println("----------------------------------------------------------------------");
				if(game.getPlayers().size() == 0)
					System.out.println("Nincsenek j�t�kosok a p�ly�n.");
				else{
					for (Player player : game.getPlayers()) {
						if(player.isAlive())
							System.out.println(player.getName() + ", ("	+ player.getPosition().getX() + ";" + player.getPosition().getY() + "), " + player.getHeadDirection() + ", " + player.getSpeed() + ", " + player.getSpotCount() + ", " + player.getOilFlag() + ", alive");
						else
							System.out.println(player.getName() + ", (" + player.getPosition().getX() + ";" + player.getPosition().getY() + "), " + player.getHeadDirection() + ", " + player.getSpeed() + ", " + player.getSpotCount() + ", " + player.getOilFlag() + ", dead");
					}
				}
			}
			else if (words[0].equalsIgnoreCase("listRobots")) {
				System.out.println("----------------------------------------------------------------------");
				for (int i = 0; i < game.getMicroMachine().size(); i++) {
					System.out.println((i + 1) + "., (" + game.getMicroMachine().get(i).getPosition().getX() + ";" + game.getMicroMachine().get(i).getPosition().getY() + "), " + game.getMicroMachine().get(i).getDirection());
				}
			}
			else if (words[0].equalsIgnoreCase("listSpots")) {
				System.out.println("----------------------------------------------------------------------");
				game.getMapHandler().getSpots(true);
			}
			else if (words[0].equalsIgnoreCase("loadMap") && words.length > 1 && words[1] != "") {
				if(game.getMapHandler().getMapName() == null)
					game.getMapHandler().loadMap(words[1]);
				else
					System.out.println("Van m�r p�lya bet�ltve.");
			}
			else if (words[0].equalsIgnoreCase("reset")) {
				game = null;
				game = new Game(0);
			}
			//k�sz
			else if (words[0].equalsIgnoreCase("setPlayerPosition") && words.length > 1 && words[1] != "" && words.length > 2 && words[2] != "" &&  words.length > 3 && words[3] != "") {
				if(findPlayer(words[1])){
					Player player = getFindPlayer(words[1]);
					player.setPosition(new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])));
				}
				else
					System.out.println("Nincs ilyen j�t�kos.");
			}
			else if (words[0].equalsIgnoreCase("startCollisions")) {
				game.getMapHandler().startCollisions();
			}
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
					System.out.println("Nincs ilyen j�t�kos.");
			}
			else if (words[0].equalsIgnoreCase("stepRobot")) {
				for (MicroMachine robot : game.getMicroMachine()) {
					if(robot.getIndex() == Integer.parseInt(words[1])){
						game.getMapHandler().testMMCleaningStatus(robot);
						game.getMapHandler().setMMDirection(robot);
						robot.testupdate();
					}
				}
			}
			else
				System.out.println("Hib�s parancs: " + words[0]);
		
		} catch (IOException e) {
			System.out.println("A bemeneti f�jl olvas�sa nem siker�lt!");
			return false;
		}
		currentLine = null;
		return true;
	}
	
	public static void info(){
		System.out.println("**********************************************************************");
		System.out.println("Haszn�lhat� parancsok:\n");
		System.out.println("addPlayer: \n\t Le�r�s: Elhelyez egy �j j�t�kost a p�ly�n \n\t Opci�k: Az �j j�t�kos neve, poz�ci�ja, ir�nya\n");
		System.out.println("addRobot: \n\t Le�r�s: Egy �j kisrobot rakunk le a p�ly�ra \n\t Opci�k: A kisrobot neve, �s kezd�poz�ci�ja\n");
		System.out.println("addSpot: \n\t Le�r�s: Folt lerak�sa \n\t Opci�k: Lerakand� folt koordin�t�i, folt t�pusa\n");
		System.out.println("addSpotPlayer: \n\t Le�r�s: Folt lerak�sa j�t�kos m�g� \n\t Opci�k: J�t�kos neve, folt t�pusa\n");
		System.out.println("changeDirection: \n\t Le�r�s: A megadott j�t�kos ir�ny�t megv�ltoztatja \n\t Opci�k: A j�t�kos neve, �j ir�nya\n");
		System.out.println("changeSpeed: \n\t Le�r�s: A megadott j�t�kos sebess�g�t megv�ltoztatja \n\t Opci�k: A j�t�kos neve, illetve sebess�g�nek ir�nya, nagys�ga\n");
		System.out.println("exit: \n\t Le�r�s: Kil�p a programb�l \n\t Opci�k: - \n");
		System.out.println("incTime: \n\t Le�r�s: Param�terben megadott id� eltel�se \n\t Opci�k: Id� (sec)\n");
		System.out.println("help: \n\t Le�r�s: Kilist�zza a haszn�lhat� parancsokat\n");
		System.out.println("listPlayers: \n\t Le�r�s: Kilist�zza a j�t�kosokat, �s adataikat \n\t Opci�k: - \n\t Kimenet: <n�v><koordin�t�k><ir�ny><sebess�g><lerakhat� foltok sz�ma><Oilflag><�llapot(�l-e)>\n");
		System.out.println("listRobots: \n\t Le�r�s: Kilist�zza a kis robotok azonos�t�j�t, poz�ci�j�t �s ir�ny�t \n\t Opci�k: - \n\t Kimenet: <azonos�t�><koordin�t�k><ir�ny>\n");
		System.out.println("listSpots: \n\t Le�r�s: Kilist�zza a p�ly�n l�v� foltokat, �s poz�ci�jukat \n\t Opci�k: - \n\t Kimenet: <koordin�t�k><fajta(olaj, vagy ragacs)><ha ragacs:mennyien l�ptek r�(db), ha olaj, akkor mennyi id� telt el(sec)>\n");
		System.out.println("loadMap: \n\t Le�r�s: Egy p�lya bet�lt�se \n\t Opci�k: A bet�ltend� p�lya neve \n\t Kimenet: <sikeres/sikertelen>\n");
		System.out.println("reset: \n\t Le�r�s: Reseteli a p�ly�t \n\t Opci�k: - \n");
		System.out.println("setPlayerPosition: \n\t Le�r�s: Be�ll�tja egy j�t�kos poz�ci�j�t \n\t Opci�k: J�t�kos neve, �j koordin�t�i\n");
		System.out.println("startCollisions: \n\t Le�r�s: �tk�z�sdetekt�l�s ind�t�sa. \n\t Opci�k: -\n");
		System.out.println("stepPlayer: \n\t Le�r�s: L�p egyet a param�terben megadott j�t�kos. \n\t Opci�k: j�t�kos neve\n");
		System.out.println("stepRobot: \n\t Le�r�s: L�p egyet a param�terben megadott robot. \n\t Opci�k: Robot azonos�t�ja\n");
		System.out.println("**********************************************************************");
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		init(System.in, System.out);
		
		//info();
		game.getMapHandler().loadMap("test1.map");
		
		while (getNextCommand()) {
			;
		}
	}

}

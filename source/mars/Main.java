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
	
	public static Player findPlayer(String name){
		for (Player player : game.getPlayers()) {
			if(player.getName().equalsIgnoreCase(name))
				return player;
		}
		return null;
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
				// TODO kommentet kiírni, h #-gel kezdõdjön.....
				if (words[0].length() < 1 || words[0].charAt(0) == '#') {
					currentLine = null;
					return true;
				}
			}catch(Exception e){}
			
			// parancsok feldolgozása
			if (words[0].equalsIgnoreCase("addPlayer")) {
				if(words[4].equalsIgnoreCase("up"))
						game.addPlayer(new Player(words[1], new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])), HeadDirection.UP));
				else if(words[4].equalsIgnoreCase("right"))
						game.addPlayer(new Player(words[1], new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])), HeadDirection.RIGHT));
				else if(words[4].equalsIgnoreCase("down"))
						game.addPlayer(new Player(words[1], new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])), HeadDirection.DOWN));
				else if(words[4].equalsIgnoreCase("left"))
						game.addPlayer(new Player(words[1], new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])), HeadDirection.LEFT));
			}
			else if (words[0].equalsIgnoreCase("addRobot")) {
				
				if(words[3].equalsIgnoreCase("up"))
					game.addMicroMachine(new MicroMachine(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), HeadDirection.UP));
				else if(words[3].equalsIgnoreCase("right"))
					game.addMicroMachine(new MicroMachine(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), HeadDirection.RIGHT));
				else if(words[3].equalsIgnoreCase("down"))
					game.addMicroMachine(new MicroMachine(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), HeadDirection.DOWN));
				else if(words[3].equalsIgnoreCase("left"))
					game.addMicroMachine(new MicroMachine(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), HeadDirection.LEFT));
			}
			//kész
			else if (words[0].equalsIgnoreCase("addSpot")) {
				if(words[3].equalsIgnoreCase("oil"))
					game.getMapHandler().addSpot(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), new Oil());
				else if(words[3].equalsIgnoreCase("goo"))
					game.getMapHandler().addSpot(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), new Goo());
			}
			//kész
			else if (words[0].equalsIgnoreCase("addSpotPlayer")) {
				Player player = findPlayer(words[1]);
				if(words[2].equalsIgnoreCase("oil"))
					player.putOilSpot();
				else if(words[2].equalsIgnoreCase("goo"))
					player.putGooSpot();
			}
			//kész
			else if (words[0].equalsIgnoreCase("changeDirection")) {
				Player player = findPlayer(words[1]);
				if(words[2].equalsIgnoreCase("left"))				
					player.setHeadDirection(HeadDirection.LEFT);
				else if(words[2].equalsIgnoreCase("right"))
					player.setHeadDirection(HeadDirection.RIGHT);
				else if(words[2].equalsIgnoreCase("up"))
					player.setHeadDirection(HeadDirection.UP);
				else if(words[2].equalsIgnoreCase("down"))
					player.setHeadDirection(HeadDirection.DOWN);
			}
			
			
			//kész
			else if (words[0].equalsIgnoreCase("changeSpeed")) {
				
				Player player = findPlayer(words[1]);
				/*if(words[2].equalsIgnoreCase("forward"))				
					player.setSpeed(player.getSpeed() + 1);
				else if(words[2].equalsIgnoreCase("backward") && player.getSpeed() > 1)
					player.setSpeed(player.getSpeed() - 1);*/
				player.setSpeed(Integer.parseInt(words[2]));
			}
			
			
			//kész
			else if (words[0].equalsIgnoreCase("exit")) {
				br.close();												
				bw.close();
				System.exit(0);
			}
			
			else if (words[0].equalsIgnoreCase("incTime")) {				
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
				for (Player player : game.getPlayers()) {
					if(player.isAlive())
						System.out.println(player.getName() + ", ("	+ player.getPosition().getX() + ";" + player.getPosition().getY() + "), " + player.getHeadDirection() + ", " + player.getSpeed() + ", " + player.getSpotCount() + ", alive");
					else
						System.out.println(player.getName() + ", (" + player.getPosition().getX() + ";" + player.getPosition().getY() + "), " + player.getHeadDirection() + ", " + player.getSpeed() + ", " + player.getSpotCount() + ", dead");
				}
			}
			else if (words[0].equalsIgnoreCase("listRobots")) {
				System.out.println("----------------------------------------------------------------------");
				for (int i = 0; i < game.getMicroMachine().size(); i++) {
					System.out.println((i + 1) + "., " + game.getMicroMachine().get(i).getPosition().getX() + ";" + game.getMicroMachine().get(i).getPosition().getX() + "), " + game.getMicroMachine().get(i).getDirection());
				}
			}
			else if (words[0].equalsIgnoreCase("listSpots")) {
				System.out.println("----------------------------------------------------------------------");
				game.getMapHandler().getSpots(true);
			}
			else if (words[0].equalsIgnoreCase("loadMap")) {
				game.getMapHandler().loadMap(words[1]);
			}
			else if (words[0].equalsIgnoreCase("reset")) {
				game = null;
				game = new Game(0);
			}
			//kész
			else if (words[0].equalsIgnoreCase("setPlayerPosition")) {
				Player player = findPlayer(words[1]);
				player.setPosition(new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])));
			}
			else if (words[0].equalsIgnoreCase("stepPlayer")) {
				for (Player player : game.getPlayers()) {
					if(player.getName().equalsIgnoreCase(words[1]) && player.isAlive()){
						player.testStep();
						game.getMapHandler().checkPosition(player);
					}
				}
			}
			else if (words[0].equalsIgnoreCase("stepRobot")) {
				for (MicroMachine robot : game.getMicroMachine()) {
					if(robot.getIndex() == Integer.parseInt(words[1]))
						robot.step();
				}
			}
			else
				System.out.println("Hibás parancs: " + words[0]);
		
		} catch (IOException e) {
			System.out.println("A bemeneti fájl olvasása nem sikerült!");
			return false;
		}
		currentLine = null;
		return true;
	}
	
	public static void info(){
		System.out.println("**********************************************************************");
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
		System.out.println("listPlayers: \n\t Leírás: Kilistázza a játékosokat, és adataikat \n\t Opciók: - \n\t Kimenet: <név><koordináták><irány><sebesség><lerakható foltok száma><állapot(él-e)>\n");
		System.out.println("listRobots: \n\t Leírás: Kilistázza a kis robotok azonosítóját, pozícióját és irányát \n\t Opciók: - \n\t Kimenet: <azonosító><koordináták><irány>\n");
		System.out.println("listSpots: \n\t Leírás: Kilistázza a pályán lévõ foltokat, és pozíciójukat \n\t Opciók: - \n\t Kimenet: <koordináták><fajta(olaj, vagy ragacs)><ha ragacs:mennyien léptek rá(db), ha olaj, akkor mennyi idõ telt el(sec)>\n");
		System.out.println("loadMap: \n\t Leírás: Egy pálya betöltése \n\t Opciók: A betöltendõ pálya neve \n\t Kimenet: <sikeres/sikertelen>\n");
		System.out.println("reset: \n\t Leírás: Reseteli a pályát \n\t Opciók: - \n");
		System.out.println("setPlayerPosition: \n\t Leírás: Beállítja egy játékos pozícióját \n\t Opciók: Játékos neve, új koordinátái\n");
		System.out.println("stepPlayer: \n\t Leírás: Lép egyet a paraméterben megadott játékos. \n\t Opciók: játékos neve\n");
		System.out.println("stepRobot: \n\t Leírás: Lép egyet a paraméterben megadott robot. \n\t Opciók: Robot azonosítója\n");
		System.out.println("**********************************************************************");
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		init(System.in, System.out);
		
		info();
		
		while (getNextCommand()) {
			;
		}
	}

}

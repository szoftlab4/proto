package mars;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
	private static int time;
	
	//bemenet kimenet init
	public static void init(InputStream in, PrintStream out) throws IOException {
		br = new BufferedReader(new InputStreamReader(in)); // TODO filereader, writer...
		bw = new BufferedWriter(new OutputStreamWriter(out));
		game = new Game();
		time = 0;
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
	
	public static boolean getNextCommand(){
		try {
			if (currentLine == null) {
				currentLine = br.readLine();
			}
			
			if (currentLine == null)
				return false;
			
			String[] words = currentLine.split(" ");
			
			// TODO kommentet kiírni, h #-gel kezdõdjön.....
			if (words.length < 1 || words[0].charAt(0) == '#') {
				currentLine = null;
				return true;
			}
			
			// parancsok feldolgozása
			if (words[0].equalsIgnoreCase("addPlayer")) {
				game.addPlayer(new Player(words[1], new Position(Integer.parseInt(words[2]), Integer.parseInt(words[3])), setDirection(words[4])));
			}
			else if (words[0].equalsIgnoreCase("addRobot")) {
				game.addMicroMachine(new MicroMachine(new Position(Integer.parseInt(words[1]), Integer.parseInt(words[2])), setDirection(words[3])));
			}
			else if (words[0].equalsIgnoreCase("addSpot")) {
				
			}
			else if (words[0].equalsIgnoreCase("addSpotPlayer")) {
				
			}
			else if (words[0].equalsIgnoreCase("changeDirection")) {
				
			}
			else if (words[0].equalsIgnoreCase("changeSpeed")) {
				
			}
			else if (words[0].equalsIgnoreCase("exit")) {
				br.close();												
				bw.close();
				System.exit(0);
			}
			else if (words[0].equalsIgnoreCase("incTime")) {
				
			}
			else if (words[0].equalsIgnoreCase("listPlayers")) {
				
			}
			else if (words[0].equalsIgnoreCase("listRobots")) {
				
			}
			else if (words[0].equalsIgnoreCase("listSpots")) {
				
			}
			else if (words[0].equalsIgnoreCase("loadMap")) {
				
			}
			else if (words[0].equalsIgnoreCase("reset")) {
				
			}
			else if (words[0].equalsIgnoreCase("setPlayerPosition")) {
				
			}
			else if (words[0].equalsIgnoreCase("step")) {
				
			}
			else
				System.out.println("Hibás parancs: " + words[0]);
				
			
			//System.out.println(words[0] + " " + words[1] + " " + words[2]);
		
		} catch (IOException e) {
			System.out.println("A bemeneti fájl olvasása nem sikerült!");
			return false;
		}
		currentLine = null;
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		init(System.in, System.out);
		
		while (getNextCommand()) {
			;
		}
	}

}

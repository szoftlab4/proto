package mars;

public class Main {

	public static void main(String[] args){
		Game game = new Game(2);
		Controller controller = new Controller();
		
		game.addController(controller);
		game.start();
	}

}

package mars;

public class Main {

	public static void main(String[] args){
		Game game = new Game(1);
		Controller controller = new Controller();
		
		game.addController(controller);
		controller.addGameReference(game);
		
		game.start();
	}

}

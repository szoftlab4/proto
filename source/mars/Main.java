package mars;

public class Main {
	static Highscore highscore;
	
	public static void main(String[] args){
		View view = new View();
		Controller controller = new Controller();
		highscore = new Highscore();
		controller.addView(view);
		view.addController(controller);
		view.drawMenu();
	}

}

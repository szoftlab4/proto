package mars;

public class Main {
	static Highscore highscore;	// TODO mb
	
	public static void main(String[] args){
		View view = new View();
		Controller controller = new Controller();
		controller.addView(view);
		view.addController(controller);
		view.drawMenu();
		
		
		highscore = new Highscore();
		
		//teszt
		highscore.add("asd",15);
		highscore.add("asd",2);
		highscore.add("asd",5);
		highscore.add("asd",10);
		highscore.add("asd",9);
		highscore.add("asd",5);
		highscore.add("asd",4);
	}

}

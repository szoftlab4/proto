package mars;

public class Main {

	public static void main(String[] args){
		View view = new View();
		Controller controller = new Controller();
		controller.addView(view);
		view.addController(controller);
		view.drawMenu();
		
		
		
	}

}

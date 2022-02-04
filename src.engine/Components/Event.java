package Components;

public class Event {
	
	public static void callEvent(String event) {
		
		switch (event) {
		
		case "leftMouseButtonDown":
			for(Component c: Component.getComponents())
				c.leftMouseButtonDown();
			break;
			
		case "leftMouseButtonUp":
			for(Component c: Component.getComponents())
				c.leftMouseButtonUp();
			break;
			
		case "leftMouseButtonClicked":
			for(Component c: Component.getComponents())
				c.leftMouseButtonClicked();
			break;
			
		case "rightMouseButtonDown":
			for(Component c: Component.getComponents())
				c.rightMouseButtonDown();
			break;
			
		case "rightMouseButtonUp":
			for(Component c: Component.getComponents())
				c.rightMouseButtonUp();
			break;
			
		case "rightMouseButtonClicked":
			for(Component c: Component.getComponents())
				c.rightMouseButtonClicked();
			break;
			
		
		}
			
		
	}

}

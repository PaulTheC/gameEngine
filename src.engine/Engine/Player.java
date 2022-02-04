package Engine;

import Camera.Camera;
import Lights.Light;
import Player.PlayerActionHandler;
import Player.PlayerMovement;

public class Player {
	
	private static Camera cam;
	private static Light light;
	
	public static void createPlayer() {
		
		cam = new Camera();
		cam.addComponent(new PlayerMovement());
		cam.addComponent(new PlayerActionHandler());
		
		
		light = new Light();
		light.setStrength(3);
		light.setBrightness(0.3f);

		
	}
	
	public static Camera getCamera() {
		return cam;
	}

}

package Camera;

public class MainCamera {
	
	private static Camera cam;
	
	public static void init() {
		cam = new Camera();
	}
	
	
	public static void onUpdate() {
		cam.move();
	}
	
	public static Camera getCamera() {
		return cam;
	}

}

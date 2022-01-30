package Scenes;

import java.util.ArrayList;

public class SceneManager{
	
	private static String activeScene = "testScene";
	private static ArrayList<Scene> scenes = new ArrayList<>();
	
	
	public static <T extends Scene> T getScene(String name) {
		for(Scene scene: scenes) {
			if(scene.getName().equals(name))
				return (T) scene;
		}
		
		
		System.err.println("/nThe scene " + name+ " does not exist!/n");
		return null;	
	}

	
	public static void setActiveScene(String name) {
		activeScene = name;
	}
	
	
	public static void addScene(Scene scene) {
		scenes.add(scene);
	}
	

	@SuppressWarnings("unchecked")
	public static <T extends Scene> T getActiveScene() {
		return (T)(getScene(activeScene));
	}
	
	
}

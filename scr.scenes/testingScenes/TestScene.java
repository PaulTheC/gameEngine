package testingScenes;

import org.lwjgl.util.vector.Vector3f;

import Components.Component;
import Lamps.Lantern;
import Lights.Sun;
import Movements.SunMovement;
import Physics.Rigidbody;
import Scenes.Scene;
import Scenes.SceneManager;
import Terrain.Map;
import Utilities.Time;

public class TestScene extends Scene{
	
	public static Map map;
	public static Sun sun;


	public TestScene() {
		super("testScene");

		
		map = new Map();
		sun = new Sun();
		
		Lantern lantern = new Lantern();
		lantern.increasePosition(1000, map.getHeightOfTerrain(1000, 200)+1, 200);
		
		Lantern lantern2 = new Lantern();
		lantern2.increasePosition(200, map.getHeightOfTerrain(200, 200)+1, 200);
		
		
		lantern.addComponent(new Rigidbody());

		
	}
}

package testingScenes;

import org.lwjgl.util.vector.Vector3f;

import Components.Component;
import EntityPresets.Cube;
import Entiys.Entity;
import Lamps.Lantern;
import Lights.Sun;
import Movements.SunMovement;
import Physics.Rigidbody;
import Scenes.Scene;
import Scenes.SceneManager;
import Terrain.Map;
import UIElements.UIElement;
import UIShader.UIShader;
import Utilities.Time;

public class TestScene extends Scene{
	
	public static Map map;
	public static Sun sun;
	public static Lantern lantern;
	public static Entity cube;


	public TestScene() {
		super("testScene");

		UIShader uiShader = new UIShader();
		
		map = new Map();
		
		sun = new Sun();
		sun.addComponent(new SunMovement());
		
		cube = new Entity("cube");
		cube.setScale(0.2f);
		
		

//		lantern = new Lantern();
//		lantern.increasePosition(1000, map.getHeightOfTerrain(300, 200)+1, 200);
//		
//		Lantern lantern2 = new Lantern();
//		lantern2.increasePosition(200, map.getHeightOfTerrain(200, 200)+1, 200);
		
		UIElement crosshair = new UIElement("circle", uiShader);
		crosshair.setScale(3);
		crosshair.increasePosition(-crosshair.getModel().getTexture().getWidth() / crosshair.getScale() / 2, -crosshair.getModel().getTexture().getHeight() /crosshair.getScale() /2, 0);
		
		Entity dummy = new Entity("entitys/enemys/dummy/dummy", "entitys/enemys/dummy/dummy");
		dummy.increasePosition(14, map.getHeightOfTerrain(14, 14)+1, 14);
		
		
	}
}

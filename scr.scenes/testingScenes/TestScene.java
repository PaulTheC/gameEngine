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
		cube = new Entity("cube");

		lantern = new Lantern();
		lantern.increasePosition(1000, map.getHeightOfTerrain(300, 200)+1, 200);
		
		Lantern lantern2 = new Lantern();
		lantern2.increasePosition(200, map.getHeightOfTerrain(200, 200)+1, 200);
		
		sun.addComponent(new SunMovement());
		
		
		UIElement crosshair = new UIElement("circle", uiShader);
		crosshair.setScale(3);
		crosshair.increasePosition(-crosshair.getModel().getTexture().getWidth() / crosshair.getScale() / 2, -crosshair.getModel().getTexture().getHeight() /crosshair.getScale() /2, 0);
		
	}
}

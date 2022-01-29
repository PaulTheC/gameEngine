package Engine;

import java.awt.geom.FlatteningPathIterator;
import java.io.IOException;
import java.util.Random;

import javax.xml.stream.events.StartDocument;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import EntityPresets.Cube;
import EntityPresets.Plane;
import EntityShader.EnitiyShader;
import Entiys.Camera;
import Entiys.Entity;
import Entiys.EntityMaster;
import Entiys.Lamp;
import Entiys.UIElement;
import Lamps.Lantern;
import Lights.Light;
import Lights.Sun;
import Loader.Loader;
import MainShader.StaticShader;
import Materials.DefaultMaterial;
import Materials.StaticMaterial;
import Materials.UIMaterial;
import Models.RawModel;
import Models.TexturedModel;
import Terrain.Map;
import Terrain.TerrainArea;
import TerrainShader.TerrainShader;
import Textures.ModelTexture;
import UIShader.UIShader;

public class Main {
	
	private static long lastTime;
	public static float deltaTime;
	public static int FPS;
	private static int FPSCount;
	private static float secondsTimer;
	
	public static float daytime;
	
	public static StaticShader shader;
	public static DefaultMaterial material;
	
	public static Camera camera;
	public static Map map;
	
	public static Sun sun = new Sun();
	
	//public static Light[] lights = new Light[3];

	public static void main(String[] args) throws IOException {

		DisplayManager.createDisplay();
		camera = new Camera();
		Loader loader = new Loader();
		shader = new EnitiyShader();
		UIShader uiShader = new UIShader();
		Renderer renderer = new Renderer(camera);
		material = new DefaultMaterial();
		
		lastTime = System.currentTimeMillis();
		
		Random r = new Random();
		
//		lights[0] = new Light(new Vector3f(10, 10, 0), 0.1f);
//		lights[1] = new Light(new Vector3f(-100, 10000, 50), new Vector3f(0,0,1));

		map = new Map();
		
		Lantern lantern = new Lantern();
		lantern.increasePosition(1000, map.getHeightOfTerrain(1000, 200)+1, 200);
		
		Lantern lantern2 = new Lantern();
		lantern2.increasePosition(200, map.getHeightOfTerrain(200, 200)+1, 200);
		
		
		
	
		
		try {
			Mouse.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mouse.setGrabbed(true);
		
		float time = 0;
		
		while(!Display.isCloseRequested()){
			time += deltaTime * 0.3f;
			daytime = (float)Math.cos((double)time);

			sun.setPosition(new Vector3f(0, (float)Math.sin((double)time) * 1000f, (float)Math.cos((double)time) * 1000f));
			
			
			
			MouseHandler.mouseUpdate();
			camera.move();
			
			
			
			//render
			renderer.prepare();
			renderer.render();
			
			//update Display
			DisplayManager.resizeDisplay(camera, renderer, shader);
			DisplayManager.updateDisplay();
			
			//analysing
			fpsstuff();
		}

		Mouse.destroy();
		
		shader.destroy();
		loader.cleanUp();
		DisplayManager.closeDisplay();

		System.exit(0);
		
	}
	
	private static void fpsstuff() {
		deltaTime = (float) ((System.currentTimeMillis() - lastTime)*0.001);
		lastTime = System.currentTimeMillis();
		FPS = (int) (1/deltaTime);

		if(deltaTime < 0.001)
			deltaTime = 0.00001f;

		
		
		//count FPS
		secondsTimer += deltaTime;
		FPSCount++;
		if(secondsTimer > 1) {
			System.out.println("FPS: "+FPSCount);
			FPSCount = 0;
			secondsTimer = 0;
		}
		
		
	}
	
	
	public static Camera getCamera() {
		return camera;
	}
	
	
}

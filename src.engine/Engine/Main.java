package Engine;

import java.awt.geom.FlatteningPathIterator;
import java.io.IOException;
import java.util.Random;

import javax.xml.stream.events.StartDocument;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import EntityPresets.Cube;
import EntityPresets.Plane;
import EntityShader.EnitiyShader;
import Entiys.Entity;
import Entiys.EntityMaster;
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
import Scenes.Scene;
import Scenes.SceneManager;
import Terrain.Map;
import Terrain.TerrainArea;
import TerrainShader.TerrainShader;
import Textures.ModelTexture;
import UIElements.UIElement;
import UIShader.UIShader;
import Utilities.MouseHandler;
import Utilities.Time;
import testingScenes.TestScene;

public class Main {
	
	
	public static StaticShader shader;
	public static DefaultMaterial material;
	
	//public static Light[] lights = new Light[3];

	public static void main(String[] args) throws IOException {
		
		DisplayManager.createDisplay();
		MouseHandler.initMouse();
		Time.init();
		
		
		shader = new EnitiyShader();
		Renderer renderer = new Renderer(Player.getCamera());
		material = new DefaultMaterial();

	
		new TestScene();
		
		Player.createPlayer();
		
		
		SceneManager.getActiveScene().onStart();
		
		while(!DisplayManager.isCloseRequested()){
			
			SceneManager.getActiveScene().onUpdate();
			

			MouseHandler.mouseUpdate();
			
			
			
			//render
			renderer.prepare();
			renderer.render();
			
			//update Display
			DisplayManager.resizeDisplay(Player.getCamera(), renderer, shader);
			DisplayManager.updateDisplay();
			
			//analysing
			Time.onUpdate();
		}

		
		shader.destroy();
		Loader.cleanUp();
		DisplayManager.closeDisplay();
		MouseHandler.onDestory();
		Time.onDestroy();

		System.exit(0);
		
	}
	
}

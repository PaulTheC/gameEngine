package Entiys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import Models.TexturedModel;
import Scenes.Scene;
import Scenes.SceneManager;

public class EntityMaster {
	
	public static void renderAllEntitys(Scene scene) {

		for(Entity e : scene.getEntitys()) {
			e.getShader().prepare(e);
			e.getShader().render(e);
			e.getShader().cleanUp(e);
		}
	}
	
	public static void addEntity(Entity entity) {
		SceneManager.getActiveScene().addEntity(entity);
	}
	
}

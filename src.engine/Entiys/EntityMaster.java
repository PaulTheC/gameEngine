package Entiys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import Models.TexturedModel;

public class EntityMaster {

	private static HashMap<String, Entity> allEntitys = new HashMap<>();
	private static HashMap<TexturedModel, ArrayList<Entity>> sortedEntitys =  new HashMap();
	private static ArrayList<Entity> entitys = new ArrayList<>();

	public static ArrayList<Entity> getAllEntitys() {
		ArrayList<Entity> perm = new ArrayList<>();
		for (String key: allEntitys.keySet()) {
			perm.add(allEntitys.get(key));
		}
		return perm;
	}
	
	public static void addEntity(Entity entity, String key) {
		allEntitys.put(key, entity);
		ArrayList<Entity> list = new ArrayList<>();
		if(sortedEntitys.containsKey(entity.getModel()))
			list = sortedEntitys.get(entity.getModel());
		
		list.add(entity);
		
		sortedEntitys.put(entity.getModel(), list);
		
		entitys.add(entity);
	}
	
	public static void addEntity(Entity entity) {
		String key = "number_";
		Random r = new Random();
		while(allEntitys.keySet().contains(key)) {
			key=key+r.nextInt();
		}
		
		addEntity(entity, key);
		
	}
	
	public static void renderAllEntitys() {

//			
//			
//		for(TexturedModel key: sortedEntitys.keySet()) {
//			Entity entity = sortedEntitys.get(key).get(0);
//			
//			entity.getShader().prepare(entity);
//			
//			
//			for(Entity target: sortedEntitys.get(key)) {
//
//				System.out.println(entity.getModel().getRawModel().getFilename());
//				target.getShader().render(target);
//
//			}
//			
//			
//			entity.getShader().cleanUp(entity);
//			
//		}
		
		
		for(Entity e : entitys) {
			e.getShader().prepare(e);
			e.getShader().render(e);
			e.getShader().cleanUp(e);
		}
	}
	
	
	public static Entity getEntityWithKey(String key) {
		return allEntitys.get(key);
	}
	
}

package Scenes;

import java.util.ArrayList;

import Entiys.Entity;

public abstract class Scene{
	
	protected String name;
	private ArrayList<Entity> entitys = new ArrayList<>(); 
	
	
	public Scene(String name) {
		this.name = name;
		SceneManager.addScene(this);
		SceneManager.setActiveScene(name);
	}
	
	public void onUpdate() {
		System.out.println(entitys);
		for(Entity e: entitys) 
			e.onUpdate();
	}
	
	public void onStart() {
		for(Entity e: entitys) 
			e.onStart();
	}
	
	
	public ArrayList<Entity> getEntitys() {
		return entitys;
	}
	
	public void addEntity(Entity entity) {
		entitys.add(entity);
	}
	
	public String getName() {
		return name;
	}	

}

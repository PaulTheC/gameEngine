package Components;

import java.util.ArrayList;

import Entiys.Entity;

public abstract class Component{
	
	
	//Components get added to this list by the entity class
	protected static ArrayList<Component> components = new ArrayList<>();
	
	//this will stop the component from excecuting the onUpdate function
	protected boolean isActive = true;
	
	public void initialise() {
		components.add(this);
	}
	
	public void disable() {
		isActive = false;
	}
	
	public void enable() {
		isActive = true;
	}
	
	public boolean isAvcive() {
		return isActive;
	}
	
	public static ArrayList<Component> getComponents(){
		return components;
	}

	public abstract void onStart(Entity entity);
	
	public abstract void onUpdate(Entity entity);
	
	public void leftMouseButtonDown() {}
	
	public void leftMouseButtonUp() {}
	
	public void leftMouseButtonClicked() {}
	
	public void rightMouseButtonDown() {}
	
	public void rightMouseButtonUp() {}
	
	public void rightMouseButtonClicked() {}

}

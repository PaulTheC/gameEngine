package Components;

import Entiys.Entity;

public abstract class Component{
	
	
	//this will stop the component from excecuting the onUpdate function
	protected boolean isActive = true;
	
	public void disable() {
		isActive = false;
	}
	
	public void enable() {
		isActive = true;
	}
	
	public boolean isAvcive() {
		return isActive;
	}

	public abstract void onStart(Entity entity);
	
	public abstract void onUpdate(Entity entity);

}

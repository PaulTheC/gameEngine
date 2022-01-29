package Entiys;

import org.lwjgl.util.vector.Vector3f;

import Lights.Light;

public class EnlightedEntity extends Entity{
	
	private Light light;
	private Vector3f offset = new Vector3f(0,0,0);
	

	public EnlightedEntity(){
		super();
		light = new Light();
	}
	
	public EnlightedEntity(String meshFile, String textureFile){
		super(meshFile, textureFile);
		light = new Light();
	}
	
	
	@Override
	public void setPosition(Vector3f position) {
		super.setPosition(position);
		updateLightPosition();
	}
	
	@Override
	public void setScale(float scale) {
		super.setScale(scale);
		updateLightPosition();
	}
	
	public void increasePosition(float dx, float dy, float dz) {
		super.increasePosition(dx, dy, dz);
		updateLightPosition();
	}
	
	private void updateLightPosition() {
		Vector3f erg = new Vector3f();
		Vector3f local = new Vector3f(this.offset.x * super.getScale(), this.offset.y * super.getScale(), this.offset.z * super.getScale());
		Vector3f.add(super.getPosition(), local, erg);
		light.setPosition(erg);
	}

	public void setBrightness(float brightness) {
		light.setBrightness(1 / brightness);
	}

	public void setOffset(Vector3f offset) {
		this.offset = offset;
		updateLightPosition();
	}
	
	public void setColor(Vector3f color) {
		light.setColor(color);
	}

	public void setStrength(float strength) {
		light.setStrength(strength);
	}
	
	public void setAttenuation(Vector3f attenuation) {
		light.setAttenuation(attenuation);
	}

}

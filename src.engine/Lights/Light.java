package Lights;

import org.lwjgl.util.vector.Vector3f;

public class Light {

	private Vector3f positions = new Vector3f(0,0,0);
	private Vector3f color = new Vector3f(1,1,1);
	private Vector3f attenuation =  new Vector3f(1f, 0.01f,0.002f);   					//The higher the value the lower the range of the lights
	private float strength = 1;
	
	public Vector3f getPosition() {
		return positions;
	}

	public void setPosition(Vector3f positions) {
		this.positions = positions;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}
	

	public Light(Vector3f positions, Vector3f color) {
		this.positions = positions;
		this.color = color;
		LightMaster.addLight(this);
	}
	
	public Light(Vector3f positions, float strenght) {
		this.positions = positions;
		this.strength = strenght;
		LightMaster.addLight(this);
	}
	
	public Light(float strenght) {
		this.strength = strenght;
		LightMaster.addLight(this);
	}
	
	public Light() {
		LightMaster.addLight(this);
	}
	
	public Light(Vector3f positions) {
		this.positions = positions;
		LightMaster.addLight(this);
	}
	
	public Light(Vector3f positions, Vector3f color, Vector3f attenuation) {
		this.positions = positions;
		this.color = color;
		this.attenuation = attenuation;
		LightMaster.addLight(this);
	}

	public Vector3f getAttenuation() {
		return attenuation;
	}

	public Vector3f getPositions() {
		return positions;
	}

	public void setPositions(Vector3f positions) {
		this.positions = positions;
	}

	public float getBrightness() {
		return attenuation.x;
	}

	public void setBrightness(float brightness) {
		this.attenuation.x = brightness;
	}

	public void setAttenuation(Vector3f attenuation) {
		this.attenuation = attenuation;
	}

	public float getStrength() {
		return strength;
	}

	public void setStrength(float strenght) {
		this.strength = strenght;
	}
	
	
	

}

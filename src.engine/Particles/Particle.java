package Particles;


import org.lwjgl.util.vector.Vector3f;

import Utilities.Time;

public class Particle {

	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeTime;
	private float rotation;
	private float scale;
	
	private float age = 0;

	public Particle(Vector3f position, Vector3f velocity, float gravityEffect, float lifeLenght, float rotation,
			float scale) {
		super();
		this.position = new Vector3f(position.x, position.y, position.z);
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeTime = lifeLenght;
		this.rotation = rotation;
		this.scale = scale;
		
		ParticleMaster.addParticle(this);
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getLifeLenght() {
		return lifeTime;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}
	
	protected boolean update() {
		velocity.y += ParticleSystem.GRAVITY * gravityEffect * Time.deltaTime;
		Vector3f change = new Vector3f(velocity);
		change.scale(Time.deltaTime);
//		Vector3f.add(change, position, position);
		age += Time.deltaTime;
		return age < lifeTime;
	}
	
	
	
}

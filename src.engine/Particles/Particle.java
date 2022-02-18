package Particles;

import org.lwjgl.util.vector.Vector3f;

import Utilities.Time;

public class Particle {

	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeTime;
	private float rotation;
	private Vector3f scale;
	private float speed;
	private float timeCreated;
	private float spawnTimeOffset;

	private ParticleSystem system;

	private float age = 0;

	public Particle(ParticleSystem system, Vector3f position, Vector3f velocity, float gravityEffect,
			float lifeLenght, float rotation, Vector3f scale, float speed, float spawnTimeOffset) {
		this.system = system;
		this.position = new Vector3f(position.x, position.y, position.z);
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeTime = lifeLenght;
		this.rotation = rotation;
		this.scale = scale;
		this.age = -spawnTimeOffset;
		this.speed = speed;
		timeCreated = Time.getTimeAlive();
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public Vector3f getPosition() {
		return system.calcPosition(position, scale);
	}

	public float getLifeLenght() {
		return lifeTime;
	}

	public ParticleTexture getTexture() {
		return system.getTexture();
	}

	public float getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		if(age < 0)
			return new Vector3f(0,0,0);
		return system.getScale(age, scale);
	}

	protected boolean update() {
		//scale Updating takes place in the getter method
		
		//updating velocity
		velocity.y += system.getGRAVITY() * gravityEffect * Time.deltaTime;
		Vector3f change = new Vector3f(velocity);
		change.scale(Time.deltaTime * speed);
		Vector3f.add(change, position, position);
		age += Time.deltaTime;
		
		
		

		return age < lifeTime;
	}
	
	public float getTimeCreated() {
		return timeCreated;
	}

}

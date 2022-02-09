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

	private ParticleTexture texture;

	private float age = 0;

	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect,
			float lifeLenght, float rotation, float scale) {
		this.texture = texture;
		this.position = new Vector3f(position.x, position.y, position.z);
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeTime = lifeLenght;
		this.rotation = rotation;
		this.scale = scale;
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

	public ParticleTexture getTexture() {
		return texture;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	protected boolean update() {
		velocity.y += 1 * gravityEffect * Time.deltaTime;
		Vector3f change = new Vector3f(velocity);
		change.scale(Time.deltaTime);
		Vector3f.add(change, position, position);
		age += Time.deltaTime;

		return age < lifeTime;
	}

}

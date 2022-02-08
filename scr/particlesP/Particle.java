package particlesP;

import org.lwjgl.util.vector.Vector3f;

public class Particle {
	
	private static final float GRAVITY = 0.5f;
	private static final float LIFE_LENGTH = 5f;
	private static final float SPEED = 4f;

	private Vector3f velocity;
	private Vector3f position = new Vector3f(0,0,0);
	
	private float lifeLeft = LIFE_LENGTH;
	
	public Particle(Vector3f velocity){
		this.velocity = velocity;
	}
	
	public boolean update(){
		velocity.y -= GRAVITY * Utilities.Time.deltaTime;
		Vector3f change = new Vector3f(velocity);
		change.scale(Utilities.Time.deltaTime * SPEED);
		Vector3f.add(position, change, position);
		lifeLeft -= Utilities.Time.deltaTime;
		return lifeLeft > 0;
	}
	
	public Vector3f getPosition(){
		return position;
	}
	
}

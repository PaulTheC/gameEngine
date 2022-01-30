package Camera;

import java.awt.AWTException;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Engine.DisplayManager;
import Engine.Main;
import Engine.Renderer;
import Entiys.Entity;
import Lights.Light;
import Scenes.SceneManager;
import Utilities.MouseHandler;
import Utilities.Time;
import testingScenes.TestScene;

public class Camera {
	

	private Vector3f position = new Vector3f(0,1.8f,10);
	private float pitch = 0;		//z
	private float yaw = 0;			//y
	private float roll = 0;			//x
	private float yStretching = (DisplayManager.HEIGHT / (float) Display.getHeight());
	private float xStretching = (DisplayManager.WIDTH / (float) Display.getWidth());
	private Light light;
	private long time;
	
	private Robot robot;
	
	
	public final float SPEED = 100;
	public final float RAY_LENGHT = 50;
	
	public Camera(){
		
		light = new Light();
		light.setStrength(3);
		light.setBrightness(0.3f);
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	
		
	}
	
	public void move(){
		
		yaw += MouseHandler.getLastXMovement() * 0.04f;
		pitch = Math.min(Math.max(pitch + MouseHandler.getLastYMovement() * -0.04f, -90), 90);
		
		
		float currentSpeed = 0;
		float sideSpeed = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			currentSpeed = -SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			currentSpeed = SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			sideSpeed = SPEED / 2;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			sideSpeed = -SPEED / 2;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			position.y -= SPEED * Time.deltaTime;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			position.y += SPEED * Time.deltaTime;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
			DisplayManager.requestClose();
		}
		
		
		
	
		
		float distance = currentSpeed * Time.deltaTime;
		float dx = (float) (distance * Math.sin(Math.toRadians(-yaw)));
		float dz = (float) (distance * Math.cos(Math.toRadians(yaw)));
		position.x += dx;
		position.z += dz;
		
		
		distance = sideSpeed * Time.deltaTime;
		dx = (float) (distance * Math.sin(Math.toRadians(yaw)));
		dz = (float) (distance * Math.cos(Math.toRadians(-yaw)));
		position.x += dz;
		position.z += dx;
		
		
		//Colision detection
		
		//position.y = SceneManager.<TestScene>getActiveScene().map.getHeightOfTerrain(position.x, position.z)+2;
		
		
		
		light.setPosition(new Vector3f(position.x, position.y, position.z));
	}

	public Vector3f getPosition() {
		return position;
	}
	

	public void leftMouseDown() {
		
		

		time = System.currentTimeMillis();
		//casting a ray from the centre of the camera and calculating the intersection with the Terrain
		//Vector3f intersection = getTerrainIntersection(getFromCameraRay());
		Vector3f ray = getFromCameraRay();
		Vector3f pos = new Vector3f(getPosition().x, getPosition().y, getPosition().z);

		for(int i = 0; i < TestScene.cube.length; i++) {
			Vector3f f = new Vector3f(ray.x * -i * 10, ray.y * i * 10, ray.z * i * 10);
			TestScene.cube[i].setPosition(Vector3f.add(pos, f, f));
		}
		
		//TestScene.cube[0].setPosition(getPosition());
		
//		if(intersection != null)
//			SceneManager.<TestScene>getActiveScene().cube.setPosition(intersection);

	}

	public float getPitch() {
		return pitch;
	}
	
	
	//performance intensive
	public Vector3f getTerrainIntersection(Vector3f ray) {
		float distance = getDistanceOfPoint((int)(Display.getHeight() / 2f), (int)(Display.getWidth() / 2f));
		
		Vector3f intersection = new Vector3f(ray.x * -distance, ray.y * distance, ray.z * distance);
		Vector3f.add(intersection, getPosition(), intersection);

		
		return distance != 0 ? intersection : null;
		
	}
	
	//calculates the distance to a point using the depth texture
	private float getDistanceOfPoint(int x, int y) {
		FloatBuffer depthBuffer = BufferUtils.createFloatBuffer(Display.getWidth() * Display.getHeight());
		GL11.glReadBuffer(GL11.GL_DEPTH);
		GL11.glReadPixels(0,0,Display.getWidth(), Display.getHeight(), GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, depthBuffer);
		float distance = getRealDepthFromSample(depthBuffer.get(Display.getWidth() * x + y -1), Renderer.NEAR_PLANE, Renderer.FAR_PLANE);
		return depthBuffer.get(Display.getWidth() * x + y -1) != 1 ? distance : 0;
		
	}
	
	public Vector3f getFromCameraRay() {
	
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		Matrix4f.translate(new Vector3f(0,0,1), m, m);
		Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(0,0,1), m, m);
		Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0,1,0), m, m);
		//Matrix4f.rotate((float) Math.toRadians(roll), new Vector3f(0,0,1), m, m);
		
		System.out.println(new Vector3f(m.m10, m.m11, m.m12));
		
		float rx = (float)Math.sin((double)getYaw() * (double)(Math.PI / 180)) * -1 * (1-Math.abs((float)Math.cos((double)getPitch() * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1));
		float ry = (float)Math.sin((double)getPitch() * (double)(Math.PI / 180)) * -1;
		float rz = (float)Math.cos((double)getYaw() * (double)(Math.PI / 180)) * -1 * (1- Math.abs((float)Math.cos((double)getPitch() * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1));
		
		System.out.println(getPitch()+ "      "+ry);
		return (Vector3f) new Vector3f(rx, m.m10, rz).normalise();
	}
	
	
	private float getRealDepthFromSample(float sample, float NEAR_PLANE, float FAR_PLANE) {
		return (float)(2.0f * NEAR_PLANE * FAR_PLANE / (FAR_PLANE + NEAR_PLANE - (2.0f * sample - 1.0f) * (FAR_PLANE - NEAR_PLANE)));
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}


	public void generateStretching() {
		yStretching = (DisplayManager.HEIGHT / (float) Display.getHeight());
		xStretching = (DisplayManager.WIDTH / (float) Display.getWidth());
	}

	public float getxStretching() {
		return xStretching;
	}

	public float getyStretching() {
		return yStretching;
	}

	
	
}

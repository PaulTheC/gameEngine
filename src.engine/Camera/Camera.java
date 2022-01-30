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
import org.lwjgl.util.vector.Vector3f;

import Engine.DisplayManager;
import Engine.Main;
import Engine.Renderer;
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
		pitch += MouseHandler.getLastYMovement() * -0.04f;
		
		
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
		
		position.y = SceneManager.<TestScene>getActiveScene().map.getHeightOfTerrain(position.x, position.z)+2;
		
		
		
		
		light.setPosition(new Vector3f(position.x, position.y, position.z));
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public void leftMouseDown() {
		Vector3f ray = getFromCameraRay();
		FloatBuffer depthBuffer = BufferUtils.createFloatBuffer(Display.getWidth() * Display.getHeight());
		GL11.glReadBuffer(GL11.GL_DEPTH);
		GL11.glReadPixels(0, 0, Display.getWidth(), Display.getHeight(), GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT,depthBuffer);
		float distance = getRealDepthFromSample(depthBuffer.get(Display.getWidth() * Display.getHeight() / 2 + Display.getWidth() / 2), Renderer.NEAR_PLANE, Renderer.FAR_PLANE);
		System.out.println(distance);
	}

	public float getPitch() {
		return pitch;
	}
	
	private Vector3f getFromCameraRay() {
		float rx = (float)Math.sin((double)getYaw() * (double)(Math.PI / 180)) * -1 * (1-Math.abs((float)Math.cos((double)getPitch() * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1));
		float ry = (float)Math.cos((double)getPitch() * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1;
		float rz = (float)Math.cos((double)getYaw() * (double)(Math.PI / 180)) * -1 * (1- Math.abs((float)Math.cos((double)getPitch() * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1));
		return new Vector3f(rx, ry, rz);
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

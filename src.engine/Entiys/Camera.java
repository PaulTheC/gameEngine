package Entiys;

import java.awt.AWTException;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Engine.DisplayManager;
import Engine.Main;
import Engine.MouseHandler;
import Lights.Light;

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
			position.y -= SPEED * Main.deltaTime;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			position.y += SPEED * Main.deltaTime;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
			System.exit(0);
		}
	
		
		float distance = currentSpeed * Main.deltaTime;
		float dx = (float) (distance * Math.sin(Math.toRadians(-yaw)));
		float dz = (float) (distance * Math.cos(Math.toRadians(yaw)));
		position.x += dx;
		position.z += dz;
		
		
		distance = sideSpeed * Main.deltaTime;
		dx = (float) (distance * Math.sin(Math.toRadians(yaw)));
		dz = (float) (distance * Math.cos(Math.toRadians(-yaw)));
		position.x += dz;
		position.z += dx;
		
		
		//Colision detection
		
		//position.y = Main.map.getHeightOfTerrain(position.x, position.z)+2;
		
		
		
		
		light.setPosition(new Vector3f(position.x, position.y, position.z));
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
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

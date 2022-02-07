package Camera;

import java.awt.AWTException;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Engine.DisplayManager;
import Engine.Main;
import Engine.Renderer;
import Entiys.Entity;
import Lights.Light;
import Loader.Loader;
import Loader.Saver;
import Scenes.SceneManager;
import Terraforming.SmoothenBrush;
import Terrain.Brush;
import Terrain.Terraformer;
import Terrain.TerrainArea;
import Utilities.MouseHandler;
import Utilities.Time;
import testingScenes.TestScene;

public class Camera extends Entity{
	

	private Vector3f position = new Vector3f(0,1.8f,10);
	private float pitch = 0;		//z
	private float yaw = 0;			//y
	private float roll = 0;			//x
	private float yStretching = (DisplayManager.HEIGHT / (float) Display.getHeight());
	private float xStretching = (DisplayManager.WIDTH / (float) Display.getWidth());
	
	
	public final float SPEED = 10;
	public final float RAY_LENGHT = 50;
	
	public Camera(){
	}

	public Vector3f getPosition() {
		return position;
	}
	



	public float getPitch() {
		return pitch;
	}
	
	

	
	public Vector3f getFromCameraRay() {
		float pitch = (float)Math.toRadians(getPitch());
		float yaw = (float)Math.toRadians(getYaw());
		
		float rx = -(float)(Math.sin(yaw) * Math.cos(pitch));
		float ry = -(float)(Math.sin(pitch) );
		float rz = -(float)(Math.cos(pitch) * Math.cos(yaw)); 
		
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

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	
	
}

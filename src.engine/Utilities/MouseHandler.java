package Utilities;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import Camera.MainCamera;
import Components.Event;
import Engine.Player;

public class MouseHandler {
	
	private static int lastXMovement = 0;
	private static int lastYMovement = 0;
	private static boolean lastLeftDown = false;
	private static boolean lastRightDown = false;
	
	public static void initMouse() {
		try {
			Mouse.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mouse.setGrabbed(true);
	}
	
	public static int getLastXMovement() {
		return lastXMovement;
	}
	
	public static int getLastYMovement() {
		return lastYMovement;
	}
	
	public static void setMousePosition(int x, int y) {
		Mouse.setCursorPosition(x, y);
	}

	public static void mouseUpdate() {
		lastXMovement = Mouse.getX()-500;
		lastYMovement = Mouse.getY()-500;
		Mouse.setCursorPosition(500, 500);
		
		if(Mouse.isButtonDown(0) && !lastLeftDown) {
//			Player.getCamera().leftMouseDown();		
			Event.callEvent("leftMouseButtonDown");
			lastLeftDown = true;
		}
		if(!Mouse.isButtonDown(0) && lastLeftDown) {
			Event.callEvent("leftMouseButtonClicked");
			lastLeftDown = false;
		}
		if(Mouse.isButtonDown(1) && !lastRightDown) {
//			Player.getCamera().rightMouseDown();
			Event.callEvent("rightMouseButtonDown");
			lastRightDown = true;
		}
		if(!Mouse.isButtonDown(1) && lastRightDown) {
			Event.callEvent("rightMouseButtonClicked");
			lastRightDown = false;
		}
	

		
	}
	
	public static void onDestory() {
		Mouse.destroy();
	}
	
}

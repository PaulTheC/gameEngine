package Utilities;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

public class MouseHandler {
	
	private static int lastXMovement = 0;
	private static int lastYMovement = 0;
	
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
	}
	
	public static void onDestory() {
		Mouse.destroy();
	}
	
}

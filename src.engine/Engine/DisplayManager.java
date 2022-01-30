package Engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import Camera.Camera;
import EntityShader.EnitiyShader;
import MainShader.StaticShader;

public class DisplayManager {
	
	public static final int WIDTH = 3000;
	public static final int HEIGHT = 1500;
	private static final int FPS_CAP = 30;
	private static boolean requestClose = false;
	
	public static void createDisplay(){		
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Our First Display!");
			Display.setResizable(true);
			Display.setLocation(0, 0);
			Display.setFullscreen(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		
		GL11.glViewport(0,0, WIDTH, HEIGHT);
	}
	
	public static void updateDisplay(){
		
		Display.update();
	}
	
	public static void requestClose() {
		requestClose = true;
	}
	
	
	public static void closeDisplay(){
		Display.destroy();
	}
	
	public static boolean isCloseRequested() {
		return requestClose != Display.isCloseRequested();
	}
	
	
	public static void resizeDisplay(Camera camera, Renderer renderer, StaticShader shader) {
		if(Display.wasResized()) {
			GL11.glViewport(0,0, Display.getWidth(), Display.getHeight());
			camera.generateStretching();
			renderer.generateProjectionMatrix();
		}
	}
	

}
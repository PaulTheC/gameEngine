package Engine;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.XRandR.Screen;

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
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Game!");
			Display.setResizable(true);
			Display.setLocation(0, 0);
			Display.setFullscreen(true);
//			Display.setVSyncEnabled(true);
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		
		GL11.glViewport(0,0, WIDTH, HEIGHT);
		
		
		//getting the monitor size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int monitorWidth = (int) (screenSize.getWidth() * Toolkit.getDefaultToolkit().getScreenResolution() / 96);
		int monitorHeight = (int) (screenSize.getHeight() * Toolkit.getDefaultToolkit().getScreenResolution() / 96);
		System.out.println("Monitor Dimensions: "+ monitorWidth + " x "+ monitorHeight);
		
		
		ArrayList<DisplayMode> resolutions = new ArrayList<DisplayMode>();
		
		
	    try {
	        DisplayMode[] modes;
	            modes = Display.getAvailableDisplayModes(); //get all resolution
	        for (int i=0;i<modes.length;i++) {
	            DisplayMode current = modes[i]; //add all DisplaMode to arraylist
	            resolutions.add(current);
	        }
	        } catch (LWJGLException e) {
	            e.printStackTrace();
	        }
	        Collections.sort(resolutions, (m1, m2) -> m1.getHeight() * m1.getWidth() - m2.getHeight() * m2.getWidth());
	        Iterator<DisplayMode> modes = resolutions.iterator();
	        while(modes.hasNext()) {
	        	DisplayMode mode = modes.next();
	        	if(mode.getFrequency() == 59)
	        		modes.remove();
	        }
	        for (DisplayMode mode : resolutions) { //optional just to see all resolution
	            System.out.println(mode.getWidth() + "x" + mode.getHeight() + "x"+mode.getBitsPerPixel() + " " + mode.getFrequency() + "Hz");
	        } 
	        
			try {
				Display.setDisplayMode(resolutions.get(resolutions.size()-1));
			} catch (LWJGLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
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
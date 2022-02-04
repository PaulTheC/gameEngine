package Colisions;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import Engine.Player;
import Engine.Renderer;

public class Raycast {
	
	public static Vector3f terrainRaycast() {
		Vector3f ray = Player.getCamera().getFromCameraRay();
		float distance = getDistanceOfPoint((int)(Display.getHeight() / 2f), (int)(Display.getWidth() / 2f));
		
		Vector3f intersection = new Vector3f(ray.x * -distance, ray.y * distance, ray.z * distance);
		Vector3f.add(intersection, Player.getCamera().getPosition(), intersection);

		
		return distance != 0 ? intersection : null;
		
	}
	
	
	private static float getDistanceOfPoint(int x, int y) {
		FloatBuffer depthBuffer = BufferUtils.createFloatBuffer(Display.getWidth() * Display.getHeight());
		GL11.glReadBuffer(GL11.GL_DEPTH);
		GL11.glReadPixels(0,0,Display.getWidth(), Display.getHeight(), GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, depthBuffer);
		float distance = getRealDepthFromSample(depthBuffer.get(Display.getWidth() * x + y -1), Renderer.NEAR_PLANE, Renderer.FAR_PLANE);
		return depthBuffer.get(Display.getWidth() * x + y -1) != 1 ? distance : 0;
		
	}
	
	private static float getRealDepthFromSample(float sample, float NEAR_PLANE, float FAR_PLANE) {
		return (float)(2.0f * NEAR_PLANE * FAR_PLANE / (FAR_PLANE + NEAR_PLANE - (2.0f * sample - 1.0f) * (FAR_PLANE - NEAR_PLANE)));
	}

}

package Engine;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import EntityShader.EnitiyShader;
import Entiys.Entity;
import Entiys.EntityMaster;
import Lights.Light;
import Lights.LightMaster;
import MainShader.ShaderMaster;
import MainShader.StaticShader;
import Models.RawModel;
import Models.TexturedModel;
import Particles.Particle;
import Particles.ParticleMaster;
import Scenes.SceneManager;
import Tools.Maths;
import UIElements.UIElement;
import UIElements.UIElementsMaster;
import UIShader.UIShader;

public class Renderer {
	
	private static final float FOV = 70;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 3000;
	private Camera camera;
	
	
	private Matrix4f projectionMatrix;
	
	public Renderer(Camera cam){
		this.camera = cam;
		generateProjectionMatrix();
		new ParticleMaster(projectionMatrix);
	}
	

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.3f, 0.5f, 0.6f, 1);
	}


	public void render() {
		ParticleMaster.addParticle(new Particle(Player.getCamera().getPosition(), new Vector3f(0,40,0), 1, 4, 0, 0.5f));
		
		
		//updating
		ParticleMaster.update();
		
		//preparing

		for(StaticShader shader: ShaderMaster.getList()) {
			shader.loadLights(LightMaster.getLightArray());
		}
		
		
		//rendering
		EntityMaster.renderAllEntitys(SceneManager.getActiveScene());
		ParticleMaster.renderAllParticles();
		UIElementsMaster.renderAllUIElements();
		
		//cleaning up
		
		
	}
	
	
	private void createProjectionMatrix(){
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}

	
	public void generateProjectionMatrix() {
		for(StaticShader shader: ShaderMaster.getList()) {
			createProjectionMatrix();
			shader.start();
			shader.loadProjectionMatrix(projectionMatrix);
			shader.stop();
		}
	}


}

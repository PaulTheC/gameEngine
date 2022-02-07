package Particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.Position;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Engine.Player;
import EntityPresets.Plane;
import Loader.Loader;
import Models.RawModel;
import ParticleShader.ParticleShader;
import Tools.Maths;
import Particles.Particle;

public class ParticleMaster {
	
	private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};
	
	
	
	private static ArrayList<Particle> particles = new ArrayList<>();
	
	private static RawModel quad;
	private static ParticleShader shader;
	
	public ParticleMaster(Matrix4f projectionMatrix){
//		quad = new RawModel(Loader.loadToVAO(VERTICES), 2);
		quad = Plane.generateRawModel();
		shader = new ParticleShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public static void renderAllParticles(){

		Matrix4f viewMatrix = Maths.createViewMatrix(Player.getCamera());
		prepare();
		for(Particle p: particles) {
			updateModelViewMatrix(p.getPosition(), p.getRotation(), p.getScale(), viewMatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			GL11.glGetError();
		}
		finishRendering();
		System.out.println(particles.size());
	}
	
	public static void update() {
		
		Iterator<Particle> iterator = particles.iterator();
		while(iterator.hasNext()) {
			
			Particle p = iterator.next();
			boolean stillAlive = p.update();
			if(!stillAlive)
				iterator.remove();
			
			
		}
	}
	
	private static void updateModelViewMatrix(Vector3f position, float rotation, float scale, Matrix4f viewMatrix) {
		
		Matrix4f modelMatrix = new Matrix4f();
		Matrix4f.translate(position, modelMatrix, modelMatrix);
		
		modelMatrix.m00 = viewMatrix.m00;
		modelMatrix.m01 = viewMatrix.m10;
		modelMatrix.m02 = viewMatrix.m20;
		modelMatrix.m10 = viewMatrix.m01;
		modelMatrix.m11 = viewMatrix.m11;
		modelMatrix.m12 = viewMatrix.m21;
		modelMatrix.m20 = viewMatrix.m02;
		modelMatrix.m21 = viewMatrix.m12;
		modelMatrix.m22 = viewMatrix.m22;
		
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0,0,1), modelMatrix, modelMatrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), modelMatrix, modelMatrix);
		
		Matrix4f modelViewMatrix = Matrix4f.mul(modelMatrix, viewMatrix, null);
		shader.loadModelViewMatrix(modelViewMatrix);
	}


	protected void cleanUp(){
		
	}
	
	public static void addParticle(Particle p) {
		particles.add(p);
	}
	
	private static void prepare(){

		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL11.glGetError();
		GL20.glEnableVertexAttribArray(0);
		GL11.glGetError();
//		GL11.glEnable(GL11.GL_BLEND);
//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
		
	}
	
	private static void finishRendering(){
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shader.stop();
	}

}

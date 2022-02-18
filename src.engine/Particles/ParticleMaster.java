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
import EntityPresets.Quad;
import Loader.Loader;
import MainShader.ParticleShader;
import Models.RawModel;
import Tools.Maths;
import Particles.Particle;

public class ParticleMaster {
	
	private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};
	
	
	
	private static ArrayList<ParticleSystem> particleSystems = new ArrayList<>();
	
	public static RawModel quad;
	private static ParticleShader shader;
	private static ParticleTexture defaultTexture;
	
	public ParticleMaster(Matrix4f projectionMatrix){
		quad = new RawModel(Loader.loadToVAO(VERTICES), 4);
		
		defaultTexture = Loader.loadParticleTexture("particleTextures/defaultParticle");
		
	}
	
	public static void renderAllParticles(){
		Matrix4f viewMatrix = Maths.createViewMatrix(Player.getCamera());
		for(ParticleSystem p: particleSystems) {
			p.getShader().loadPerSystemUniforms(p);
			p.render(viewMatrix);
		}
		finishRendering();
	}
	
	public static void update() {
		Iterator<ParticleSystem> iterator = particleSystems.iterator();
		while(iterator.hasNext()) {
			ParticleSystem p = iterator.next();
			boolean stillAlive = p.update();
			if(!stillAlive)
//				p.destroy();				
				iterator.remove();
		}
	}
	
	public static void addParticleSystem(ParticleSystem system) {
		particleSystems.add(system);
	}
	
	public static void removeParticleSystem(ParticleSystem system) {
		System.out.println(particleSystems.size());
		particleSystems.remove(system);
		System.out.println(particleSystems.size());
	}

	public static ParticleTexture getDefaultTexture() {
		return defaultTexture;
	}

	protected void cleanUp(){
		
	}
	
	private static void finishRendering(){
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glCullFace(GL11.GL_BACK);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}

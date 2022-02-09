package Particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Tools.Curve;
import Tools.CurvePresets;
import Utilities.*;
import Models.*;
import ParticleShader.*;

enum ParticleEmitShape{
	Point, 
	Sphere
}

public class ParticleSystem {
	
	private List<Particle> particles = new ArrayList<>();
	private float lifeLenght = 3;
	private float systemLifeLenght = 10;
	private ParticleShader shader;
	private ParticleTexture texture;
	private RawModel model;
	
	private final float GRAVITY = -10;
	private float gravityAffect = 0;
	private Vector3f velocity = new Vector3f(0,0,0); 
	private Vector3f position = new Vector3f(0,0,0);
	
	private ParticleEmitShape particleEmitShape = ParticleEmitShape.Point;
	private int constantSpawnRate = 10;
	private int burstSpawnRate = 30;
	
	private Curve scaleXCurve = CurvePresets.constant;
	private Curve scaleYCurve = CurvePresets.constant;
	private Curve scaleZCurve = CurvePresets.constant;
	private Vector3f scale = new Vector3f(1,1,1);
	private float age = 0;
	
	
	public ParticleSystem(ParticleShader shader, ParticleTexture texture) {
		this.texture = texture;
		this.shader = shader;
		this.model = ParticleMaster.quad;
		
		ParticleMaster.addParticleSystem(this);
	}
	
	public ParticleSystem(ParticleShader shader) {
		this.texture = ParticleMaster.getDefaultTexture();
		this.shader = shader;
		this.model = ParticleMaster.quad;
		
		ParticleMaster.addParticleSystem(this);
	}
	
	public ParticleSystem apply() {
		spanwnBurst(burstSpawnRate);
		return this;
	}
	
	
	public void render(Matrix4f viewMatrix) {
		prepare();
		
		//bind Texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureId());
		
		
		for(Particle p: particles) {
			updateModelViewMatrix(p.getPosition(), p.getRotation(), scale, viewMatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, model.getVertexCount());
		}
		
		
		shader.stop();
	}
	
	public boolean update() {
		
		age += Time.deltaTime;
		
		spawnParticles();
		
		Iterator<Particle> iterator = particles.iterator();
		while(iterator.hasNext()) {
			Particle p = iterator.next();
			boolean stillAlive = p.update();
			if(!stillAlive)
				iterator.remove();
		}
		
		age += Time.deltaTime;
		return systemLifeLenght > age;
		
	}
	
	private void prepare(){

		shader.start();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glCullFace(GL11.GL_FRONT);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
		
	}
	
	
	private void updateModelViewMatrix(Vector3f position, float rotation, Vector3f scale, Matrix4f viewMatrix) {
		
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
		Matrix4f.scale(scale, modelMatrix, modelMatrix);
		
		Matrix4f modelViewMatrix = Matrix4f.mul(viewMatrix, modelMatrix, null);
		shader.loadModelViewMatrix(modelViewMatrix);
	}
	
	private void spawnParticles() {
		//When this if statement is true, another particle should be spawned
		if(age % constantSpawnRate > (age + Time.deltaTime) % constantSpawnRate){
			spawnParticle();
		}
		
	}
	
	public void destroy() {
		particles.clear();
		particles = null;
	}
	
	private void spanwnBurst(int count) {
		for(int i = 0; i < count; i++)
			spawnParticle();
	}
	
	private void spawnParticle() {
		Random r = new Random();
		particles.add(new Particle(texture, position, velocity, gravityAffect, lifeLenght, 0, 1));
	}
	
	public ParticleSystem setGravityAffect(float affect) {
		this.gravityAffect = affect;
		return this;
	}
	
	public ParticleSystem setVelocity(Vector3f velocity) {
		this.velocity = velocity;
		return this;
	}
	
	public ParticleTexture getTexture() {
		return texture;
	}

	public ParticleSystem setTexture(ParticleTexture texture) {
		this.texture = texture;
		return this;
	}

	public ParticleSystem setConstantSpawnRate(int rate) {
		this.constantSpawnRate = rate;
		return this;
	}

	public ParticleSystem setParticleEmitShape(ParticleEmitShape particleEmitShape) {
		this.particleEmitShape = particleEmitShape;
		return this;
	}

	public ParticleSystem setBurstSpawnRate(int burstSpawnRate) {
		this.burstSpawnRate = burstSpawnRate;
		return this;
	}

	public ParticleSystem setScaleXCurve(Curve sizeXCurve) {
		this.scaleXCurve = sizeXCurve;
		return this;
	}

	public ParticleSystem setScaleYCurve(Curve sizeYCurve) {
		this.scaleYCurve = sizeYCurve;
		return this;
	}

	public ParticleSystem setScaleZCurve(Curve sizeZCurve) {
		this.scaleZCurve = sizeZCurve;
		return this;
	}

	public ParticleSystem setScale(Vector3f size) {
		this.scale = size;
		return this;
	}

	public ParticleSystem setLifeLenght(float lifeLenght) {
		this.lifeLenght = lifeLenght;
		return this;
	}
	
	public ParticleSystem setSystemLifeLenght(float systemLifeLenght) {
		this.systemLifeLenght = systemLifeLenght;
		return this;
	}
	
	

	public ParticleSystem setPosition(Vector3f position) {
		this.position = position;
		return this;
	}

	public float getGRAVITY() {
		return GRAVITY;
	}

}

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
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import DefaultParticleShader.*;
import Engine.Player;
import MainShader.ParticleShader;
import Tools.Curve;
import Tools.Maths;
import Utilities.*;
import Models.*;

enum ParticleEmitShape {
	Point, Sphere
}

public class ParticleSystem {

	private Random r = new Random();

	private List<Particle> particles = new ArrayList<>();

	private ParticleShader shader;
	private ParticleTexture texture;
	private Vector3f color = new Vector3f(1,1,1);

	private RawModel model;

	private boolean depthTesting = true;
	private boolean oneTriangle = false;

	private final float GRAVITY = -10;
	private float gravityAffect = 0;
	private float speed = 1;
	private float speedRandomness = 0;
	private Curve speedOverLifetime = x -> Curve.linear.invert(x);
	private Vector3f velocity = new Vector3f(0, 5, 0);
	private Vector3f position = new Vector3f(0, 0, 0);
	private Vector3f offset = new Vector3f(0, 0, 0);
	private Vector3f pivot = new Vector3f(0, 0, 0);
	private float startVelocityRandomness = 0;

	private ParticleEmitShape particleEmitShape = ParticleEmitShape.Point;
	private int constantSpawnRate = 10;
	private int burstSpawnRate = 30;

	private Curve scaleXCurve = Curve.constant;
	private Curve scaleYCurve = Curve.constant;
	private Curve scaleZCurve = Curve.constant;
	private Curve scaleOverLifetime = x -> Curve.linear.invert(x);
	private Vector3f scale = new Vector3f(1, 1, 1);

	private float age = 0;
	private float particleLifeLenght = 3;
	private float systemLifeLenght = 10;
	private float spawnTimeOffset = 0;

	private boolean fixXRotation = false;
	private boolean fixYRotation = false;

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

		// bind Texture
//		GL13.glActiveTexture(GL13.GL_TEXTURE0);
//		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureId());

		for (Particle p : particles) {
			updateModelViewMatrix(p.getPosition(), p.getRotation(), p.getScale(), viewMatrix);
			shader.loadPerParticleUniforms(p);
			if (!oneTriangle)
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, model.getVertexCount());
			else
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 3);
		}

		shader.stop();
	}

	public boolean update() {

		age += Time.deltaTime;

		spawnParticles();

		Iterator<Particle> iterator = particles.iterator();
		while (iterator.hasNext()) {
			Particle p = iterator.next();
			boolean stillAlive = p.update();
			if (!stillAlive)
				iterator.remove();
		}

		age += Time.deltaTime;
		return systemLifeLenght > age;

	}

	private void prepare() {

		shader.start();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glCullFace(GL11.GL_FRONT);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
		if (!depthTesting)
			GL11.glDisable(GL11.GL_DEPTH_TEST);

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

		
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 0, 1), modelMatrix, modelMatrix);
		if (fixXRotation)
			Matrix4f.rotate((float) Math.toRadians(Player.getCamera().getPitch()), new Vector3f(1, 0, 0), modelMatrix,
					modelMatrix);
		if (fixYRotation)
			Matrix4f.rotate((float) Math.toRadians(Player.getCamera().getYaw()), new Vector3f(0, 1, 0), modelMatrix,
					modelMatrix);
		Matrix4f.scale(scale, modelMatrix, modelMatrix);

		Matrix4f modelViewMatrix = Matrix4f.mul(viewMatrix, modelMatrix, null);
		shader.loadModelViewMatrix(modelViewMatrix);
	}

	private void spawnParticles() {
		// When this if statement is true, another particle should be spawned
		if (age % (float) (1f / constantSpawnRate) > (age + Time.deltaTime) % (float) (1f / constantSpawnRate)) {
			spawnParticle();
		}

	}

	public void destroy() {
		particles.clear();
		particles = null;
	}

	private void spanwnBurst(int count) {
		for (int i = 0; i < count; i++)
			spawnParticle();
	}

	private void spawnParticle() {
		Vector3f vel = new Vector3f();
		float speed = 0;
		if (startVelocityRandomness < 0.00001f) {
			vel = velocity;
		} else {
			vel = generateRandomUnitVectorWithinCone(velocity, startVelocityRandomness);
		}
		if (speedRandomness < 0.000001f)
			speed = this.speed;
		else
			speed += r.nextFloat(speedRandomness);

		particles.add(
				new Particle(this, position, vel, gravityAffect, particleLifeLenght, 0, scale, speed, spawnTimeOffset));
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

	public ParticleSystem setColor(Vector3f color) {
		this.color = color;
		return this;
	}

	public ParticleSystem setParticleLifeLenght(float particleLifeLenght) {
		this.particleLifeLenght = particleLifeLenght;
		return this;
	}

	public ParticleSystem setSystemLifeLenght(float systemLifeLenght) {
		this.systemLifeLenght = systemLifeLenght;
		return this;
	}

	public ParticleSystem setSpeed(float speed) {
		this.speed = speed;
		return this;
	}

	public ParticleSystem setPosition(Vector3f position) {
		this.position = position;
		return this;
	}

	public ParticleSystem setPivot(Vector3f pivot) {
		this.pivot = pivot;
		return this;
	}

	public ParticleSystem setOffset(Vector3f offset) {
		this.offset = offset;
		return this;
	}

	public float getGRAVITY() {
		return GRAVITY;
	}

	public Vector3f getScale(float ageOfParticle, Vector3f scale) {
		float lifeTimeScale = scaleOverLifetime.calc(ageOfParticle / particleLifeLenght);
		return new Vector3f(scale.x * scaleXCurve.calc(ageOfParticle) * lifeTimeScale,
				scale.y * scaleYCurve.calc(ageOfParticle) * lifeTimeScale,
				scale.z * scaleZCurve.calc(ageOfParticle) * lifeTimeScale);
	}

	public float getSpeed(float ageOfParticle, float speed) {
		return speedOverLifetime.calc(ageOfParticle / particleLifeLenght) * speed;
	}

	public ParticleSystem setStartVelocityRandomness(float startVelocityRandomness) {
		this.startVelocityRandomness = startVelocityRandomness;
		return this;
	}

	public ParticleSystem setSpeedRandomness(float speedRandomness) {
		this.speedRandomness = speedRandomness;
		return this;
	}

	public ParticleSystem setSpawnTimeOffset(float spawnTimeOffset) {
		this.spawnTimeOffset = spawnTimeOffset;
		return this;
	}

	public ParticleSystem setScaleOverLifetime(Curve scaleOverLifetime) {
		this.scaleOverLifetime = scaleOverLifetime;
		return this;
	}

	public ParticleSystem setFixXRotation(boolean fixXRotation) {
		this.fixXRotation = fixXRotation;
		return this;
	}

	public ParticleSystem setDepthTesting(boolean depthTesting) {
		this.depthTesting = depthTesting;
		return this;
	}

	public ParticleSystem setFixYRotation(boolean fixYRotation) {
		this.fixYRotation = fixYRotation;
		return this;
	}

	public ParticleSystem setOneTriangle(boolean oneTriangle) {
		this.oneTriangle = oneTriangle;
		return this;
	}

	public ParticleShader getShader() {
		return shader;
	}

	public Vector3f getColor() {
		return color;
	}

	public Vector3f calcPosition(Vector3f position, Vector3f scale) {

		return new Vector3f(position.x + scale.x / 2 * pivot.x * -1 + offset.x,
				position.y + scale.y / 2 * pivot.y * -1 + offset.y, position.z + scale.z / 2 * pivot.z * -1 + offset.z);
	}

	private static Vector3f generateRandomUnitVectorWithinCone(Vector3f coneDirection, float angle) {
		float cosAngle = (float) Math.cos(angle);
		Random random = new Random();
		float theta = (float) (random.nextFloat() * 2f * Math.PI);
		float z = cosAngle + (random.nextFloat() * (1 - cosAngle));
		float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
		float x = (float) (rootOneMinusZSquared * Math.cos(theta));
		float y = (float) (rootOneMinusZSquared * Math.sin(theta));

		Vector4f direction = new Vector4f(x, y, z, 1);
		if (coneDirection.x != 0 || coneDirection.y != 0 || (coneDirection.z != 1 && coneDirection.z != -1)) {
			Vector3f rotateAxis = Vector3f.cross(coneDirection, new Vector3f(0, 0, 1), null);
			rotateAxis.normalise();
			float rotateAngle = (float) Math.acos(Vector3f.dot(coneDirection, new Vector3f(0, 0, 1)));
			Matrix4f rotationMatrix = new Matrix4f();
			rotationMatrix.rotate(-rotateAngle, rotateAxis);
			Matrix4f.transform(rotationMatrix, direction, direction);
		} else if (coneDirection.z == -1) {
			direction.z *= -1;
		}
		return new Vector3f(direction);
	}

}

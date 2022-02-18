package ParticleEffects;

import org.lwjgl.util.vector.Vector3f;

import DefaultParticleShader.DefaultParticleShader;
import LightningShader.LightningShader;
import LightningV2Shader.LightningV2Shader;
import Loader.Loader;
import MainShader.ParticleShader;
import Particles.ParticleSystem;
import Particles.ParticleTexture;
import Tools.Curve;

public class LightningV2 {
	
	static ParticleShader shader = new LightningV2Shader();
	static ParticleShader standard = new DefaultParticleShader();
	static ParticleTexture texture = Loader.loadParticleTexture("particleTextures/defaultParticle");
	
	public LightningV2(Vector3f position) {
		
		ParticleSystem p = new ParticleSystem(shader)
				.setSystemLifeLenght(100)
				.setConstantSpawnRate(0)
				.setParticleLifeLenght(50f)
				.setTexture(texture)
				.setBurstSpawnRate(1)
				.setScaleOverLifetime(Curve.constant)
				.setPosition(position)
				.setSpeedRandomness(0f)
				.setScale(new Vector3f(2, 5, 1))
				.setStartVelocityRandomness(1f)
				.setFixXRotation(true)
				.setPivot(new Vector3f(0,-1,0))
				.setOneTriangle(true)
				.setSpeed(0)
				.apply();
		
		ParticleSystem flash1 = new ParticleSystem(standard)
				.setBurstSpawnRate(1)
				.setConstantSpawnRate(0)
				.setSpeed(0)
				.setDepthTesting(false)
				.setParticleLifeLenght(0.2f)
				.setScale(new Vector3f(20, 20, 20))
				.setTexture(texture)
				.setPosition(position)
				.setOffset(new Vector3f(0,5,0))
				.setColor(new Vector3f(0.68f, 085f, 0.99f))
				.apply();
		
		
		ParticleSystem smallflash1 = new ParticleSystem(standard)
				.setBurstSpawnRate(1)
				.setConstantSpawnRate(0)
				.setSpeed(0)
				.setDepthTesting(false)
				.setParticleLifeLenght(0.5f)
				.setScale(new Vector3f(3, 3, 3))
				.setTexture(texture)
				.setPosition(position)
				.setOffset(new Vector3f(0,5,0))
				.setColor(new Vector3f(0.68f, 085f, 0.99f))
				.apply();
	
		
		ParticleSystem flash2 = new ParticleSystem(standard)
				.setBurstSpawnRate(1)
				.setConstantSpawnRate(0)
				.setSpeed(0)
				.setDepthTesting(false)
				.setParticleLifeLenght(0.1f)
				.setScale(new Vector3f(5, 5, 5))
				.setColor(new Vector3f(0.68f, 085f, 0.99f))
				.setTexture(texture)
				.setPosition(position)
				.setSpawnTimeOffset(0.33f)
				.apply();
	
		
		ParticleSystem smallflash2 = new ParticleSystem(standard)
				.setBurstSpawnRate(1)
				.setConstantSpawnRate(0)
				.setSpeed(0)
				.setDepthTesting(false)
				.setParticleLifeLenght(0.35f)
				.setSpawnTimeOffset(0.33f)
				.setScale(new Vector3f(3, 3, 3))
				.setTexture(texture)
				.setPosition(position)
				.setColor(new Vector3f(0.68f, 085f, 0.99f))
				.apply();
		
		ParticleSystem splash = new ParticleSystem(standard)
				.setBurstSpawnRate(10)
				.setConstantSpawnRate(0)
				.setStartVelocityRandomness(1)
				.setSpeed(10)
				.setVelocity(new Vector3f(0,5,0))
				.setGravityAffect(0.3f)
				.setDepthTesting(false)
				.setParticleLifeLenght(0.35f)
				.setSpawnTimeOffset(0.4f)
				.setScale(new Vector3f(0.3f, 0.3f, 0.3f))
				.setTexture(texture)
				.setPosition(position)
				.setColor(new Vector3f(0.68f, 085f, 0.99f))
				.apply();
		
	}

}

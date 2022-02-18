package ParticleEffects;

import org.lwjgl.util.vector.Vector3f;

import DefaultParticleShader.DefaultParticleShader;
import Engine.Renderer;
import LightningShader.LightningShader;
import LightningV2Shader.LightningV2Shader;
import Loader.Loader;
import MainShader.ParticleShader;
import Particles.ParticleEffect;
import Particles.ParticleSystem;
import Particles.ParticleTexture;
import Tools.Curve;

public class LightningEffect extends ParticleEffect{
	
	public LightningEffect(Vector3f position) {
		
		ParticleShader shader = new LightningV2Shader();
		
		ParticleTexture texture = Loader.loadParticleTexture("particleTextures/defaultParticle");
		ParticleSystem p = new ParticleSystem(shader)
				.setSystemLifeLenght(100)
				.setConstantSpawnRate(0)
				.setParticleLifeLenght(0.5f)
				.setTexture(texture)
				.setBurstSpawnRate(1)
				.setScaleOverLifetime(Curve.constant)
				.setPosition(position)
				.setSpeedRandomness(0f)
				.setScale(new Vector3f(2, 5, 1))
				.setStartVelocityRandomness(1f)
				.setFixXRotation(true)
				.setPivot(new Vector3f(0,-1,0))
				.setSpeed(0)
				.apply();
		
	}

}

package ParticleEffects;

import org.lwjgl.util.vector.Vector3f;

import Engine.Renderer;
import Loader.Loader;
import ParticleShader.ParticleShader;
import Particles.ParticleEffect;
import Particles.ParticleSystem;
import Particles.ParticleTexture;

public class LightningEffect extends ParticleEffect{
	
	public LightningEffect(Vector3f position) {
		
		ParticleShader shader = new ParticleShader(Renderer.getProjectionMatrix());
		
		ParticleTexture texture = Loader.loadParticleTexture("bunt");
		ParticleSystem p = new ParticleSystem(shader)
				.setLifeLenght(0.5f)
				.setConstantSpawnRate(0)
				.setTexture(texture)
				.setBurstSpawnRate(1)
				.setPosition(position)
				.apply();
		
	}

}

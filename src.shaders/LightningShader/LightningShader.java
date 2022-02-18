package LightningShader;

import Loader.Loader;
import MainShader.ParticleShader;
import Particles.Particle;
import Particles.ParticleSystem;
import Particles.ParticleTexture;
import Textures.PerlinNoise;

public class LightningShader extends ParticleShader{
	
	private static final String VERTEX_FILE = "src.shaders/LightningShader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src.shaders/LightningShader/fragmentShader.txt";
	
	private int location_particletexture;
	private ParticleTexture particleTexture;
	private int location_noiseTexture;
	private ParticleTexture noiseTexture;

	public LightningShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		location_particletexture = super.getUniformLocation("particleTexture");
		location_noiseTexture = super.getUniformLocation("noiseTexture");
		
		particleTexture = Loader.loadParticleTexture("particleTextures/lightning");
		noiseTexture = Loader.loadParticleTexture("particleTextures/lightning");

	}

	@Override
	protected void getUniformLocations() {
	}

	@Override
	public void perParticleUniforms(Particle p) {
	}

	@Override
	public void perSystemUniforms(ParticleSystem p) {
		super.loadTextureToUniform(particleTexture.getTextureId(), 0, location_particletexture);
		super.loadTextureToUniform(noiseTexture.getTextureId(), 1, location_noiseTexture);
	}

}

package DefaultParticleShader;

import MainShader.ParticleShader;
import Particles.Particle;
import Particles.ParticleSystem;

public class DefaultParticleShader extends ParticleShader{
	
	private static final String VERTEX_FILE = "src.shaders/DefaultParticleShader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src.shaders/DefaultParticleShader/fragmentShader.txt";
	
	private int location_particleTexture;
	private int location_color;

	public DefaultParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		
		location_particleTexture = super.getUniformLocation("particleTexture");
		location_color = super.getUniformLocation("color");
	}

	@Override
	protected void getUniformLocations() {
	}

	@Override
	public void perParticleUniforms(Particle p) {
	}

	@Override
	public void perSystemUniforms(ParticleSystem p) {
		super.loadVector(location_color, p.getColor());
		super.loadTextureToUniform(p.getTexture().getTextureId(), 0, location_particleTexture);
	}

}

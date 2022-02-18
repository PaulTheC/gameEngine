package LightningV2Shader;

import org.lwjgl.opengl.GL11;

import MainShader.ParticleShader;
import Particles.Particle;
import Particles.ParticleSystem;

public class LightningV2Shader extends ParticleShader{
	
	private static final String VERTEX_FILE = "src.shaders/LightningV2Shader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src.shaders/LightningV2Shader/fragmentShader.txt";
	private static final String GEOMETRY_FILE = "src.shaders/LightningV2Shader/geometryShader.txt";

	public LightningV2Shader() {
		super(VERTEX_FILE, GEOMETRY_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getUniformLocations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void perParticleUniforms(Particle p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void perSystemUniforms(ParticleSystem p) {
		// TODO Auto-generated method stub
		
	}

}

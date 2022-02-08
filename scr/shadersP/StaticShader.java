package shadersP;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src.shaders/ParticleShader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src.shaders/ParticleShader/fragmentShader.txt";
	
	private int location_projectionViewMatrix;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionViewMatrix = super.getUniformLocation("projectionViewMatrix");
		
	}
	
	public void loadProjectionViewMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionViewMatrix, matrix);
	}
	
	

}

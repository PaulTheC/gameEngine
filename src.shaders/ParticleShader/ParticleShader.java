package ParticleShader;

import org.lwjgl.util.vector.Matrix4f;

import Camera.Camera;
import Entiys.Entity;
import Lights.Light;
import MainShader.StaticShader;

public class ParticleShader extends StaticShader {

	private static final String VERTEX_FILE = "src.shaders/ParticleShader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src.shaders/ParticleShader/fragmentShader.txt";

	private int location_modelViewMatrix;
	private int location_projectionMatrix;

	public ParticleShader(Matrix4f projectionMatrix) {
		super(VERTEX_FILE, FRAGMENT_FILE);
		start();
		loadProjectionMatrix(projectionMatrix);
		stop();
	}
	
	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
	}

	public void loadModelViewMatrix(Matrix4f modelView) {
		super.loadMatrix(location_modelViewMatrix, modelView);
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

	@Override
	protected String[] getAttributes() {
		// TODO Auto-generated method stub
		return new String[]{"position"};
	}

	@Override
	public void loadTransformationMatrix(Matrix4f matrix) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadViewMatrix(Camera camera) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadArguments(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadLights(Light[] lights) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadMaterial(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanUp(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Entity entity) {
		// TODO Auto-generated method stub
		
	}

}

package MainShader;

import org.lwjgl.util.vector.Matrix4f;

import Camera.Camera;
import Engine.Renderer;
import Entiys.Entity;
import Lights.Light;
import Particles.Particle;
import Particles.ParticleSystem;
import Utilities.Time;

public abstract class ParticleShader extends StaticShader {

	private int location_modelViewMatrix;
	private int location_projectionMatrix;
	private int location_time;
	private int location_timeCreated;
	private int location_age;
	private int location_sinTime;
	
	public ParticleShader(String VERTEX_FILE, String FRAGMENT_FILE) {
		super(VERTEX_FILE, FRAGMENT_FILE);
		start();
		loadProjectionMatrix(Renderer.getProjectionMatrix());
		stop();
		ShaderMaster.removeShader(this);
	}
	
	public ParticleShader(String VERTEX_FILE, String GEOMETRY_FILE, String FRAGMENT_FILE) {
		super(VERTEX_FILE, GEOMETRY_FILE, FRAGMENT_FILE);
		start();
		loadProjectionMatrix(Renderer.getProjectionMatrix());
		stop();
		ShaderMaster.removeShader(this);
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_time = super.getUniformLocation("time");
		location_sinTime = super.getUniformLocation("sinTime");
		location_timeCreated = super.getUniformLocation("timeCreated");
		location_age = super.getUniformLocation("age");
		getUniformLocations();
	}
	
	protected abstract void getUniformLocations();
	protected abstract void perParticleUniforms(Particle p);
	protected abstract void perSystemUniforms(ParticleSystem p);
	
	public final void loadPerParticleUniforms(Particle p ) {
		perParticleUniforms(p);
		if(location_timeCreated != -1)
			super.loadFloat(location_timeCreated, p.getTimeCreated());
		if(location_age != -1)
			super.loadFloat(location_age, (float)(p.getAge() / p.getLifeLenght()));
		
	}
	
	public final void loadPerSystemUniforms(ParticleSystem p ) {
		perSystemUniforms(p);
		
		if(location_time != -1)
			super.loadFloat(location_time, Time.getTimeAlive());
		if(location_sinTime != -1)
			super.loadFloat(location_sinTime, (float) Math.sin(Time.getTimeAlive()));
		
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

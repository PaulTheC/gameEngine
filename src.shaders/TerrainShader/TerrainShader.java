package TerrainShader;

import java.util.ArrayList;
import java.util.Iterator;

import javax.management.modelmbean.ModelMBean;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import Camera.MainCamera;
import Engine.Main;
import Entiys.Entity;
import Lights.Light;
import MainShader.StaticShader;
import Models.TexturedModel;
import Tools.Maths;

public class TerrainShader extends StaticShader{
	
	private static final String VERTEX_FILE = "src.shaders/TerrainShader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src.shaders/TerrainShader/fragmentShader.txt";
	
	private static final int MAX_LIGHTS = 5;
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_hasTexture;
	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_lightAttenuation[];
	private int location_diffuse;
	private int location_diffuseColor;
	private int location_strength[];
	private int location_offset;
	
	
	

	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		getAllUniformArrayLocations();
		
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_hasTexture = super.getUniformLocation("hasTexture");
		location_diffuse = super.getUniformLocation("diffuse");
		location_diffuseColor = super.getUniformLocation("diffuseColor");
		location_offset = super.getUniformLocation("offset");
		
	}
	
	
	private void getAllUniformArrayLocations() {
		location_lightColor = new int[MAX_LIGHTS];
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightAttenuation = new int[MAX_LIGHTS];
		location_strength = new int[MAX_LIGHTS];
		
		
		for (int i = 0; i < MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition["+i+"]");
			location_lightColor[i] = super.getUniformLocation("lightColor["+i+"]");
			location_lightAttenuation[i] = super.getUniformLocation("attenuation["+i+"]");
			location_strength[i] = super.getUniformLocation("strength["+i+"]");
		}
		
	}
	
	@Override
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	@Override
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	@Override
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	@Override
	public void loadArguments(Entity entity) {
		super.loadBoolean(location_hasTexture, entity.getModel().getHasTexture());
		loadViewMatrix(MainCamera.getCamera());
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale(), MainCamera.getCamera(), false);
		loadTransformationMatrix(transformationMatrix);
		loadMaterial(entity);
		super.loadVector(location_offset, entity.getPosition());
		
		
		
	}
	
	@Override
	public void loadLights(Light[] lights) {
	
		
		for (int i = 0; i < MAX_LIGHTS; i++) {
			super.loadVector(location_lightColor[i], lights[i].getColor());
			super.loadVector(location_lightPosition[i], lights[i].getPosition());
			super.loadVector(location_lightAttenuation[i], lights[i].getAttenuation());
			super.loadFloat(location_strength[i], lights[i].getStrength());
		}
	}


	@Override
	protected String[] getAttributes() {
		String[] strings = {"position", "textureCoordinates", "normals"};
		return strings;
	}

	@Override
	public void prepare(Entity entity) {
		start();
	
		GL32.glProvokingVertex(GL32.GL_FIRST_VERTEX_CONVENTION);
		GL30.glBindVertexArray(entity.getModel().getRawModel().getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
	}
	
	
	public void cleanUp(Entity entity) {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
		
		stop();
		
	}

	@Override
	public void render(Entity entity) {
		

		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		
		loadArguments(entity);
		
		if(entity.getModel().getHasTexture()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getTexture().getTextureID());
		}
		GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}

	@Override
	public void loadMaterial(Entity entity) {
		
		super.loadFloat(location_diffuse, entity.getMaterial().diffuse());
		super.loadVector(location_diffuseColor, entity.getMaterial().diffuseColor());
		
		
	}

}

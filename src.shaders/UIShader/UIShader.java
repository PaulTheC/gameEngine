package UIShader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Camera.Camera;
import Camera.MainCamera;
import Engine.Main;
import Entiys.Entity;
import Lights.Light;
import MainShader.StaticShader;
import Models.RawModel;
import Models.TexturedModel;
import Tools.Maths;

public class UIShader extends StaticShader{

	private static final String VERTEX_FILE = "src.shaders/UIShader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src.shaders/UIShader/fragmentShader.txt";
	
	private int location_transformationMatrix;

	

	public UIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected String[] getAttributes() {
		String[] strings = {"position", "textureCoordinates"};
		return strings;
	}

	@Override
	public void loadViewMatrix(Camera camera) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadProjectionMatrix(Matrix4f projection) {
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
	public void prepare(Entity entity) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void cleanUp(Entity entity) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void render(Entity entity) {
		TexturedModel model = entity.getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale(), MainCamera.getCamera(), true);
		entity.getShader().loadTransformationMatrix(transformationMatrix);
		if(model.getHasTexture()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
		}
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}


	@Override
	public void loadMaterial(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	


}

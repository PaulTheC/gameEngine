package MainShader;

import java.beans.ParameterDescriptor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.FloatBuffer;

import javax.xml.stream.events.StartDocument;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import Entiys.Entity;
import Lights.Light;
import Models.TexturedModel;

public abstract class StaticShader {

	private int programID;
	private int vertexShaderID;
	private int geometryShaderID;
	private int fragmentShaderID;

	protected abstract String[] getAttributes();

	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	public StaticShader(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
		ShaderMaster.addShader(this);
	}
	
	public StaticShader(String vertexFile, String fragmentFile, boolean addedToRenderer) {
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	public StaticShader(String vertexFile, String geometryFile,String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		geometryShaderID = loadShader(geometryFile, GL32.GL_GEOMETRY_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, geometryShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
		ShaderMaster.addShader(this);
	}

	protected abstract void getAllUniformLocations();

	public abstract void loadTransformationMatrix(Matrix4f matrix);

	public abstract void loadViewMatrix(Camera camera);

	public abstract void loadProjectionMatrix(Matrix4f projection);

	public abstract void loadArguments(Entity entity);

	public abstract void loadLights(Light[] lights);
	
	public abstract void loadMaterial(Entity entity);

	protected int getUniformLocation(String uniformName) {
		int location = GL20.glGetUniformLocation(programID, uniformName);
		if (location == -1) {
//			System.err.println("\nCoundn?t locate uniform Variable " + uniformName
//					+ "\nThis occurrs when uniform isn?t use in the shader or the name is spelled incorrectly\n");
		}
		
		return location;
	}

	public void start() {
		GL20.glUseProgram(programID);
		GL11.glGetError();
	}

	public void stop() {
		GL20.glUseProgram(0);
	}

	public void destroy() {
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, geometryShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(geometryShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	
	public abstract void prepare(Entity entity);
	
	public abstract void cleanUp(Entity entity);
	
	public abstract void render(Entity entity);

	
	private void bindAttributes() {
		int i = 0;
		for(String attribute : getAttributes()) {
			bindAttribute(i, attribute);
			i++;
		}
	}
	
	public void loadTextureToUniform(int textureId, int textureLocation, int uniformLocation) {
		float i = 1;
		if(textureLocation > 8)
			//throw NotImplementedException
			i = i / 0;
		
		switch(textureLocation) {
		case 0:
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			break;
		case 1:
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			break;
		case 2:
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			break;
		case 3:
			GL13.glActiveTexture(GL13.GL_TEXTURE3);
			break;
		case 4:
			GL13.glActiveTexture(GL13.GL_TEXTURE4);
			break;
		case 5:
			GL13.glActiveTexture(GL13.GL_TEXTURE5);
			break;
		case 6:
			GL13.glActiveTexture(GL13.GL_TEXTURE6);
			break;
		case 7:
			GL13.glActiveTexture(GL13.GL_TEXTURE7);
			break;
		case 8:
			GL13.glActiveTexture(GL13.GL_TEXTURE8);
			break;
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		loadInt(uniformLocation, textureLocation);
	}

	protected void bindAttribute(int attribute, String variableName) {
		start();
		GL20.glBindAttribLocation(programID, attribute, variableName);
		GL11.glGetError();
	}

	protected void loadFloat(int location, float value) {
		start();
		GL20.glUniform1f(location, value);
	}
	
	protected void loadInt(int location, int value) {
		start();
		GL20.glUniform1i(location, value);
	}

	protected void loadVector(int location, Vector3f vector) {
		start();
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
		GL11.glGetError();
	}

	protected void loadBoolean(int location, boolean value) {
		start();
		float toLoad = 0;
		if (value) {
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
		GL11.glGetError();
	}

	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		start();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
		GL11.glGetError();
	}

	private static int loadShader(String file, int type) {
		StringBuilder shaderVersion = new StringBuilder();
		
		
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.startsWith("#version")) {
					shaderVersion.append("#version 430//\n");
					shaderSource.append("//\n");
				}else
					shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		StringBuilder lib = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src.shaders/MainShader/lib.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				lib.append(line).append("//\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, new CharSequence[] {shaderVersion, lib, shaderSource});
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}

}

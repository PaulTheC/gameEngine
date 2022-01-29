package Materials;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import Entiys.Entity;
import MainShader.StaticShader;
import Models.RawModel;
import Models.TexturedModel;
import Tools.Maths;

public abstract class StaticMaterial{
	
	public float diffuse() {
		return 0.0f;
	}
	
	public final Vector3f diffuseColor()
	{
		return new Vector3f(0,0.15f,0);
	}

}

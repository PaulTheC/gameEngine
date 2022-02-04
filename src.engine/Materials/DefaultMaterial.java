package Materials;

import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import Entiys.Entity;
import MainShader.StaticShader;
import Models.RawModel;
import Models.TexturedModel;
import Tools.Maths;

public class DefaultMaterial extends StaticMaterial{
	
	@Override
	public final float diffuse() {
		return 2f;
	}
	
}

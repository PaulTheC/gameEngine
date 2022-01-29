package EntityPresets;

import org.lwjgl.util.vector.Vector3f;

import Engine.Main;
import Entiys.Entity;
import Loader.Loader;
import Materials.StaticMaterial;
import Models.RawModel;
import Models.TexturedModel;

public class Plane {
	
	 static float[] vertices = {				
			    -1,1,0,	
				-1,-1,0,
				1,-1,0,
				1,1,0,
	   };
	
	 static float[] textureCoords = {
			
			0,0,
			0,1,
			1,1,
			1,0,	};
	
	 static int[] indices = {
			0,1,3,	
			3,1,2,};
	
	
	 static float[] normals = {
				0.0000f, 0.0000f, -1.0000f,
				0.0000f, 0.0000f, -1.0000f,
				0.0000f, 0.0000f, -1.0000f,
				0.0000f, 0.0000f, -1.0000f,

		};
	
	public static Entity generatePlane() {
		
		Loader loader = new Loader();
		
		
		RawModel model =new RawModel(loader.loadToVAO(vertices, textureCoords, indices, normals), indices.length);
		
		TexturedModel tModel = new TexturedModel(model);
		
		return new Entity(tModel, new Vector3f(0,0,0),0,0,0,1, Main.shader);
		
	}

}

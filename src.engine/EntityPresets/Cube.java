package EntityPresets;

import org.lwjgl.util.vector.Vector3f;

import Engine.Main;
import Entiys.Entity;
import Loader.Loader;
import Materials.StaticMaterial;
import Models.RawModel;
import Models.TexturedModel;

public class Cube{
	
	
	static float[] vertices = {			
			-1.0f, 1.0f, -1.0f,//roh  4
			1.0f, 1.0f, 1.0f,//lov  2
			1.0f, 1.0f, -1.0f, //loh  0
			
			1.0f, 1.0f, 1.0f,//lov  2
			-1.0f, -1.0f, 1.0f,//ruv  7
			1.0f, -1.0f, 1.0f,//luv  3
			
			-1.0f, 1.0f, 1.0f,//rov  6				
			-1.0f, -1.0f, -1.0f,//ruh  5
			-1.0f, -1.0f, 1.0f,//ruv  7
			
			1.0f, -1.0f, -1.0f,//luh  1
			-1.0f, -1.0f, 1.0f,//ruv  7
			-1.0f, -1.0f, -1.0f,//ruh  5

			1.0f, 1.0f, -1.0f, //loh  0
			1.0f, -1.0f, 1.0f,//luv  3
			1.0f, -1.0f, -1.0f,//luh  1

			-1.0f, 1.0f, -1.0f,//roh  4  
			1.0f, -1.0f, -1.0f,//luh  1
			-1.0f, -1.0f, -1.0f,//ruh  5
			

			-1.0f, 1.0f, -1.0f,//roh  4  
			-1.0f, 1.0f, 1.0f,//rov  6
			1.0f, 1.0f, 1.0f,//lov  2
			

			1.0f, 1.0f, 1.0f,//lov  2
			-1.0f, 1.0f, 1.0f,//rov  6
			-1.0f, -1.0f, 1.0f,//ruv  7
			

			-1.0f, 1.0f, 1.0f,//rov  6
			-1.0f, 1.0f, -1.0f,//roh  4  
			-1.0f, -1.0f, -1.0f,//ruh  5

			1.0f, -1.0f, -1.0f,//luh  1
			1.0f, -1.0f, 1.0f,//luv  3
			-1.0f, -1.0f, 1.0f,//ruv  7

			1.0f, 1.0f, -1.0f, //loh  0
			1.0f, 1.0f, 1.0f,//lov  2
			1.0f, -1.0f, 1.0f,//luv  3

			-1.0f, 1.0f, -1.0f,//roh  4 
			1.0f, 1.0f, -1.0f, //loh  0
			1.0f, -1.0f, -1.0f,//luh  1
			
	};
	
	static float[] colors = {
			0,0,1,1,
			1,1,1,1,
			1,1,1,1,
			1,1,1,1,
			
			0,0,1,1,
			1,1,1,1,
			1,1,1,1,
			1,1,1,1,
			
			0,0,1,1,
			1,1,1,1,
			1,1,1,1,
			1,1,1,1,
			
			0,0,1,1,
			1,1,1,1,
			1,1,1,1,
			1,1,1,1,
			
			0,0,1,1,
			1,1,1,1,
			1,1,1,1,
			1,1,1,1,
			
			0,0,1,1,
			1,1,1,1,
			1,1,1,1,
			1,1,1,1,
	};
	
	static float[] textureCoords = {
			
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,			
			0,0,
			0,1,
			1,1,
			1,0,
			

			
	};
	
	static int[] indices = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31, 32, 33, 34, 35

	};
	
	static float[] normals = {
			
			0.0000f, 1.0000f, 0.0000f,
			0.0000f, 1.0000f, 0.0000f,
			0.0000f, 1.0000f, 0.0000f,
			
			0.0000f, 0.0000f, 1.0000f,
			0.0000f, 0.0000f, 1.0000f,
			0.0000f, 0.0000f, 1.0000f,
			
			-1.0000f, 0.0000f, 0.0000f,
			-1.0000f, 0.0000f, 0.0000f,
			-1.0000f, 0.0000f, 0.0000f,
			
			0.0000f, -1.0000f, 0.0000f,
			0.0000f, -1.0000f, 0.0000f,
			0.0000f, -1.0000f, 0.0000f,
			
			1.0000f, 0.0000f, 0.0000f,
			1.0000f, 0.0000f, 0.0000f,
			1.0000f, 0.0000f, 0.0000f,
			
			0.0000f, 0.0000f, -1.0000f,
			0.0000f, 0.0000f, -1.0000f,
			0.0000f, 0.0000f, -1.0000f,
			
			0.0000f, 1.0000f, 0.0000f,
			0.0000f, 1.0000f, 0.0000f,
			0.0000f, 1.0000f, 0.0000f,
			
			0.0000f, 0.0000f, 1.0000f,
			0.0000f, 0.0000f, 1.0000f,
			0.0000f, 0.0000f, 1.0000f,
			
			-1.0000f, 0.0000f, 0.0000f,
			-1.0000f, 0.0000f, 0.0000f,
			-1.0000f, 0.0000f, 0.0000f,
			
			0.0000f, -1.0000f, 0.0000f,
			0.0000f, -1.0000f, 0.0000f,
			0.0000f, -1.0000f, 0.0000f,
			
			1.0000f, 0.0000f, 0.0000f,
			1.0000f, 0.0000f, 0.0000f,
			1.0000f, 0.0000f, 0.0000f,
			
			0.0000f, 0.0000f, -1.0000f,
			0.0000f, 0.0000f, -1.0000f,
			0.0000f, 0.0000f, -1.0000f,
			
			
	};
	
	public static Entity generateCube(Vector3f color, StaticMaterial sm) {
		

		
		
		RawModel model = new RawModel(Loader.loadToVAO(vertices, textureCoords, indices, normals), indices.length);	
		
		TexturedModel tModel = new TexturedModel(model);
		
		return new Entity(tModel, new Vector3f(0,0,0),0,0,0,1, Main.shader);
	}

}

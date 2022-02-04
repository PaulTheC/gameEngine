package Terrain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Colisions.Raycast;
import Loader.Loader;
import Loader.Saver;
import Terraforming.BasicBrush;
import Terraforming.SmoothenBrush;
import testingScenes.TestScene;

public class Terraformer {

	
	public static void applyHeightChange(int xPos, int zPos, float[] heightMap, float strenght, boolean inverted) {
		
		if(Math.sqrt(heightMap.length) % 1 != 0) {
			System.err.println("The size of the heightMap is not a power of 2.");
			System.exit(0);
		}
		
		int gridSize = (int) Math.ceil(TestScene.map.getTerrainInGrid(100, 100).gridSquareSize);
		
		for(int x = 0; x < Math.sqrt(heightMap.length); x++) {
			for(int z = 0; z < Math.sqrt(heightMap.length); z++) {
				TestScene.map.setHeight((int)((x - Math.sqrt(heightMap.length)/2 + 1) * gridSize + xPos),(int)( (z - Math.sqrt(heightMap.length)/2 +1 ) * gridSize + zPos ), strenght * heightMap[x * (int)Math.sqrt(heightMap.length) + z], inverted);
			}
		}

		
	}
	
	
	
	public static void terraform(boolean inverted) {
		
		Vector3f intersection = Raycast.terrainRaycast();
		
		if(intersection != null) {
			Vector2f nearestVertex = TestScene.map.getNearestVertex(intersection.x, intersection.z);
			TestScene.cube.setPosition(new Vector3f(nearestVertex.x, intersection.y, nearestVertex.y));	
			TerrainArea area = TestScene.map.getTerrainInGrid(intersection.x, intersection.z);
			
//			Terraformer.terraform((int)nearestVertex.x + 1, (int)nearestVertex.y + 1, new float[] {1,1.5f,1,1.5f,2,1.5f,1,1.5f,1}, 5, false);
			Brush brush = getActiveBrush();
			Terraformer.applyHeightChange((int)nearestVertex.x + 1, (int)nearestVertex.y + 1, brush.apply(nearestVertex.x, nearestVertex.y), 5, inverted);
			float[] verticies = area.calculateVertices(area.getHeightMap());
			Loader.updateVertexPositions(verticies, area.getModel().getRawModel().getVaoID());
			Loader.updateVertexNormals(area.calculateNormals(verticies), area.getModel().getRawModel().getVaoID());
			Saver.saveHeightMap(area.getHeightMap(), area.MAX_HEIGHT, area.getHeightMapFile(), (int)area.getAreaGridPosition().x, (int)area.getAreaGridPosition().y);
		}

		
		
	}
	
	
	private static Brush getActiveBrush() {
		

		if(Keyboard.isKeyDown(Keyboard.KEY_1))
			return new SmoothenBrush();
		
		
		System.out.println("Selected Basic Brush");
		return new BasicBrush();
		
	}
	
	


}

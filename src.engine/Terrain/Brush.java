package Terrain;

import org.lwjgl.util.vector.Vector2f;

import testingScenes.TestScene;

public abstract class Brush {
	
	public abstract float[] applyHeight(float worldX, float worldZ);
	public abstract float[] applyColors(float worldX, float worldZ);
	
	protected int toGridCoords(float x) {
		
		float gridSize = (float) (TestScene.map.getTerrainInGrid(100, 100).gridSquareSize);
		return (int)(x / gridSize);
		
	}
	
	protected TerrainArea getTerrainAreRelativeToPosition(float worldX, float worldZ, int offsetX, int offsetZ) {
		
		Vector2f pos = TestScene.map.getTerrainTileInGrid(worldX, worldZ);
		
		return TestScene.map.getTerrainInGrid((int) (pos.x + offsetX), (int) (pos.y + offsetZ));
		
	}

}
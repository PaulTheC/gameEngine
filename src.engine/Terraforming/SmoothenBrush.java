package Terraforming;

import Terrain.Brush;
import Terrain.TerrainArea;
import testingScenes.TestScene;

public class SmoothenBrush extends Brush{

	@Override
	public float[] apply(float worldX, float worldZ) {
		
		return new float[] {getTerrainAreRelativeToPosition(worldX, worldZ, 0, 0).getSmoothHeight(toGridCoords(worldX), toGridCoords(worldZ))};
		
	}

}

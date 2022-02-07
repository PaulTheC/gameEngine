package Terraforming;

import Terrain.Brush;
import Terrain.TerrainArea;
import testingScenes.TestScene;

public class SmoothenBrush extends Brush{

	@Override
	public float[] applyHeight(float worldX, float worldZ) {
		
		return new float[] {getTerrainAreRelativeToPosition(worldX, worldZ, 0, 0).getSmoothHeight(toGridCoords(worldX), toGridCoords(worldZ))};
		
	}

	@Override
	public float[] applyColors(float worldX, float worldZ) {
		// TODO Auto-generated method stub
		return null;
	}

}

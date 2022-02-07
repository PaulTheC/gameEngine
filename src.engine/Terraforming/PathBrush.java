package Terraforming;

import Terrain.Brush;
import Terrain.TerrainArea;

public class PathBrush extends Brush{

	@Override
	public float[] applyHeight(float worldX, float worldZ) {
		TerrainArea area = getTerrainAreRelativeToPosition(worldX, worldZ, 0, 0);
		System.out.println(toGridCoords(worldX) + "     " + toGridCoords(worldZ) + "     " + area.gridSquareSize + "     " + worldX + "     " + worldZ);
		int radius = 3;
		float origin = area.getHeight(toGridCoords(worldX), toGridCoords(worldZ));
		
		float[] height = new float[radius * radius];
		for(int x = 0; x < radius; x++) {
			for(int z =0; z < radius; z++) {
				float posX = (float) (worldX + (x - Math.ceil(radius / 2)) * area.gridSquareSize);
				float posZ = (float) (worldZ + (z - Math.ceil(radius / 2)) * area.gridSquareSize);
				height[x * radius + z] = -(getTerrainAreRelativeToPosition(posX, posZ, 0, 0).getHeight(toGridCoords(posX), toGridCoords(posZ)) - origin);
				System.out.println(toGridCoords(posX) + "  " + toGridCoords(posZ) + "    " + origin + "    " + getTerrainAreRelativeToPosition(posX, posZ, 0, 0).getHeight(toGridCoords(posX), toGridCoords(posZ)) + "    " + height[x * radius + z]);

			}
		}
		
		return height;
		
	}

	@Override
	public float[] applyColors(float worldX, float worldZ) {
		// TODO Auto-generated method stub
		return null;
	}

}

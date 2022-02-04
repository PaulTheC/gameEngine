package Terraforming;

import Terrain.Brush;

public class BasicBrush extends Brush{

	@Override
	public float[] apply(float worldX, float worldZ) {
		return new float[] {1};
	}

}

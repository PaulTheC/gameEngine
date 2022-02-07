package Terraforming;

import Terrain.Brush;

public class BasicBrush extends Brush{

	@Override
	public float[] applyHeight(float worldX, float worldZ) {
		return new float[] {1};
	}

	@Override
	public float[] applyColors(float worldX, float worldZ) {
		// TODO Auto-generated method stub
		return null;
	}

}

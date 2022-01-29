package Terrain;

import java.util.Random;

import LowPolyTerrainShader.LowPolyTerrainShader;
import TerrainShader.TerrainShader;

public class Map {
	
	public final int MAP_SIZE = 2;
	private final int AREA_SIZE = 800;
	private TerrainArea[][] map = new TerrainArea[MAP_SIZE][MAP_SIZE];
	
	private TerrainShader shader = new TerrainShader();
	
	
	public Map() {
		
		
		
//		for(int i = 0; i < MAP_SIZE; i++) {
//			for(int j = 0; j < MAP_SIZE; j++) {
//				if(new Random().nextFloat() > 0.95f) {
//					map[i][j] = new TerrainArea("height");
//				}else {
//					map[i][j] = new TerrainArea("flat");
//				}
//				map[i][j].setAreaGridPosition(i, j);
//				System.out.println(map[i][j].getPosition());
//			}
//		}
		
		
		map[0][0] = new TerrainArea("terrain/mountains/1", shader); 
		map[0][0].setAreaGridPosition(0, 0);
		
		map[1][0] = new TerrainArea("terrain/mountains/1", shader); 
		map[1][0].setAreaGridPosition(1, 0);
		
		map[0][1] = new TerrainArea("lowpoly_height", shader); 
		map[0][1].setAreaGridPosition(0, 1);
		
		map[1][1] = new TerrainArea("lowpoly_height", shader); 
		map[1][1].setAreaGridPosition(1, 1);
		
	}
	
	
	
	public float getHeightOfTerrain(float x, float z) {
	
		
		int gridX = (int)Math.floor((x + MAP_SIZE / 2) / AREA_SIZE);
		int gridZ = (int)Math.floor((z + MAP_SIZE / 2) / AREA_SIZE);
		
		//System.out.println(gridX + "     "+ gridZ);
		
		if(gridX >= MAP_SIZE|| gridZ >= MAP_SIZE|| gridX < 0 || gridZ < 0) {
			return 0;
		}
		
		
		return map[gridX][gridZ].getHeightOfTerrain(x, z);
		
	}

}

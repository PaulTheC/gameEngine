package Terrain;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import LowPolyTerrainShader.LowPolyTerrainShader;
import TerrainShader.TerrainShader;
import testingScenes.TestScene;

public class Map {
	
	public final int MAP_SIZE = 2;
	private final int AREA_SIZE = 800;
	private TerrainArea[][] map = new TerrainArea[MAP_SIZE][MAP_SIZE];
	
	private LowPolyTerrainShader shader = new LowPolyTerrainShader();
	
	
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
		
		
		map[0][0] = new TerrainArea("map/area[0][0]", shader); 
		map[0][0].setAreaGridPosition(0, 0);
		
		map[1][0] = new TerrainArea("map/area[1][0]", shader); 
		map[1][0].setAreaGridPosition(1, 0);
		
		map[0][1] = new TerrainArea("map/area[0][1]", shader); 
		map[0][1].setAreaGridPosition(0, 1);
		
		map[1][1] = new TerrainArea("map/area[1][1]", shader); 
		map[1][1].setAreaGridPosition(1, 1);
		
	}
	
	
	
	public float getHeightOfTerrain(float x, float z) {

		Vector2f grid = getTerrainTileInGrid(x, z);
		
		
		return map[(int) grid.x][(int) grid.y].getHeightOfTerrain(x, z);
		
	}
	
	
	public Vector2f getTerrainTileInGrid(float worldX, float worldZ) {
		
		int gridX = (int)Math.floor((worldX + MAP_SIZE / 2) / AREA_SIZE);
		int gridZ = (int)Math.floor((worldZ + MAP_SIZE / 2) / AREA_SIZE);

		if(gridX >= MAP_SIZE|| gridZ >= MAP_SIZE|| gridX < 0 || gridZ < 0) {
			return new Vector2f(0,0);
		}
		return new Vector2f((float)gridX, (float)gridZ);
	}
	
	public TerrainArea getTerrainInGrid(float worldX, float worldZ) {
		
		int gridX = (int)Math.floor((worldX + MAP_SIZE / 2) / AREA_SIZE);
		int gridZ = (int)Math.floor((worldZ + MAP_SIZE / 2) / AREA_SIZE);
		
		if(gridX >= MAP_SIZE|| gridZ >= MAP_SIZE|| gridX < 0 || gridZ < 0) {
			return null;
		}
		return map[gridX][gridZ];
	}
	
	public Vector2f getNearestVertex(float x, float z) {
		
		Vector2f grid = getTerrainTileInGrid(x, z);
	 	Vector2f res = map[(int) grid.x][(int) grid.y].getNearestVertex(x, z);
		
		
		return new Vector2f(res.x + (grid.x) * AREA_SIZE, res.y + (grid.y) * AREA_SIZE);
	}
	
	
	public void setHeight(int x, int y, float value, boolean inverted) {
		Vector2f grid = getTerrainTileInGrid(x, y);
		
		map[(int) grid.x][(int) grid.y].setHeight(x, y, value, inverted);
	}
	
	public float getHeight(float worldX, float worldZ) {
		int gridSize = (int) Math.ceil(TestScene.map.getTerrainInGrid(100, 100).gridSquareSize);
		int gridX = (int)(worldX / gridSize);
		int gridZ = (int)(worldZ / gridSize);
		
		return getTerrainInGrid(worldX, worldZ).getArrayHeight((int)(gridX % AREA_SIZE), (int)(gridZ % AREA_SIZE));
		
	}

}

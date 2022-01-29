package Terrain;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.prefs.BackingStoreException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Engine.Main;
import Entiys.Entity;
import Loader.Loader;
import MainShader.StaticShader;
import Models.RawModel;
import Models.TexturedModel;
import TerrainShader.TerrainShader;
import Tools.Maths;

public class TerrainArea extends Entity{
	
	private final int SIZE = 800;
	private final int MAX_HEIGHT = 100;
	private final int MAX_PIXEL_COLOUR = 256 * 256 * 256;
	private BufferedImage heightMap;
	private float[][] heights; 
	private float x = 0;
	private float z = 0;

	
	public TerrainArea(String heightMapFile){		
		super(null, new TerrainShader());
		super.setModel(new TexturedModel(generateTerrain(heightMapFile), "grass"));
	}
	
	public TerrainArea(String heightMapFile, StaticShader shader){		
		super(null, shader);
		super.setModel(new TexturedModel(generateTerrain(heightMapFile)));
	}
	
	
	private RawModel generateTerrain(String heightMapFile){
		try {
			heightMap = ImageIO.read(new File("res/"+ heightMapFile + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		float[] vertices = calculateVertices();
		float[] normals = calculateNormals(vertices);
		int[] indices = calculateIndices();
	

		
		return new RawModel(Loader.loadToVAO(vertices, new float[] {}, indices, normals), indices.length);
	}
	
	
	private float[] calculateVertices() {
		
		Random r = new Random();
		
		int VERTEX_COUNT = heightMap.getHeight();
		
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int vertexPointer = 0;
		for(int z=0;z<VERTEX_COUNT;z++){
			for(int x=0;x<VERTEX_COUNT;x++){
				vertices[vertexPointer*3] = ((float)x/((float)VERTEX_COUNT - 1) * SIZE) + r.nextFloat(5)-2.5f;
				heights[x][z] = getHeight(x, z);
				vertices[vertexPointer*3+1] = getHeight(x, z);
				vertices[vertexPointer*3+2] = ((float)z/((float)VERTEX_COUNT - 1) * SIZE) + r.nextFloat(5)-2.5f;
				textureCoords[vertexPointer*2] = (float)x/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)z/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		
		float[] newVertices = new float[vertices.length * 2];
		for(int pointer = 0;pointer < newVertices.length; pointer += 6){
			newVertices[pointer] = vertices[pointer / 2];
			newVertices[pointer+1] = vertices[pointer / 2 +1];
			newVertices[pointer+2] = vertices[pointer / 2 +2];
			newVertices[pointer+3] = vertices[pointer / 2];
			newVertices[pointer+4] = vertices[pointer / 2 +1];
			newVertices[pointer+5] = vertices[pointer / 2 +2];
			
			
		}
		
		return newVertices;
	}
	
	public float getHeightOfTerrain(float worldX, float worldZ) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		
		float gridSquareSize = SIZE / ((float)heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		
		if(gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
			return 0;
		}
		
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		
		float answer;
		if (xCoord <= (1-zCoord)) {
			answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ], 0), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		} else {
			answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		
		return answer;
	}
	
	
	private int[] calculateIndices() {
		
		int VERTEX_COUNT = heightMap.getHeight();
		
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		
		int pointer = 0;
		for(int z=0;z<VERTEX_COUNT-1;z++){
			for(int x=0;x<VERTEX_COUNT-1;x++){
				int topLeft = (z*VERTEX_COUNT * 2)+2 * x;
				int topLeft2 = topLeft + 1;
				int topRight = topLeft + 2;
				int bottomLeft = ((z+1)*VERTEX_COUNT*2)+2*x;
				int bottomRight = bottomLeft + 2;
				
				
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
				indices[pointer++] = topLeft2;
				indices[pointer++] = bottomRight;
				indices[pointer++] = topRight;
				
			}
		}
		
		return indices;
		
	}
	
	
	private float[] calculateNormals(float[] vertices) {
		
		float[] newNormals = new float[vertices.length];
		
		Random r = new Random();
		
		int VERTEX_COUNT = heightMap.getHeight();
		
		int pointer = 0;
		for(int z=0;z<VERTEX_COUNT-1;z++){
			for(int x=0;x<VERTEX_COUNT-1;x++){
				int topLeft = (z*VERTEX_COUNT * 2)+2 * x;
				int topLeft2 = topLeft + 1;
				int topRight = topLeft + 2;
				int bottomLeft = ((z+1)*VERTEX_COUNT*2)+2*x;
				int bottomRight = bottomLeft + 2;
				
				Vector3f p1 = new Vector3f(vertices[topLeft * 3], vertices[topLeft * 3+1], vertices[topLeft * 3+2]);
				Vector3f p2 = new Vector3f(vertices[bottomLeft * 3], vertices[bottomLeft * 3+1], vertices[bottomLeft * 3+2]);
				Vector3f p3 = new Vector3f(vertices[bottomRight * 3], vertices[bottomRight * 3+1], vertices[bottomRight * 3+2]);
				
				Vector3f res = calculateNormal(p1, p2, p3);
				newNormals[pointer] = res.x + r.nextFloat(0.1f);
				newNormals[pointer+1] = res.y + r.nextFloat(0.1f);
				newNormals[pointer+2] = res.z + r.nextFloat(0.1f);
				
				
				
				p1 = new Vector3f(vertices[topLeft2 * 3], vertices[topLeft2 * 3+1], vertices[topLeft2 * 3+2]);
				p2 = new Vector3f(vertices[bottomRight * 3], vertices[bottomRight * 3+1], vertices[bottomRight * 3+2]);
				p3 = new Vector3f(vertices[topRight * 3], vertices[topRight * 3+1], vertices[topRight * 3+2]);
				
				res = calculateNormal(p1, p2, p3);
				newNormals[pointer+3] = res.x + r.nextFloat(0.1f);
				newNormals[pointer+4] = res.y + r.nextFloat(0.1f);
				newNormals[pointer+5] = res.z + r.nextFloat(0.1f);
				
				pointer += 6;
			}
		}
		
		
		return newNormals;
		
	}
	
	
	private Vector3f calculateNormal(Vector3f p1, Vector3f p2, Vector3f p3) {
		Vector3f a = new Vector3f();
		Vector3f.sub(p2, p1, a);
		
		Vector3f b = new Vector3f();
		Vector3f.sub(p3, p1, b);
		
		float nx = a.y * b.z - a.z * b.y;
		float ny = a.z * b.x - a.x * b.z;
		float nz = a.x * b.y - a.y * b.x;
		
		return new Vector3f(nx, ny, nz);
	}
	
	public float getHeight(int x, int z) {
		
		if(x < 0 || x >= heightMap.getHeight() ||z < 0 || z >= heightMap.getHeight()) {
			return 0;
		}
		
		float height =  heightMap.getRGB(x, z);
		height += MAX_PIXEL_COLOUR / 2f;
		height /= MAX_PIXEL_COLOUR / 2f;
		height *= MAX_HEIGHT;
		return height;
		
	}
	
	
	public void setAreaPosition(float x, float z) {
		this.x = x;
		this.z = z;
		super.setPosition(new Vector3f(this.x, 0, this.z));
	}
	
	public void setAreaGridPosition(int x, int z) {
		this.x = x * SIZE;
		this.z = z * SIZE;
		super.setPosition(new Vector3f(this.x, 0, this.z));
	}
	
	
	private float getSmoothHeight(int centerX, int centerZ) {
		
		if(centerX < 0 || centerX >= heightMap.getHeight() ||centerZ < 0 || centerZ >= heightMap.getHeight()) {
			return 0;
		}
		
		if(heights[centerX][centerZ] != 0)
			return heights[centerX][centerZ];
		
		float height = getHeight(centerX, centerZ);
		float heightL = getHeight(centerX-1, centerZ);
		float heightR = getHeight(centerX+1, centerZ);
		float heightD = getHeight(centerX, centerZ-1);
		float heightU = getHeight(centerX, centerZ+1);
		float heightLD = getHeight(centerX-1, centerZ-1);
		float heightRD = getHeight(centerX+1, centerZ-1);
		float heightLU = getHeight(centerX-1, centerZ+1);
		float heightRU = getHeight(centerX+1, centerZ+1);
		float heightLL = getHeight(centerX-2, centerZ);
		float heightRR = getHeight(centerX+2, centerZ);
		float heightDD = getHeight(centerX, centerZ-2);
		float heightUU = getHeight(centerX, centerZ+2);
		
		return (height + heightL + heightR + heightD + heightU + heightLD + heightRD + heightLU + heightRU + heightRR+ heightLL+ heightDD+ heightUU)/9;
		
//		
//		ArrayList<Vector2f> points = new ArrayList<>();
//		
//		int radius = 2; 
//
//		for (int x=centerX-radius ; x<=centerX+radius ; x++){
//		    for (int y=centerZ-radius ; y<=centerZ+radius; y++) {
//		        if ((x-centerX)*(x-centerX) + (y-centerZ) * (y-centerZ) <= radius*radius) {
//		            points.add(new Vector2f(x, y));
//		        }
//		    }
//		}
//		
//		System.out.println(points);
//		
//		float height = 0;
//		
//		for(Vector2f vec: points) {
//			height += getHeight((int)vec.x, (int)vec.y);
//		}
//		
//		return height / points.size();
	}

}

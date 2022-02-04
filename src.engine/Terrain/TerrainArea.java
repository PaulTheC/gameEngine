package Terrain;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Random;
import java.util.prefs.BackingStoreException;

import javax.imageio.ImageIO;
import javax.swing.text.StyledEditorKit.BoldAction;

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
	public final int MAX_HEIGHT = 100;
	private final int MAX_PIXEL_COLOUR = 256 * 256 * 256;
	private BufferedImage heightMap;
	private float[][] heights; 
	private float x = 0;
	private float z = 0;
	public float gridSquareSize;
	private String heightMapFile;
	
	
	public TerrainArea(String heightMapFile){		
		super(null, new TerrainShader());
		this.heightMapFile = heightMapFile;
		super.setModel(new TexturedModel(generateTerrain(heightMapFile), "grass"));
	}
	
	public TerrainArea(String heightMapFile, StaticShader shader){		
		super(null, shader);
		this.heightMapFile = heightMapFile;
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
		float[] colors = calculateColors(normals);

		return new RawModel(Loader.loadToVAO(vertices, new float[] {}, indices, normals, colors), indices.length);
	}

	private float[] calculateVertices() {
		
		//Random r = new Random();
		
		int VERTEX_COUNT = heightMap.getHeight();
		
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		
		gridSquareSize = SIZE / ((float)heights.length - 1);
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		int vertexPointer = 0;
		for(int z=0;z<VERTEX_COUNT;z++){
			for(int x=0;x<VERTEX_COUNT;x++){
				vertices[vertexPointer*3] = ((float)x/((float)VERTEX_COUNT - 1) * SIZE);
				heights[x][z] = getHeight(x, z);
				vertices[vertexPointer*3+1] = getHeight(x, z);
				vertices[vertexPointer*3+2] = ((float)z/((float)VERTEX_COUNT - 1) * SIZE);
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
	
	public float[] calculateVertices(float[][] heightMap) {
		
		//Random r = new Random();
		
		int VERTEX_COUNT = this.heightMap.getHeight();
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		int vertexPointer = 0;
		for(int z=0;z<VERTEX_COUNT;z++){
			for(int x=0;x<VERTEX_COUNT;x++){
				vertices[vertexPointer*3] = ((float)x/((float)VERTEX_COUNT - 1) * SIZE);
				vertices[vertexPointer*3+1] = heightMap[x][z];
				vertices[vertexPointer*3+2] = ((float)z/((float)VERTEX_COUNT - 1) * SIZE);
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
	
	public float[] calculateColors(float[] normals) {
		float[] colors = new float[normals.length];
		for(int i = 0; i < normals.length;) {
			colors[i] = 0;
			colors[i+1] = 1f;
			colors[i+2] = 0;
			
			Vector3f normal = new Vector3f(normals[i], normals[i+1], normals[i+2]);
			float dot = Vector3f.dot(normal,(Vector3f) new Vector3f(0,1,0).normalise());
			
			if(dot < 0.7f) {
				colors[i] = 0.6f;
				colors[i+1] = 0.6f;
				colors[i+2] = 0.6f;
			}
			
			i+=3;
		}
		
		float[] newColors = new float[colors.length];
		
		int pointer = 0;
		int lenght = (int)Math.floor(Math.sqrt(colors.length / 6)) * 2;
		for(int i=0;i<colors.length;i+=3){
			
			int change = i / 3;
			int right = change + 1;
			int left = change - 1;
			int up = change + lenght;
			int down = change - lenght;
			
			float floatChange = colors[change * 3];
			float floatRight, floatLeft, floatUp, floatDown;
			
			try {
				floatRight = colors[right * 3];
			}catch(IndexOutOfBoundsException e) {
				floatRight = 1;
			}
			try {
				floatLeft = colors[left * 3];
			}catch(IndexOutOfBoundsException e) {
				floatLeft = 1;
			}
			try {
				floatUp = colors[up * 3];
			}catch(IndexOutOfBoundsException e) {
				floatUp = 1;
			}
			try {
				floatDown = colors[down * 3];
			}catch(IndexOutOfBoundsException e) {
				floatDown = 1;
			}
			
			if(floatRight == 1 || floatLeft == 1 || floatUp == 1 || floatDown == 1) {
				newColors[pointer] = 0;
				newColors[pointer+1] = 1;
				newColors[pointer+2] =0;
				
			}else if(floatRight != 0 || floatLeft != 0 || floatUp != 0 || floatDown != 0) {
				newColors[pointer] = 0.6f;
				newColors[pointer+1] = 0.6f;
				newColors[pointer+2] = 0.6f;
			}else {
				newColors[pointer] = 0;
				newColors[pointer+1] = 1;
				newColors[pointer+2] =0;
			}

//			
//			System.out.println(pointer+ "    "+ floatChange + "    "+ floatDown + "     "+ floatUp + "     " + floatLeft + "     "+ floatRight);
//			
			
			pointer += 3;
		}
		
		return newColors;
	}
	
	public float[] calculateNormals(float[] vertices) {
		
		float[] newNormals = new float[vertices.length];

		Random r = new Random();
		
		int VERTEX_COUNT = heightMap.getHeight();
		
		int pointer = 0;
		for(int x=0;x<VERTEX_COUNT- 1;x++){
			for(int z=0;z<VERTEX_COUNT- 1;z++){
				int topLeft = ((x)*VERTEX_COUNT * 2)+2 * z;
				int topLeft2 = topLeft + 1;
				int topRight = topLeft + 2;
				int bottomLeft = ((x+1)*VERTEX_COUNT*2)+2*z;
				int bottomRight = bottomLeft + 2;
				
				float offset = r.nextFloat(0.1f) - 0.05f;
				
				Vector3f p1 = new Vector3f(vertices[topLeft * 3], vertices[topLeft * 3+1], vertices[topLeft * 3+2]);
				Vector3f p2 = new Vector3f(vertices[bottomLeft * 3], vertices[bottomLeft * 3+1], vertices[bottomLeft * 3+2]);
				Vector3f p3 = new Vector3f(vertices[bottomRight * 3], vertices[bottomRight * 3+1], vertices[bottomRight * 3+2]);
				
				Vector3f res = calculateNormal(p1, p2, p3);
				newNormals[pointer] = res.x + offset;
				newNormals[pointer+1] = res.y + offset;
				newNormals[pointer+2] = res.z + offset;
				
				offset = r.nextFloat(0.1f) - 0.05f;
				
				p1 = new Vector3f(vertices[topLeft2 * 3], vertices[topLeft2 * 3+1], vertices[topLeft2 * 3+2]);
				p2 = new Vector3f(vertices[bottomRight * 3], vertices[bottomRight * 3+1], vertices[bottomRight * 3+2]);
				p3 = new Vector3f(vertices[topRight * 3], vertices[topRight * 3+1], vertices[topRight * 3+2]);
				
				res = calculateNormal(p1, p2, p3);
				newNormals[pointer+3] = res.x + offset;
				newNormals[pointer+4] = res.y + offset;
				newNormals[pointer+5] = res.z + offset;
				
				pointer += 6;
			}
			pointer += 6;
		}
		
		
		return newNormals;
		
	}
	
	private Vector3f calculateNormal(Vector3f p1, Vector3f p2, Vector3f p3) {
		Vector3f a = new Vector3f();
		Vector3f.sub(p1, p2, a);
		
		Vector3f b = new Vector3f();
		Vector3f.sub(p1, p3, b);
		
		float nx = a.y * b.z - a.z * b.y;
		float ny = a.z * b.x - a.x * b.z;
		float nz = a.x * b.y - a.y * b.x;
		
		Vector3f res = (Vector3f) new Vector3f(nx, ny, nz).normalise();
		
		return (Vector3f) new Vector3f(nx, ny, nz).normalise();
	}
	
	private float getHeight(int x, int z) {
		
		
		if(x < 0 || x >= heightMap.getHeight() ||z < 0 || z >= heightMap.getHeight()) {
			return 0;
		}

	
		
		float height =  heightMap.getRGB(x, z);
		height += MAX_PIXEL_COLOUR / 2f;
		height /= MAX_PIXEL_COLOUR / 2f;
		height *= MAX_HEIGHT;
		return height;
		
	}
	
	public float getArrayHeight(int x, int y) {
		return heights[x][y];
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
	
	public Vector2f getAreaGridPosition() {
		
		return new Vector2f((int)(x / SIZE),(int)( z / SIZE));
		
	}
	
	public float getSmoothHeight(int centerX, int centerZ) {
		
		if(centerX < 0 || centerX >= heightMap.getHeight() ||centerZ < 0 || centerZ >= heightMap.getHeight()) {
			return 0;
		}
		
//		if(heights[centerX][centerZ] != 0)
//			return heights[centerX][centerZ];
		
		float height = heights[centerX][centerZ];
		float heightL = heights[centerX-1][centerZ];
		float heightR = heights[centerX+1][centerZ];
		float heightD = heights[centerX][centerZ-1];
		float heightU = heights[centerX][centerZ+1];
		float heightLD = heights[centerX-1][centerZ-1];
		float heightRD = heights[centerX+1][centerZ-1];
		float heightLU = heights[centerX-1][centerZ+1];
		float heightRU = heights[centerX+1][centerZ+1];
		float heightLL = heights[centerX-2][centerZ];
		float heightRR = heights[centerX+2][ centerZ];
		float heightDD = heights[centerX][centerZ-2];
		float heightUU = heights[centerX][centerZ+2];
		
		return ((height+heightL + heightR + heightD + heightU + heightLD + heightRD + heightLU + heightRU + heightRR+ heightLL+ heightDD+ heightUU)/13  - height) / 10;

	}
	
	public Vector2f getNearestVertex(float worldX, float worldZ) {
		
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		
		if(gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
			return new Vector2f(0,0);
		}

		
		
		float distance1 = (float) Math.sqrt((terrainX - gridX * gridSquareSize) * (terrainX - gridX * gridSquareSize) + (terrainZ - gridZ * gridSquareSize) * (terrainZ - gridZ * gridSquareSize));
		float distance2 = (float) Math.sqrt((terrainX - (gridX + 1) * gridSquareSize) * (terrainX - (gridX + 1) * gridSquareSize) + (terrainZ - gridZ * gridSquareSize) * (terrainZ - gridZ * gridSquareSize));
		float distance3 = (float) Math.sqrt((terrainX - gridX * gridSquareSize) * (terrainX - gridX * gridSquareSize) + (terrainZ - (gridZ + 1) * gridSquareSize) * (terrainZ - (gridZ + 1) * gridSquareSize));
		float distance4 = (float) Math.sqrt((terrainX - (gridX + 1) * gridSquareSize) * (terrainX - (gridX + 1) * gridSquareSize) + (terrainZ - (gridZ + 1) * gridSquareSize) * (terrainZ - (gridZ + 1) * gridSquareSize));

		Vector2f res;
		if(distance1 < distance2 && distance1 < distance3 && distance1 < distance4)
			res = new Vector2f(gridX * gridSquareSize, gridZ * gridSquareSize);
		else if(distance2 < distance1 && distance2 < distance3 && distance2 < distance4)
			res = new Vector2f((gridX + 1) * gridSquareSize, gridZ * gridSquareSize);
		else if(distance3 < distance1 && distance3 < distance2 && distance3 < distance4)
			res = new Vector2f(gridX * gridSquareSize, (gridZ + 1) * gridSquareSize);
		else if(distance4 < distance1 && distance4 < distance2 && distance4 < distance3)
			res = new Vector2f((gridX + 1) * gridSquareSize, (gridZ + 1) * gridSquareSize);
		else {
			res = new Vector2f();
		}
		
		return res;
				
	}
	
	public void setHeight(int worldX, int worldZ, float value, boolean inverted) {
		
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		
		if(gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
			return;
		}
		
		if(!inverted)
			heights[gridX][gridZ] += value;
		else
			heights[gridX][gridZ] -= value;
	}

	public float[][] getHeightMap() {
		return heights;
		
	}

	public String getHeightMapFile() {
		return heightMapFile;
	}

	
}

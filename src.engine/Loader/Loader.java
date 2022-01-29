package Loader;

import java.awt.List;
import java.awt.geom.FlatteningPathIterator;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Engine.DisplayManager;
import Models.RawModel;
import Models.TexturedModel;

public class Loader {
	
	private static ArrayList<Integer> vaos = new ArrayList<Integer>();
	private static ArrayList<Integer> vbos = new ArrayList<Integer>();
	private static ArrayList<Integer> textures = new ArrayList<Integer>();
	
	public static int loadToVAO(float[] positions,float[] textureCoords ,int[] indices, float[] normals){
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0,3,positions);
		storeDataInAttributeList(1,2,textureCoords);
		storeDataInAttributeList(2,3,normals);
		
		
		unbindVAO();
		return vaoID;
	}
	
	
	public static int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Tried to load texture " + fileName + ".png , didn't work");
			System.exit(-1);
		}
		textures.add(texture.getTextureID());
		int n = texture.getImageHeight();
		
		//checking if n is a power of 2 
		if((int)(Math.ceil((Math.log(n) / Math.log(2)))) != (int)(Math.floor(((Math.log(n) / Math.log(2))))))
			System.out.println("The size of the image "+ fileName+" is not a power of 2. Black line might occur!");
		return texture.getTextureID();
	}
	
	public void cleanUp(){
		for(int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures){
			GL11.glDeleteTextures(texture);
		}
	}
	
	private static int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private static void storeDataInAttributeList(int attributeNumber, int coordinateSize,float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber,coordinateSize,GL11.GL_FLOAT,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private static void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	private static void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private static IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public static RawModel loadFromOBJ(String filemane) throws IOException{
		
		FileReader fr = new FileReader("res/"+filemane+".obj");

		BufferedReader bReader = new BufferedReader(fr);
		String line;
		ArrayList<Vector3f> vertices = new ArrayList<>();
		ArrayList<Vector3f> normals = new ArrayList<>();
		ArrayList<Vector2f> textureCoords = new ArrayList<>();
		ArrayList<Integer> indices = new ArrayList<>();
		ArrayList<Vector3f> vertices2 = new ArrayList<>();
		ArrayList<Vector3f> normals2 = new ArrayList<>();
		ArrayList<Vector2f> textureCoords2 = new ArrayList<>();
		ArrayList<Integer> indices2 = new ArrayList<>();
		float[]  verticesArray = null;
		float[]  normalsArray = null;
		float[]  textureArray = null;
		int[]  indicesArray = null;

		boolean isCol = true;
		
		/*while(true) {
			line = bReader.readLine();
			if(line == null)break;
			String[] currentLine = null;
			try {
				currentLine = line.split(" ");
			}catch(NullPointerException e){}
			if(line.startsWith("v ")) { 
				 Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
				 vertices.add(vertex);
				 if(currentLine.length == 7) {
					 Vector3f color = new Vector3f(Float.parseFloat(currentLine[4]),Float.parseFloat(currentLine[5]),Float.parseFloat(currentLine[6]));
					 colors.add(color);
				 }else {
					 Vector3f color = new Vector3f(1.0f, 1.0f, 1.0f);
					 colors.add(color);
					 isCol = false;
				 }
			}else if(line.startsWith("vn ")) {
				 Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
				 normals.add(normal);
			}else if(line.startsWith("vt ")) {
				 Vector2f textureCoord = new Vector2f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]));
				 textureCoords.add(textureCoord);
			}else if(line.startsWith("f ")) {
		         indices.add(Integer.parseInt(currentLine[1].split("/")[0])-1);
		         indices.add(Integer.parseInt(currentLine[2].split("/")[0])-1);
		         indices.add(Integer.parseInt(currentLine[3].split("/")[0])-1);
		         indices.add(Integer.parseInt(currentLine[1].split("/")[1])-1);
		         indices.add(Integer.parseInt(currentLine[2].split("/")[1])-1);
		         indices.add(Integer.parseInt(currentLine[3].split("/")[1])-1);
		         indices.add(Integer.parseInt(currentLine[1].split("/")[2])-1);
		         indices.add(Integer.parseInt(currentLine[2].split("/")[2])-1);
		         indices.add(Integer.parseInt(currentLine[3].split("/")[2])-1);
			}else if(line.startsWith("# End of File")){
				
				break;
			}
		}*/
		int h = 0;
		while(true) {
			line = bReader.readLine();
			String[] currentLine = line.split(" ");
			if(line.startsWith("v  ")) {
				Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]), Float.parseFloat(currentLine[4]));
				vertices.add(vertex);
			}else if(line.startsWith("v ")) {
				Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
				vertices.add(vertex);
			}else if(line.startsWith("vt ")) {
				Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), 1 - Float.parseFloat(currentLine[2]));
				textureCoords.add(texture);
			}else if(line.startsWith("vn ")) {
				Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
				normals.add(normal);
			}else if(line.startsWith("f ")) {
				break;
			}
		}
		int j= 0;
		while(line!=null) {
			if(!line.startsWith("f ")) {
				line = bReader.readLine();
				continue;
			}
			String[] currentLine = line.split(" ");
			String[] vertex1 = currentLine[1].split("/");
			String[] vertex2 = currentLine[2].split("/");
			String[] vertex3 = currentLine[3].split("/");
			

			vertices2.add(vertices.get(Integer.parseInt(vertex1[0])-1));
			vertices2.add(vertices.get(Integer.parseInt(vertex2[0])-1));
			vertices2.add(vertices.get(Integer.parseInt(vertex3[0])-1));
			
			textureCoords2.add(textureCoords.get(Integer.parseInt(vertex1[1])-1));
			textureCoords2.add(textureCoords.get(Integer.parseInt(vertex2[1])-1));
			textureCoords2.add(textureCoords.get(Integer.parseInt(vertex3[1])-1));
			
			normals2.add(normals.get(Integer.parseInt(vertex1[2])-1));
			normals2.add(normals.get(Integer.parseInt(vertex2[2])-1));
			normals2.add(normals.get(Integer.parseInt(vertex3[2])-1));
			
			indices2.add(j++);
			indices2.add(j++);
			indices2.add(j++);
			
			
			line = bReader.readLine();
		}

		
		
		
		verticesArray = new float[vertices2.size()*3];
		textureArray = new float[vertices2.size()*2];
		normalsArray = new float[vertices2.size()*3];
		indicesArray = new int[indices2.size()];
		
		int vertexPointer = 0;
		for(Vector3f vertex: vertices2){
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		vertexPointer = 0;
		for(Vector2f color: textureCoords2){
			textureArray[vertexPointer++] = color.x;
			textureArray[vertexPointer++] = color.y;
		}
		vertexPointer = 0;
		for(Vector3f color: normals2){
			normalsArray[vertexPointer++] = color.x;
			normalsArray[vertexPointer++] = color.y;
			normalsArray[vertexPointer++] = color.z;
		}
		
		for (int i = 0; i < indices2.size(); i++) {
			indicesArray[i] = indices2.get(i);
		}
		
		
		System.out.println("Successfully loaded "+filemane+".obj");
		return new RawModel(loadToVAO(verticesArray, textureArray, indicesArray, normalsArray), indicesArray.length);
	
		
	}
	
	public TexturedModel loadUIElement(String filename, int size_inHorizontalPercent, int x, int y) {
		   File f = new File("res/"+filename+".png");
		   BufferedImage image = null;
			try {
				image = ImageIO.read(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   int height = image.getHeight();
		   int width = image.getWidth();
		   System.out.print(height/(float)DisplayManager.HEIGHT);
		   
		   float[] vertices = {				
				    0,height/(float)DisplayManager.HEIGHT,0,	
					0,0,0,
					width/(float)DisplayManager.WIDTH,0,0,
					width/(float)DisplayManager.WIDTH,height/(float)DisplayManager.HEIGHT,0,
		   };
		   
			float[] textureCoords = {
					
					0,0,
					0,1,
					1,1,
					1,0,	};
			
			int[] indices = {
					0,1,3,	
					3,1,2,};
			
			
		 float[] normals = {
					0.0000f, 1.0000f, 0.0000f,
					0.0000f, 0.0000f, 1.0000f,
					-1.0000f, 0.0000f, 0.0000f,
					0.0000f, -1.0000f, 0.0000f,
					1.0000f, 0.0000f, 0.0000f,
					0.0000f, 0.0000f, -1.0000f,
			};
			
			return new TexturedModel(new RawModel(loadToVAO(vertices, textureCoords, indices, normals), indices.length), filename);

	}
	

}
 
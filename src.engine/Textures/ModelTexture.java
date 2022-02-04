package Textures;

public class ModelTexture {
	
	private int textureID;
	private String fileName;
	private int width;
	private int height;
	
	public ModelTexture(int id, int height, int width, String fileName) {
		this.textureID = id;
		this.width = width;
		this.height = height;
		this.fileName = fileName;
	}

	public int getTextureID() {
		return textureID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public String getFileName() {
		return fileName;
	}
	
}

package Models;

public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	private String filename;
	
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String fileName) {
		this.filename = fileName;
	}
	
	
	
	

}

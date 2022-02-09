package Particles;

public class ParticleTexture {

	private int textureId;
	private int numberOfRows;

	public ParticleTexture(int textureId, int numberOfRows) {
		super();
		this.textureId = textureId;
		this.numberOfRows = numberOfRows;
	}

	public ParticleTexture(int textureId) {
		super();
		this.textureId = textureId;
		this.numberOfRows = 1;
	}

	public int getTextureId() {
		return textureId;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

}

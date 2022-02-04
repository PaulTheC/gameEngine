package Models;

import Loader.Loader;
import Textures.ModelTexture;

public class TexturedModel {
	
	private RawModel rawModel;
	private ModelTexture texture;
	private boolean hasTexture = false;
	
	public TexturedModel(RawModel model, String texureName){
		this.rawModel = model;
		this.texture = Loader.loadTexture(texureName);
		hasTexture();
	}
	
	public TexturedModel(RawModel model){
		this.rawModel = model;
	}
	
	public void hasTexture() {
		hasTexture = true;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}

	public boolean getHasTexture() {
		return hasTexture;
	}

	public void setTexture(String texureName) {
		this.texture = new ModelTexture(Loader.loadTexture(texureName));
		hasTexture();
	}
	
	

}

package UIElements;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Engine.Renderer;
import Entiys.Entity;
import Loader.Loader;
import MainShader.StaticShader;
import Materials.DefaultMaterial;
import Models.TexturedModel;

public class UIElement extends Entity{

	public UIElement(String fileName, StaticShader programm) {
		super(Loader.loadUIElement(fileName, 1,1,1), programm);
		// TODO Auto-generated constructor stub
	}
	
	public int getHeight() {
		return super.getModel().getTexture().getHeight();
	}

	
	public int getWidth() {
		return super.getModel().getTexture().getWidth();
	}
	
	@Override
	public void setPosition(Vector3f position) {
		super.setPosition(new Vector3f(position.x / Display.getWidth(), position.y / Display.getHeight(), 1));
	}
	
	@Override
	public void increasePosition(float x, float y, float z) {
		super.increasePosition(x / Display.getWidth(), y / Display.getHeight(), 1);
	}

	public Vector3f getPivot() {
		return pivot;
	}

	public void setPivot(Vector3f pivot) {
		this.pivot = pivot;
	}
	
	
	
}

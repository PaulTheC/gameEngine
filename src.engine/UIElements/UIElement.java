package UIElements;

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

}

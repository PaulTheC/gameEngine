package Entiys;

import Engine.Renderer;
import Loader.Loader;
import MainShader.StaticShader;
import Materials.DefaultMaterial;
import Models.TexturedModel;

public class UIElement extends Entity{

	public UIElement(String fileName, Loader loader, StaticShader programm) {
		super(loader.loadUIElement(fileName, 1,1,1), programm);
		// TODO Auto-generated constructor stub
	}

}

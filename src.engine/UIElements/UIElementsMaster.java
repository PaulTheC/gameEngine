package UIElements;

import java.util.ArrayList;

import Entiys.UIElement;
import Lights.Light;

public class UIElementsMaster {
	
	private static ArrayList<UIElement> allUiElements = new ArrayList<>();

	public static ArrayList<UIElement> getAllUiElements() {
		return allUiElements;
	}
	
	public static void addUiElement(UIElement uielement) {
		allUiElements.add(uielement);
	}
	
	public static void deleteUiElement(UIElement uielement) {
		boolean success = allUiElements.remove(uielement);
		if(!success)System.out.println("You tried to remove a not existing Light from the List");
	}
	
	public static void renderAllUIElements() {
		for(UIElement uiElement: allUiElements) {
			uiElement.getShader().start();
			uiElement.getShader().render(uiElement);
			uiElement.getShader().stop();
		}
	}

}

package Lights;

import java.util.ArrayList;

public class LightMaster {
	
	private static ArrayList<Light> allLights = new ArrayList<>();

	public static ArrayList<Light> getAllLights() {
		return allLights;
	}
	
	public static void addLight(Light light) {
		allLights.add(light);
	}
	
	public static void deleteLight(Light light) {
		boolean success = allLights.remove(light);
		if(!success)System.out.println("You tried to remove a not existing Light from the List");
	}
	
	public ArrayList<Light> getLights(){
		return allLights;
	}
	
	public static Light[] getLightArray() {
		Light[] lightArray = new Light[allLights.size()+5];
		for(int i = 0; i < allLights.size(); i++) {
			lightArray[i] = allLights.get(i);
		}
		lightArray[lightArray.length-1] = new Light(0);
		lightArray[lightArray.length-2] = new Light(0);
		lightArray[lightArray.length-3] = new Light(0);
		lightArray[lightArray.length-4] = new Light(0);
		lightArray[lightArray.length-5] = new Light(0);
		return lightArray;
	}

}

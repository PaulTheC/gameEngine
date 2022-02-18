package MainShader;

import java.util.ArrayList;
import java.util.List;

public class ShaderMaster {
	
	private static ArrayList<StaticShader> list = new ArrayList<>();
	
	public static void addShader(StaticShader shader) {
		list.add(shader);
	}
	
	public static ArrayList<StaticShader> getList() {
		return list;
	}

	public static void removeShader(StaticShader shader) {
		list.remove(shader);
	}
	
}

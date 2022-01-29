package Lights;

import org.lwjgl.util.vector.Vector3f;

public class Sun extends Light{

	public Sun() {
		super(new Vector3f(1000, 0,1000), new Vector3f(1,1,1));
		super.setAttenuation(new Vector3f(1,0,0));
		super.setStrength(5);
	}

}

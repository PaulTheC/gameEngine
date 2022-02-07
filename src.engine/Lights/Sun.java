package Lights;

import org.lwjgl.util.vector.Vector3f;

import Entiys.EnlightedEntity;
import Movements.SunMovement;

public class Sun extends EnlightedEntity{


	public Sun() {
		super();
		super.setPosition(new Vector3f(0, 10000,10000));
		super.setAttenuation(new Vector3f(1,0,0));
		super.setBrightness(1);
		super.setStrength(1);
		
	}
	
}

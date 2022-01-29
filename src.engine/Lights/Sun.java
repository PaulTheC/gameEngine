package Lights;

import org.lwjgl.util.vector.Vector3f;

import Entiys.EnlightedEntity;
import Movements.SunMovement;

public class Sun extends EnlightedEntity{


	public Sun() {
		super();
		super.setPosition(new Vector3f(100, 0,1000));
		super.setAttenuation(new Vector3f(1,0,0));
		super.setBrightness(2);
		super.setStrength(5);
		
		super.addComponent(new SunMovement());
	}
	
}

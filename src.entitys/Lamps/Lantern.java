package Lamps;

import org.lwjgl.util.vector.Vector3f;

import Entiys.EnlightedEntity;

public class Lantern extends EnlightedEntity{
	
	public Lantern(){
		super("entitys/lamps/lantern/lantern", "entitys/lamps/lantern/lantern");
		super.setScale(1);
		super.setOffset(new Vector3f(0,0,0));
		super.setStrength(5);
	}

}

package Lamps;

import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;

import Entiys.Lamp;

public class Lantern extends Lamp{
	
	public Lantern() throws IOException {
		super("entitys/lamps/lantern/lantern", "entitys/lamps/lantern/lantern");
		super.setScale(1);
		//super.setOffset(new Vector3f(0,10,0));
		super.setStrength(5);

	}

}

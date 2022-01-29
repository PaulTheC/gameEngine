/**
 * 
 */
package Movements;

import org.lwjgl.util.vector.Vector3f;

import Components.Component;
import Entiys.Entity;
import Utilities.Time;

/**
 * @author Admin
 *
 */
public class SunMovement extends Component {

	@Override
	public void onStart(Entity entity) {


	}

	@Override
	public void onUpdate(Entity entity) {
		System.out.println(entity.getPosition());
		entity.setPosition(new Vector3f(0, (float)Math.sin((double)Time.getTimeAlive() / 3) * 1000f, (float)Math.cos((double)Time.getTimeAlive() / 3) * 1000f));
	
	}


}

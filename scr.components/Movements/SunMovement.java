/**
 * 
 */
package Movements;

import org.lwjgl.util.vector.Vector3f;

import Camera.MainCamera;
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

		disable();

	}

	@Override
	public void onUpdate(Entity entity) {
//		Vector3f offset = MainCamera.getCamera().getPosition();
		Vector3f offset = new Vector3f(0,0,0);
		entity.setPosition(new Vector3f(offset.x, (float)Math.sin((double)Time.getTimeAlive() / 3) * 100000 + offset.y, (float)Math.cos((double)Time.getTimeAlive() / 3) * 100000 + offset.z));
	
	}


}

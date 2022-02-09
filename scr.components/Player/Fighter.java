package Player;

import org.lwjgl.util.vector.Vector3f;

import Colisions.Raycast;
import Components.Component;
import Entiys.Entity;
import ParticleEffects.LightningEffect;
import Particles.ParticleSystem;

enum SelectedWeapon {
	Lightning
}


public class Fighter extends Component{
	
	private SelectedWeapon weapon = SelectedWeapon.Lightning;
	private LightningEffect lightningEffect;
	
	public void fight() {
		
		Vector3f target = Raycast.terrainRaycast();
		
		switch(weapon) {
		
		case Lightning:
			lightningEffect = new LightningEffect(target);
			break;
			
		default:
			break;
		
		}
	}
	
	
	

	@Override
	public void onStart(Entity entity) {
	}

	@Override
	public void onUpdate(Entity entity) {
	}

}

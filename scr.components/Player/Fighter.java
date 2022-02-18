package Player;

import org.lwjgl.util.vector.Vector3f;

import Colisions.Raycast;
import Components.Component;
import Entiys.Entity;
import ParticleEffects.LightningEffect;
import ParticleEffects.LightningV2;
import Particles.ParticleSystem;

enum SelectedWeapon {
	Lightning
}


public class Fighter extends Component{
	
	private SelectedWeapon weapon = SelectedWeapon.Lightning;
	private LightningV2 lightningEffect;
	
	public void fight() {
		
		Vector3f target = Raycast.terrainRaycast();
		
		switch(weapon) {
		
		case Lightning:
			if(target != null)
				lightningEffect = new LightningV2(target);
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

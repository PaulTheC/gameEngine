package Player;

import Components.Component;
import Entiys.Entity;

enum SelectedWeapon {
	Lightning
}


public class Fighter extends Component{
	
	private SelectedWeapon weapon = SelectedWeapon.Lightning;
	
	public void fight() {
		
		switch(weapon) {
		
		case Lightning:
			
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

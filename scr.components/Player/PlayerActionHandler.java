package Player;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Colisions.Raycast;
import Components.Component;
import Engine.Player;
import Entiys.Entity;
import Loader.Loader;
import Loader.Saver;
import Terraforming.SmoothenBrush;
import Terrain.Brush;
import Terrain.Terraformer;
import Terrain.TerrainArea;
import testingScenes.TestScene;

enum PlayerState{
	terraforming, 
	fighting
}

public class PlayerActionHandler extends Component{
	
	private PlayerState state = PlayerState.fighting;
	private boolean inverted = false;

	@Override
	public void onStart(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate(Entity entity) {
		
	}
	
	@Override
	public void leftMouseButtonClicked() {
		
		switch (state) {
		
		case terraforming:
			inverted = false;
			Terraformer.terraform(inverted);
			break;
			
		case fighting:
			Player.getCamera().<Fighter>getComponent(Fighter.class).fight();
			break;
			
		default:
			break;
		
		}
		
	}
	
	
	@Override
	public void rightMouseButtonClicked() {
	

		
		switch (state) {
		
		case terraforming:
			inverted = true;
			Terraformer.terraform(inverted);
			break;
			
		case fighting:
			break;
			
		default:
			break;
		
		}
		
	}

}

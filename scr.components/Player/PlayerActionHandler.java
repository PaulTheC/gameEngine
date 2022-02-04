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

public class PlayerActionHandler extends Component{
	
	private String mode = "terraforming";
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
		
		switch (mode) {
		
		case "terraforming":
			inverted = false;
			Terraformer.terraform(inverted);
		
		}
		
	}
	
	
	@Override
	public void rightMouseButtonClicked() {
	

		
		switch (mode) {
		
		case "terraforming":
			inverted = true;
			Terraformer.terraform(inverted);
		
		}
		
	}

}

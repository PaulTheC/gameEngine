package Player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import Components.Component;
import Engine.DisplayManager;
import Engine.Player;
import Entiys.Entity;
import Utilities.MouseHandler;
import Utilities.Time;

public class PlayerMovement extends Component{
	
	private Camera cam;

	@Override
	public void onStart(Entity entity) {
		cam = Player.getCamera();
		
	}

	@Override
	public void onUpdate(Entity entity) {
		move();
		
	}
	
	
	
	private void move() {
		
		float yaw = cam.getYaw(), pitch = cam.getPitch(), roll = cam.getRoll();
		yaw += MouseHandler.getLastXMovement() * 0.04f;
		pitch = Math.min(Math.max(cam.getPitch() + MouseHandler.getLastYMovement() * -0.04f, -89), 89);
		
		
		Vector3f position = cam.getPosition();
		
		float currentSpeed = 0;
		float sideSpeed = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			currentSpeed = -cam.SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			currentSpeed = cam.SPEED;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			sideSpeed =cam. SPEED / 2;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			sideSpeed = -cam.SPEED / 2;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			position.y -= cam.SPEED * Time.deltaTime;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			position.y += cam.SPEED * Time.deltaTime;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
			DisplayManager.requestClose();
		}
		
		
		
	
		
		float distance = currentSpeed * Time.deltaTime;
		float dx = (float) (distance * Math.sin(Math.toRadians(-cam.getYaw())));
		float dz = (float) (distance * Math.cos(Math.toRadians(cam.getYaw())));
		position.x += dx;
		position.z += dz;
		
		
		distance = sideSpeed * Time.deltaTime;
		dx = (float) (distance * Math.sin(Math.toRadians(cam.getYaw())));
		dz = (float) (distance * Math.cos(Math.toRadians(-cam.getYaw())));
		position.x += dz;
		position.z += dx;
		
		
		//Colision detection
		
		//position.y = SceneManager.<TestScene>getActiveScene().map.getHeightOfTerrain(position.x, position.z)+2;
		//position = TestScene.sun.getPosition();
		
		cam.setRoll(roll);
		cam.setPitch(pitch);
		cam.setYaw(yaw);
		cam.setPosition(position);
	}
	
	

}

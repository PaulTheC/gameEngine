package Entiys;

import java.io.IOException;

import javax.naming.ldap.HasControls;

import org.lwjgl.util.vector.Vector3f;

import Engine.Main;
import EntityShader.EnitiyShader;
import Loader.Loader;
import MainShader.StaticShader;
import Materials.DefaultMaterial;
import Materials.StaticMaterial;
import Models.RawModel;
import Models.TexturedModel;

public class Entity {
	
	private TexturedModel model;
	private Vector3f position = new Vector3f(0,0,0);
	private float rotX = 0, rotY = 0, rotZ = 0;
	private float scale = 1;
	private StaticShader programm;
	private StaticMaterial material;

	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale, StaticShader programm) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.programm = programm;
		material = Main.material;
		EntityMaster.addEntity(this);
	}
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale, StaticShader programm, StaticMaterial material) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.programm = programm;
		this.material = material;
		EntityMaster.addEntity(this);
	}
	
	public Entity(TexturedModel model, StaticShader programm) {
		this.model = model;
		this.position = new Vector3f(0, 0,-0.2f);
		this.rotX = 0;
		this.rotY = 0;
		this.rotZ = 0;
		this.scale = 1;
		this.programm = programm;
		this.material = Main.material;
		EntityMaster.addEntity(this);
	}
	
	public Entity(TexturedModel model, StaticShader programm, StaticMaterial material) {
		this.model = model;
		this.position = new Vector3f(0, 0,-0.2f);;
		this.rotX = 0;
		this.rotY = 0;
		this.rotZ = 0;
		this.scale = 1;
		this.programm = programm;
		this.material = material;
		EntityMaster.addEntity(this);
	}
	
	public Entity(String objPath, String texturePath) throws IOException {
		this.model = new TexturedModel(Loader.loadFromOBJ(objPath), texturePath);
		this.programm = Main.shader;
		this.material = Main.material;
		EntityMaster.addEntity(this);
	}
	
	public Entity(String objPath) throws IOException {
		this.model = new TexturedModel(Loader.loadFromOBJ(objPath));
		this.programm = Main.shader;
		this.material = Main.material;
		EntityMaster.addEntity(this);
	}

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	
	public StaticShader getShader() {
		return programm;
	}

	public StaticMaterial getMaterial() {
		return material;
	}

	public void setShader(StaticShader programm) {
		this.programm = programm;
	}

	public void setMaterial(DefaultMaterial material) {
		this.material = material;
	}
	
	

}
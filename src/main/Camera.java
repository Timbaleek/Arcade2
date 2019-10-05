package main;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class Camera {
	
	public static Camera single;
	
	Vector2f pos;
	float zoom;
	int halfScreenWidth = Main.screenWidth/2;
	int halfScreenHeight = Main.screenHeight/2;
	
	public Camera(Vector2f pos, float zoom){
		this.pos = pos;
		this.zoom = zoom;
		Camera.single = this;
	}
	
	public float getLeftEdge(){
		return pos.x;
	}
	public float getTopEdge(){
		return pos.y;
	}
	public float getRightEdge(){
		return pos.x + Main.screenWidth;
	}
	public float getBottomEdge(){
		return pos.y + Main.screenHeight;
	}
	
	public Vector2f getAbsoluteMouse(){
		return new Vector2f((Mouse.getX() + Camera.single.pos.x)/zoom,
				((Main.screenHeight-Mouse.getY()) + Camera.single.pos.y)/zoom);
	}
}

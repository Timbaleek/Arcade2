package graphics;

import org.lwjgl.util.vector.Vector2f;

public class Rect {
	public Vector2f pos;
	public Vector2f size;

	public Rect(Vector2f pos, Vector2f size) { //reduces loading if the same texture is used for multiple objects
		this.pos = pos;
		this.size = size;
	}
	
	public void move(Vector2f move){
		pos.x += move.x;
		pos.y += move.y;
	}
	
	public void move(float moveX, float moveY){
		pos.x += moveX;
		pos.y += moveY;
	}
}

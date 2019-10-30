package entities;

import org.lwjgl.util.vector.Vector2f;

import graphics.GraphicRect;

public class CollidingGameEntity {

	public Vector2f vel = new Vector2f(0,0);
	public Polygon poly;
	public GraphicRect rect;
	private final float maxSpeed = 3000;
	
	public CollidingGameEntity(Polygon poly, GraphicRect rect) {
		this.poly = poly;
		this.rect = rect;
	}

	public void update(){
		poly.move(vel);
		rect.move(vel);
		
		if(vel.x > 0.01 || vel.x < -0.01){
			vel.x /= 1.02f;
		}else{
			vel.x = 0;
		}
		
		if(vel.x >= maxSpeed) vel.x = maxSpeed; else if(vel.x <= -maxSpeed) vel.x = -maxSpeed;
		if(vel.y >= maxSpeed) vel.y = maxSpeed; else if(vel.y <= -maxSpeed) vel.y = -maxSpeed;
	}
}

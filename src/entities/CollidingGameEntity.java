package entities;

import org.lwjgl.util.vector.Vector2f;

import graphics.GraphicRect;

public class CollidingGameEntity {

	public Vector2f vel = new Vector2f(0,0);
	public Polygon poly;
	public GraphicRect rect;
	
	public CollidingGameEntity(Polygon poly, GraphicRect rect) {
		this.poly = poly;
		this.rect = rect;
	}

	public void update(){
		//System.out.println(vel);
		poly.move(vel);
		rect.move(vel);
		
		if(vel.x > 0.01 || vel.x < -0.01){
			vel.x /= 1.02f;
		}else{
			vel.x = 0;
		}
		if(vel.y > 0.01 || vel.y < -0.01){
			vel.y /= 1.02f;
		}else{
			vel.y = 0;
		}
	}
}

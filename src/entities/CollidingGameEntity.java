package entities;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import graphics.GraphicRect;
import graphics.Rect;

public class CollidingGameEntity {
	//physics
	public Vector2f vel = new Vector2f(0,0);
	public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	public ArrayList<Rect> rects = new ArrayList<Rect>();
	public GraphicRect rect;
	private final float maxSpeed = 3000;
	//path
	public Vector2f[] path = {};
	public float pathSpeed = 2;
	int pathIndex = 0;
	
	public CollidingGameEntity(GraphicRect rect) {
		this.rect = rect;
	}
	
	public CollidingGameEntity(GraphicRect rect, ArrayList<Polygon> polygons) {
		this.rect = rect;
		this.polygons = polygons;
	}
	
	public void addPolygon(Polygon poly){
		polygons.add(poly);
	}

	public void update(){
		move(vel);
		
		if(vel.x > 0.01 || vel.x < -0.01){
			vel.x /= 1.02f;
		}else{
			vel.x = 0;
		}
		
		if(vel.x >= maxSpeed) vel.x = maxSpeed; else if(vel.x <= -maxSpeed) vel.x = -maxSpeed;
		if(vel.y >= maxSpeed) vel.y = maxSpeed; else if(vel.y <= -maxSpeed) vel.y = -maxSpeed;
	}
	
	public void move(Vector2f move){
		rect.move(move);
		for(Polygon poly:polygons){
			poly.move(move);
		}
	}
	
	//path
	public void updatePath(){
		if(moveTowards(path[pathIndex], pathSpeed)){
			pathIndex = (pathIndex + 1) % path.length;
		}
	}

	public boolean moveTowards(Vector2f destination, float time){
		Vector2f rectMiddle = new Vector2f(rect.pos.x+(rect.size.x/2.0f),rect.pos.y+(rect.size.y/2.0f));
		if(rectMiddle.x<=destination.x+5&&rectMiddle.x>=destination.x-5&&rectMiddle.y<=destination.y+5&&rectMiddle.y>=destination.y-5){
			return true;
		}
		Vector2f movement = new Vector2f(destination.x-rectMiddle.x,
										 destination.y-rectMiddle.y);
		double dist = Math.sqrt((movement.x*movement.x) + (movement.y*movement.y));
		double totalVel = dist/time;
		vel.x = (float) (movement.x/totalVel);
		vel.y = (float) (movement.y/totalVel);
		return false;
	}



}

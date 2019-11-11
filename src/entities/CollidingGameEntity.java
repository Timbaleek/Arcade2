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
	public GraphicRect gRect;
	private final float maxSpeed = 3000;
	//path
	public Polygon path = null;
	public float pathSpeed = 2;
	int pathIndex = 0;
	
	public CollidingGameEntity(GraphicRect gRect) {
		this.gRect = gRect;
	}
	
	public void setPolygons(ArrayList<Polygon> polygons) {
		this.polygons = polygons;
	}

	public void setRects(ArrayList<Rect> rects) {
		this.rects = rects;
	}

	public void setPath(Polygon path) {
		this.path = path;
	}

	public void addPolygon(Polygon poly){
		polygons.add(poly);
	}
	
	public void addRects(Rect rect) {
		rects.add(rect);
	}
	
	Vector2f setVel = new Vector2f(0,0);
	public void setVel(Vector2f setVel){
		this.setVel = setVel;
		vel = setVel;
	}

	public void update(){
		if(setVel!=new Vector2f(0,0)) vel = setVel;

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
		gRect.move(move);
		for(Polygon poly:polygons){
			poly.move(move);
		}
		for(Rect rect:rects) {
			rect.move(move);
		}
	}
	
	//path
	public void updatePath(){
		if(moveTowards(path.shape.get(pathIndex), pathSpeed)){
			pathIndex = (pathIndex + 1) % path.shape.size();
		}
	}

	public boolean moveTowards(Vector2f destination, float time){
		Vector2f gRectMiddle = new Vector2f(gRect.pos.x+(gRect.size.x/2.0f),gRect.pos.y+(gRect.size.y/2.0f));
		if(gRectMiddle.x<=destination.x+5&&gRectMiddle.x>=destination.x-5&&gRectMiddle.y<=destination.y+5&&gRectMiddle.y>=destination.y-5){
			return true;
		}
		Vector2f movement = new Vector2f(destination.x-gRectMiddle.x,
										 destination.y-gRectMiddle.y);
		double dist = Math.sqrt((movement.x*movement.x) + (movement.y*movement.y));
		double totalVel = dist/time;
		vel.x = (float) (movement.x/totalVel);
		vel.y = (float) (movement.y/totalVel);
		return false;
	}

	public boolean moveTo(Vector2f destination){
		Vector2f movement = new Vector2f(destination.x-gRect.pos.x,destination.y-gRect.pos.y);
		move(movement);
		return false;
	}

}

package entities;

import org.lwjgl.util.vector.Vector2f;

import graphics.Rect;
import util.CollisionHandler;

public class Trigger extends Rect{
	
	//boolean inside, lastInside;
	private Rect otherRect;
	
	public Trigger(Vector2f pos, Vector2f size, Rect otherRect) {
		super(pos, size);
		this.otherRect = otherRect;
		// TODO Auto-generated constructor stub
	}
	
	public boolean update(){
		if(CollisionHandler.doRectCollision(otherRect, new Rect(super.pos,super.size)) != null){
			return true;
		} 
		return false;
	}
}


package entities;

import org.lwjgl.input.Keyboard;

import graphics.GraphicRect;

public class Player extends CollidingGameEntity{
	
	public Player(Polygon polygon,GraphicRect graphicRect) {
		super(polygon, graphicRect);
	}

	public void updateInput(){
			if ((Keyboard.isKeyDown(Keyboard.KEY_A))){
				vel.x -= 0.05;
				//move(new Vector2f(-1,0));
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_D))){
				vel.x += 0.05;
				//move(new Vector2f(1,0));
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_W))){
				vel.y -= 0.05;
				//move(new Vector2f(0,-1));
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_S))){
				vel.y += 0.05;
				//move(new Vector2f(0,1));
			}
	}
}

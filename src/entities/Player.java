package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import graphics.GraphicRect;
import main.Main;

public class Player extends CollidingGameEntity{
	
	public boolean grounded = true;
	public float jumpForce = 5f;

	public Player(Polygon polygon,GraphicRect graphicRect, Vector2f spawnpoint) {
		super(graphicRect);
		//player only one polygon
		vel = Main.nullVec;
		polygons.add(polygon);
		moveTo(spawnpoint);
	}

	boolean rolling;
	public void updateInput(){
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				vel.x -= 0.05;
			}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
				vel.x += 0.05;
				if(!rolling) {
					gRect.changeTex(0, 700, true);
				}rolling = true;
		} else {
			rolling = false;
		}
		
		
			if ((Keyboard.isKeyDown(Keyboard.KEY_W))){

				//System.out.println(grounded);
				if(grounded){
					//(long)(jumpForce/(Main.gravity))*2
					//Main.player.gRect.changeTex(1, 400, false); // change to before jumping texture
					vel.y = -jumpForce;
					grounded = false;
				}
				//vel.y -= 0.05;
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_S))){
				vel.y += 0.05;
			}
	}
	
	public void update(){
		if(vel.y <= 0.01f && vel.y >= -0.01f){
			//Main.player.gRect.changeTex(2, 100, true); // change to falling texture
		}
		if(Main.player.gRect.isDone() == 0){
			if(rolling) {
				gRect.changeTex(0, 1000, false); // change to ascending texture
			}
		}
		super.update();
	}

	public void respawn(Vector2f spawnpoint) {
		moveTo(spawnpoint);
		vel = new Vector2f(0,0);
	}
}

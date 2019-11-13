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

	}

	public void updateInput(){
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_A) {
				if (Keyboard.getEventKeyState()) {
				vel.x -= 5;
				gRect.changeTex(0, 700, true);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_D) {
				if (Keyboard.getEventKeyState()) {
				vel.x += 5;
				gRect.changeTex(0, 700, true);}
				
			}
			}
		}
		
		
			if ((Keyboard.isKeyDown(Keyboard.KEY_W))){

				//System.out.println(grounded);
				if(grounded){
					//Main.player.gRect.changeTex(1, (long)(jumpForce/(Main.gravity)), false); // change to before jumping texture
					vel.y = -jumpForce;
					grounded = false;
				}
				vel.y -= 0.05;
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_S))){
				vel.y += 0.05;
			}
	}
	
	public void update(){
		if(vel.y <= 0.01f && vel.y >= -0.01f){
			Main.player.gRect.changeTex(2, 100, true); // change to falling texture
		}
		if(Main.player.gRect.isDone() == 0){
			gRect.changeTex(1, 1000, true); // change to ascending texture
		}
		super.update();
	}

	public void respawn(Vector2f spawnpoint) {
		moveTo(spawnpoint);
		vel = new Vector2f(0,0);
	}
}

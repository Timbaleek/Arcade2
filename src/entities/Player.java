package entities;

import org.lwjgl.input.Keyboard;

import graphics.GraphicRect;
import main.Main;

public class Player extends CollidingGameEntity{
	
	public boolean grounded = true;
	public float jumpForce = 5f;

	public Player(Polygon polygon,GraphicRect graphicRect) {
		super(polygon, graphicRect);
	}

	public void updateInput(){
			if ((Keyboard.isKeyDown(Keyboard.KEY_A))){
				vel.x -= 0.05;
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_D))){
				vel.x += 0.05;
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_W))){

				System.out.println(grounded);
				if(grounded){
					//Main.player.rect.changeTex(1, (long)(jumpForce/(Main.gravity)), false); // change to before jumping texture
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
			//Main.player.rect.changeTex(2, 100, true); // change to falling texture
		}
		if(Main.player.rect.isDone() == 1){
			Main.player.rect.changeTex(1, (long)(jumpForce/(Main.gravity)), false); // change to ascending texture
		}
		super.update();
	}
}

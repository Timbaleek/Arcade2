package worlds;


import org.lwjgl.util.vector.Vector2f;

import arduinoCom.ArduinoCommunication;
import entities.CollidingGameEntity;
import graphics.GraphicRect;
import graphics.GraphicRectLoader;
import main.Main;

public class World1 extends World{

	GraphicRect mask;
	public World1(World world) {
		graphicGameEntities = world.graphicGameEntities;
		collidingGameEntities = world.collidingGameEntities;
		pathingGameEntities = world.pathingGameEntities;
		triggerGameEntities = world.triggerGameEntities;
		mask = new GraphicRect(Main.player.gRect.pos, new Vector2f(1080,1080), "Höhle Maske");
	}
	
	@Override
	public void update(){
		super.update();
		mask.pos.x = Main.player.gRect.pos.x - Main.player.gRect.size.x + (mask.size.x/2);
		mask.pos.y = Main.player.gRect.pos.y - Main.player.gRect.size.y + (mask.size.y/2);
	}
	
	public void render() {
		super.render();
		mask.render();
	}
	
	@Override
	public void trigger(CollidingGameEntity triggered){
		System.out.println(triggered.gRect.textureName);
		switch(triggered.gRect.textureName){
		case "Papierloch":
			Main.changeWorld();
		case "Pilz":
			Main.player.vel.y *= 1;
		}
	}
	
	@Override
	public void updateInput(String inputLine) {
		System.out.println("rightInput");
		int[] inputs = getInput(inputLine);
		try {
			System.out.println(inputs[0]);
			ArduinoCommunication.arduinoSend(String.valueOf(inputs[0]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(inputs[0]){
			case 1:
				if(inputs[1] == -1){
					Main.player.setVel(new Vector2f(-10,0));
				} else if(inputs[1] == 1){
					Main.player.setVel(new Vector2f(10,0));
				} else { //0
					Main.player.setVel(new Vector2f(0,0));
				}
				break;
			case 2:
				if(inputs[1] == -1){
					Main.player.setVel(new Vector2f(0,10));
				} else if(inputs[1] == 1){
					Main.player.setVel(new Vector2f(0,-10));
				} else { //0
					Main.player.setVel(new Vector2f(0,0));
				}
		}	
	}
}

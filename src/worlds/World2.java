package worlds;


import org.lwjgl.util.vector.Vector2f;

import arduinoCom.ArduinoCommunication;
import entities.CollidingGameEntity;
import main.Main;

public class World2 extends World{

	public World2(World world) {
		graphicGameEntities = world.graphicGameEntities;
		collidingGameEntities = world.collidingGameEntities;
		pathingGameEntities = world.pathingGameEntities;
		triggerGameEntities = world.triggerGameEntities;
	}
	
	@Override
	public void update(){
		super.update();
	}
	
	@Override
	public void trigger(CollidingGameEntity triggered){
		System.out.println(triggered.gRect.textureName);
		switch(triggered.gRect.textureName){
		case "Einsiedlerkrebs":
			Main.changeWorld();
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

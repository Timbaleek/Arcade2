package worlds;


import org.lwjgl.util.vector.Vector2f;

import arduinoCom.ArduinoCommunication;
import entities.CollidingGameEntity;
import main.Main;

public class World5 extends World{

	private boolean amH�gel;

	public World5(World world) {
		graphicGameEntities = world.graphicGameEntities;
		collidingGameEntities = world.collidingGameEntities;
		pathingGameEntities = world.pathingGameEntities;
		triggerGameEntities = world.triggerGameEntities;
	}
	
	public void update(){
		super.update();
	}
	
	@Override
	public void trigger(CollidingGameEntity triggered){
		switch(triggered.gRect.textureName){
		case "noTex":
			Main.changeWorld();
		case "h�gel":
			amH�gel = true;
			try {
				ArduinoCommunication.arduinoSend("2");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//activate drahtfolger
			//sperren
			if(amH�gel == false){
				Main.player.move(new Vector2f(100,0));
				Main.player.gRect.changeTex(1, 1, false);
			}
			
		}
		Main.player.respawn(spawnPoint);
	}
	
	@Override
	public void updateInput(String inputLine) {
		int [] inputs = getInput(inputLine);
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
			case 3:
				amH�gel = true;
		}
	}

}

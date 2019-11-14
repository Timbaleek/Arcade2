package worlds;


import org.lwjgl.util.vector.Vector2f;

import arduinoCom.ArduinoCommunication;
import entities.CollidingGameEntity;
import entities.Player;
import graphics.GraphicRectLoader;
import main.Main;
import util.PolygonLoader;

public class World0 extends World{

	public Vector2f spawnPoint = new Vector2f(1000,100);
	

	public World0(World world) {
		graphicGameEntities = world.graphicGameEntities;
		collidingGameEntities = world.collidingGameEntities;
		pathingGameEntities = world.pathingGameEntities;
		triggerGameEntities = world.triggerGameEntities;
		
		Main.player = new Player(PolygonLoader.load("res/worlds/world0/playerPoly.txt").get(0),
				GraphicRectLoader.load("res/worlds/world0/playerRect.txt"),spawnPoint);
		Main.player.addRects(GraphicRectLoader.loadColliders("res/worlds/world0/playerCol.txt").get(0));
	}
	
	public void update() {
		if(Main.player.gRect.pos.y > 10000) {
			Main.player.respawn(spawnPoint);
		}
		super.update();
	}
	
	@Override
	public void trigger(CollidingGameEntity triggered){
		System.out.println(triggered.gRect.textureName);
		switch(triggered.gRect.textureName){
		case "Papierloch":
			Main.changeWorld();
			break;
		case "Maulwurfshügel":
			System.out.println("c");
			Main.player.move(new Vector2f(1000,0));
			//Main.player.gRect.changeTex(1, 1, false);
//			try {
//				ArduinoCommunication.arduinoSend("p");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}//activate drahtfolger
//			Main.wait = true;
			
			break;
		case "Pflock 1":
		case "Pflock 2":
		case "Pflock 3":
		case "Pflock 4":
		case "Pflock 5":
			Main.player.respawn(spawnPoint);
			break;
		}

	}
	
	@Override
	public void updateInput(String inputLine) {
		int [] inputs = getInput(inputLine);
		switch(inputs[0]){
			case 1:
				if(inputs[1] == -1){
					Main.player.setVel(new Vector2f(-10,0));
					Main.player.gRect.changeTex(0, 10, true);
				} else if(inputs[1] == 1){
					Main.player.setVel(new Vector2f(10,0));
					Main.player.gRect.changeTex(0, 1200, true);
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
				break;
			case 3:
				Main.player.move(new Vector2f(1000,0));
				Main.player.gRect.changeTex(1, 1, false);
				Main.wait = false;
				break;
		}
	}

}

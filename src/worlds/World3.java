package worlds;


import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import arduinoCom.ArduinoCommunication;
import entities.CollidingGameEntity;
import entities.Polygon;
import graphics.GraphicRect;
import main.Main;

public class World3 extends World{

	CollidingGameEntity[] flyingObjects;
	
	public World3(World world) {
		graphicGameEntities = world.graphicGameEntities;
		collidingGameEntities = world.collidingGameEntities;
		pathingGameEntities = world.pathingGameEntities;
		triggerGameEntities = world.triggerGameEntities;
		
		for(int i = 0; i<4; i++){
			Random r = new Random();
			flyingObjects[i] = new CollidingGameEntity(new GraphicRect(new Vector2f(r.nextInt((int) (Main.camera.pos.x+Main.screenWidth))+(Main.camera.pos.x-Main.screenWidth),
													Main.camera.getTopEdge()-100), new Vector2f(100,100), "Hammer"));
			ArrayList<Vector2f> v = new ArrayList<Vector2f>();
			v.add(flyingObjects[i].gRect.pos);
			v.add(Main.player.gRect.pos);
			flyingObjects[i].setPath(new Polygon(new Vector2f(0,0),v));
			pathingGameEntities.add(flyingObjects[i]);
		}
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
			
		}	
	}
}

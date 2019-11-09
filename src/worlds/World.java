	package worlds;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import entities.CollidingGameEntity;
import main.Main;
import util.CollisionHandler;

public class World {
	public List<CollidingGameEntity> graphicGameEntities = new ArrayList<CollidingGameEntity>();
	public List<CollidingGameEntity> collidingGameEntities = new ArrayList<CollidingGameEntity>();
	public List<CollidingGameEntity> pathingGameEntities = new ArrayList<CollidingGameEntity>();
	public List<CollidingGameEntity> triggerGameEntities = new ArrayList<CollidingGameEntity>();
	public CollidingGameEntity end = null;
	
	public Vector2f spawnPoint = new Vector2f((Main.screenWidth/2)+(Main.player.gRect.size.x/2), 300);
	
	public World(){
	}
	

	public void update(){
		
		for (CollidingGameEntity g:graphicGameEntities){
			g.update();
		}
		for (CollidingGameEntity c: collidingGameEntities){
			CollisionHandler.resolveRectCollision(Main.player, c);
			//CollisionHandler.resolveSATCollision(Main.player, c);
//			System.out.println((CollisionHandler.detectSATCollision(Main.player, c)));
		}
		
		for (CollidingGameEntity p:pathingGameEntities){
			p.updatePath();
		}
		
		for (CollidingGameEntity k:triggerGameEntities){
			if(CollisionHandler.detectSATCollision(Main.player, k)){
				Main.player.respawn(spawnPoint);
			}
		}
		
		if(CollisionHandler.detectSATCollision(Main.player, triggerGameEntities.get(triggerGameEntities.size()-1))){
			Main.changeWorld();
		}
	}
	
	public void render(){
		for (CollidingGameEntity c:graphicGameEntities){
			c.gRect.renderAnim();
			//SAT DEBUGGING
//			for(Polygon p:c.polygons){
//				p.render();
//				CollisionHandler.renderNormals(p);
//			}
		}
	}

	public int[] getInput(String inputLine){
		String[] stringInputs = inputLine.split(",");
		int[] inputs = new int[stringInputs.length];
		for(int i = 0; i<stringInputs.length; i++) inputs[i] = Integer.parseInt(stringInputs[i]);
		return inputs;
	}
	
	public void updateInput(String inputLine) {
	}
}

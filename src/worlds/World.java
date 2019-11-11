	package worlds;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import entities.CollidingGameEntity;
import entities.Polygon;
import graphics.Rect;
import main.Main;
import util.CollisionHandler;

public class World {
	public List<CollidingGameEntity> graphicGameEntities = new ArrayList<CollidingGameEntity>();
	public List<CollidingGameEntity> collidingGameEntities = new ArrayList<CollidingGameEntity>();
	public List<CollidingGameEntity> pathingGameEntities = new ArrayList<CollidingGameEntity>();
	public List<CollidingGameEntity> triggerGameEntities = new ArrayList<CollidingGameEntity>();
	
	public Vector2f spawnPoint = new Vector2f(0,0);
	
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
			p.moveTowards(p.path.shape.get(0), 0.001f);
			p.updatePath();
		}
		
		for (CollidingGameEntity k:triggerGameEntities){
			//CollisionHandler.resolveSATCollision(Main.player, k);
			if(CollisionHandler.detectSATCollision(Main.player, k)){
				trigger(k);
			}
		}
		
	}
	
	public void render(){
		for (CollidingGameEntity c:graphicGameEntities){
			c.gRect.renderAnim();
			//SAT DEBUGGING
			for(Polygon p:c.polygons){
				p.render();
				CollisionHandler.renderNormals(p);
			}
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
	
	public void trigger(CollidingGameEntity triggered){
	}
}

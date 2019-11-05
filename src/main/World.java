	package main;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import entities.CollidingGameEntity;
import entities.Polygon;
import util.CollisionHandler;

public class World {
	public List<CollidingGameEntity> collidingGameEntities = new ArrayList<CollidingGameEntity>();
	public List<CollidingGameEntity> pathingGameEntities = new ArrayList<CollidingGameEntity>();
	public List<CollidingGameEntity> killingGameEntities = new ArrayList<CollidingGameEntity>();
	
	Vector2f spawnPoint = new Vector2f((Main.screenWidth/2)+(Main.player.rect.size.x/2), 300);
	
	public World(List<CollidingGameEntity> collidingGameEntities){
		this.collidingGameEntities = collidingGameEntities;
		Vector2f[] path = {new Vector2f(0,0),new Vector2f(400,130),new Vector2f(200,650)};
		collidingGameEntities.get(0).path = path;
		pathingGameEntities.add(collidingGameEntities.get(0));
	}
	

	public void update(){

		for (CollidingGameEntity c:collidingGameEntities){
			c.update();
			//CollisionHandler.resolveRectCollision(Main.player, c);
			CollisionHandler.resolveSATCollision(Main.player, c);
//			System.out.println((CollisionHandler.detectSATCollision(Main.player, c)));
		}
		for (CollidingGameEntity p:pathingGameEntities){
			p.updatePath();
		}
		for (CollidingGameEntity k:killingGameEntities){
			if(CollisionHandler.detectSATCollision(Main.player, k)){
				Main.player.respawn(spawnPoint);
			}
		}
	}
	
	public void render(){
		for (CollidingGameEntity c:collidingGameEntities){
			c.rect.renderAnim();
			for(Polygon p:c.polygons){
				p.render();
				CollisionHandler.renderNormals(p);
			}
		}
	}
}

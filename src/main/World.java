	package main;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import entities.CollidingGameEntity;
import util.CollisionHandler;

public class World {
	public List<CollidingGameEntity> collidingGameEntities = new ArrayList<CollidingGameEntity>();
	
	public World(List<CollidingGameEntity> collidingGameEntities){
		this.collidingGameEntities = collidingGameEntities;
	}
	
	public void update(){
		for (CollidingGameEntity c:collidingGameEntities){
			c.update();
//			CollisionHandler.resolveSATCollision(Main.player, c);
			System.out.println((CollisionHandler.detectSATCollision(Main.player, c)));
		}
	}
	
	public void render(){
		for (CollidingGameEntity c:collidingGameEntities){
			c.rect.render();
			c.poly.render();
			CollisionHandler.renderNormals(c.poly);
		}
	}
}

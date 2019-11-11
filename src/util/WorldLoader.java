package util;

import java.io.File;
import java.util.ArrayList;
import entities.CollidingGameEntity;
import entities.Polygon;
import graphics.GraphicRect;
import graphics.GraphicRectLoader;
import graphics.Rect;
import worlds.World;

public class WorldLoader {

	public static World loadWorld(int worldNumber){
		World world = new World();

		//ArrayList<CollidingGameEntity> gameEntities = null;
		String worldPath = "res/worlds/world" + worldNumber;
		//File worldDirectory = new File(worldPath);
		//File[] directories = worldDirectory.listFiles();
		//for(File directory:directories){
		//	if (directory.isDirectory()) { .getAbsolutePath()
		String[] pFiles = new File(worldPath + "/rects/").list();
		for(String file:pFiles){
			GraphicRect gRect = GraphicRectLoader.load(worldPath + "/rects/" +file);
			CollidingGameEntity c = new CollidingGameEntity(gRect);
			world.graphicGameEntities.add(c);
			ArrayList<Polygon> polygons = PolygonLoader.load(worldPath+ "/polygons/"+file);
			if(polygons!=null){
				c.setPolygons(polygons);
				world.triggerGameEntities.add(c);
			}
			ArrayList<Polygon> paths = PolygonLoader.load(worldPath+"/path/"+file);
			if(paths!=null){
				c.setPath(paths.get(0));
				world.pathingGameEntities.add(c);
			}
			ArrayList<Rect> colliders = GraphicRectLoader.loadColliders(worldPath+"/col/"+file);
			if(colliders!=null){
				c.setRects(colliders);
				world.collidingGameEntities.add(c);
			}
		}

		return world;
	}

}

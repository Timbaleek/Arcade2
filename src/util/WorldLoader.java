package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import entities.CollidingGameEntity;
import entities.Polygon;
import graphics.GraphicRect;
import graphics.GraphicRectLoader;
import main.World;

public class WorldLoader {
	
	public static World loadWorld(int worldNumber){
		List<CollidingGameEntity> collidingGameEntities = new ArrayList<CollidingGameEntity>();
		
		String dirPath = "res/worlds/world" + worldNumber + "/polygons/";
		String[] pFiles = new File(dirPath).list();
		for(String file:pFiles){
			dirPath = "res/worlds/world" + worldNumber + "/polygons/";
			ArrayList<Polygon> polygons = PolygonLoader.load(dirPath+file);
			dirPath = "res/worlds/world" + worldNumber + "/rects/";
			GraphicRect rect = GraphicRectLoader.load(dirPath+file);
			collidingGameEntities.add(new CollidingGameEntity(rect,polygons));
		}
		return new World(collidingGameEntities);
	}
	
}

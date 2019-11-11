package util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector2f;

import entities.Polygon;

import java.util.ArrayList;

public class PolygonLoader {
	public static ArrayList<Polygon> load(String path){
		ArrayList<Polygon> polygons = new ArrayList<Polygon>();
		Vector2f pos = null;
		ArrayList<Vector2f> points = new ArrayList<Vector2f>();
		boolean firstLine = true;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(path));
			//System.out.println("Reading Collider: " + path.substring(path.lastIndexOf("/")));
			//read line by line
			while(scanner.hasNextLine()){
				//process each line
				String line = scanner.nextLine();
				String[]objects = line.split(",");
				if(firstLine){
					pos = new Vector2f(Float.parseFloat(objects[0]),Float.parseFloat(objects[1]));
					firstLine = false;
				} else {
					if(objects.length%2==0&&objects.length>0){
						for(int i = 0; i < objects.length; i+=2){
							points.add(new Vector2f(Float.parseFloat(objects[i]),Float.parseFloat(objects[i+1])));
						}
					} else {
						System.out.println("Wrong Polygon");
					}
				}
				if(points.size()>1){
					polygons.add(new Polygon(pos, points));
				}
			}
			
			if(polygons.size()>1){
				return polygons;
			}
		} catch (IOException e) {
			//System.out.println("No Polygon");
			return null;
		}
		return null;
	}
}	

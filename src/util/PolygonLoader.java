package util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector2f;

import entities.Polygon;

import java.util.ArrayList;

public class PolygonLoader {
	public static Polygon load(String path){
		Vector2f pos = null;
		ArrayList<Vector2f> points = new ArrayList<>();
		boolean firstLine = true;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Reading Collider: " + path.substring(path.lastIndexOf("/")));
		//read line by line
		while(scanner.hasNextLine()){
		    //process each line
		    String line = scanner.nextLine();
		    String[]object = line.split(",");
		    if(firstLine){
		    	pos = new Vector2f(Float.parseFloat(object[0]),Float.parseFloat(object[1]));
		    	firstLine = false;
		    } else {
		    	points.add(new Vector2f(Float.parseFloat(object[0]),Float.parseFloat(object[1])));
		    }
		}
		System.out.println(points);
		return new Polygon(pos, points);
	}
	
}

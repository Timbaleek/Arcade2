package entities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import main.Main;

public class Polygon {
	
	public Vector2f pos;
	public ArrayList<Vector2f> shape;
	
	public Polygon(Vector2f pos, ArrayList<Vector2f> shape){
		this.pos = pos;
		this.shape = shape;
	}
	
	public void render(){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(255, 0, 100, Main.transparency);
		GL11.glBegin(GL11.GL_POLYGON);
		for(Vector2f point:shape){
			GL11.glVertex2f(point.getX(),point.getY());
		}
		GL11.glEnd();
	}

	public void move(Vector2f move){
		pos.x += move.x;
		pos.y += move.y;
		for(Vector2f p:shape){
			p.x += move.x;
			p.y += move.y;
		}
	}
	
	public void savePolygon(String path, int polygonNumber){
		try {
			Writer wr = new FileWriter(path + polygonNumber +".txt");
			wr.write(pos.x+","+pos.y+"\n");
			for(Vector2f p:shape){
				wr.write((int)p.x+","+(int)p.y+"\n");
			}
			wr.flush();
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

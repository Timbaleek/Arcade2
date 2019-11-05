package entities;

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
	
	public void move(float moveX, float moveY){
		pos.x += moveX;
		pos.y += moveY;
		for(Vector2f p:shape){
			p.x += moveX;
			p.y += moveY;
		}
	}
	
	public Vector2f getCenterPoint(){
		if(shape.size() != 0){
			float f = 0, area = 0;
			Vector2f first = shape.get(0);
			Vector2f center = new Vector2f(0,0);
			for (int i = 0, j = shape.size() - 1; i < shape.size(); j = i++) {
				Vector2f p1 = shape.get(i), p2 = shape.get(j);
				f = (p1.x - first.x) * (p2.y - first.y) - (p2.x - first.x) * (p1.y - first.y);
				area += f;
				center.x += (p1.x + p2.x - 2 * first.x) * f;
				center.y += (p1.y + p2.y - 2 * first.y) * f;
			}
			area *= 3;
			return new Vector2f(center.x / area + first.x,center.y / area + first.y);
		}
		return null;
	}
}

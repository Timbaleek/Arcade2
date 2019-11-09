package util;

import org.lwjgl.util.vector.Vector2f;

import entities.CollidingGameEntity;
import entities.Polygon;
import graphics.Rect;
import main.Main;

//Idea from:
//https://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
//https://www.youtube.com/watch?v=7Ik2vowGcU0

public class CollisionHandler {

	public static Line currentLine = new Line(new Vector2f(0,0),new Vector2f(0,0));
	public static Line currentNormal = new Line(new Vector2f(0,0),new Vector2f(0,0));
	
	public static Vector2f doSATCollision(Polygon movingE, Polygon staticE) {
		
		//!!!
		Polygon c2 = movingE;
		Polygon c1 = staticE;
		float overlap = Float.MAX_VALUE;
		
		for (int poly = 0; poly < 2; poly++) { // checking one against the other
			
			if (poly == 1) { // switching the one and other
				c2 = movingE;
				c1 = staticE;
			}
			for (int i = 0; i < c1.shape.size(); i++) {
				Vector2f a = c1.shape.get(i);
				Vector2f b = c1.shape.get((i + 1) % c1.shape.size());
				Vector2f edge = new Vector2f(b.x-a.x, b.y-a.y);
				
				Vector2f normal;
				if (poly == 0) {
					normal = new Vector2f((edge.y),-(edge.x));
				} else {
					normal = new Vector2f(-(edge.y),(edge.x));
				}
				//Vector2f axisProj = new Vector2f((edge.y), //get normal axis
				//								-(edge.x));//Clockwise(y,-x)
				normal.normalise();
				//float d = (float) Math.sqrt(axisProj.x * axisProj.x + axisProj.y * axisProj.y); //length
				//axisProj.set(axisProj.x / d, axisProj.y / d); //scale
				currentNormal = new Line(new Vector2f(normal.x+b.x, normal.y+b.y),
										 new Vector2f(normal.x*100+b.x, normal.y*100+b.y));
				new Line(a, b).render();
				
				float min1 = Float.MAX_VALUE, max1 = Float.MIN_VALUE;
				for(Vector2f p:c1.shape){ // Farthest points
					float q = Vector2f.dot(normal,p);
					//float q = (p.x * axisProj.x + p.y * axisProj.y); //dotProduct
					min1 = Math.min(min1, q);
					max1 = Math.max(max1, q);
				}
				
				float min2 = Float.MAX_VALUE, max2 = Float.MIN_VALUE;
				for(Vector2f p:c2.shape){ // Farthest points
					float q = Vector2f.dot(normal,p);
					//float s = (r.x * axisProj.x + r.y * axisProj.y); //dotProduct
					min2 = Math.min(min2, q);
					max2 = Math.max(max2, q);
				}
				overlap = Math.min(Math.min(max1,max2) - Math.max(min1, min2), overlap);
				
				if (!(max2 >= min1 && max1 >= min2)) {//axis not overlapping
					return null;
				} 
			}
		}
		
		//Fehler polygon.pos? problem nur bei positiv, nicht bei negagiv
		//OHNE ABS
		/*Vector2f centerS = staticE.getCenterPoint();
		Vector2f centerM = movingE.getCenterPoint();*/
		Vector2f d = new Vector2f(staticE.pos.x - movingE.pos.x, staticE.pos.y - movingE.pos.y); //minimalTranslationVector
		//float s = (float) Math.sqrt(d.x*d.x + d.y*d.y);
		float s = d.length();
		
		Vector2f move = new Vector2f(-overlap * d.x / s, -overlap * d.y / s); //block
		
		//staticE.move(new Vector2f(overlap * d.x / s, overlap * d.y / s)); //push
		//return null;
		return move;
	}
	
	public static boolean detectSATCollision(CollidingGameEntity movingE, CollidingGameEntity staticE) {
		if(detectRectCollision(movingE, staticE)){ //rough
			for(Polygon m:movingE.polygons){
				for(Polygon s:staticE.polygons){
					Vector2f move1 = doSATCollision(m, s);
					Vector2f move2 = doSATCollision(s, m);
					if(move1 != null && move2 != null){
						/*Vector2f offset = new Vector2f(100, 100);
						new Line(offset, Vector2f.add(move1, offset, null)).render(1, 0, 0, 1);
						new Line(offset, Vector2f.add(move2, offset, null)).render(0, 0, 1, 1);*/
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void resolveSATCollision(CollidingGameEntity movingE, CollidingGameEntity staticE) {
		if(detectRectCollision(movingE, staticE)){ //rough
			for(Polygon m:movingE.polygons){
				for(Polygon s:staticE.polygons){
					//System.out.println(s.shape);
					//System.out.println(s.pos);
					Vector2f move = doSATCollision(m, s);
					Vector2f move1 = doSATCollision(s, m);
					if(move != null&&move1 != null){
						if(move1.length()<move.length() && move1.length()>0){
							//move1 = (Vector2f)move1.scale(-1f);
							movingE.move(move1);
						} else {
							movingE.move(move);
						}
					}
				}
			}
		}
	}
	
	public static Vector2f doRectCollision(Rect r1, Rect gRect){
		float r1Left = r1.pos.x, r1Right = r1.pos.x + r1.size.x, r1Top = r1.pos.y, r1Bottom = r1.pos.y + r1.size.y;
		float r2Left = gRect.pos.x, r2Right = gRect.pos.x + gRect.size.x, r2Top = gRect.pos.y, r2Bottom = gRect.pos.y + gRect.size.y;
		Vector2f overlap = new Vector2f(0,0);
		
		if(r1Left<r2Left){
			overlap.x = r2Left-r1Right;
		} else {
			overlap.x = r1Left-r2Right;
		}
		if(r1Top<r2Top){
			overlap.y = r2Top-r1Bottom;
		} else {
			overlap.y = r1Top-r2Bottom;
		}
		if(overlap.x<=0 && overlap.y<=0){
			return overlap;
		}
		return null;
	}
	
	public static boolean detectRectCollision(CollidingGameEntity movingE, CollidingGameEntity staticE) {
		if(doRectCollision(movingE.gRect, staticE.gRect)!=null){ return true;}
		return false;
	}
	
	public static void resolveRectCollision(CollidingGameEntity movingE, CollidingGameEntity staticE){
		Vector2f move = doRectCollision(movingE.gRect, staticE.gRect);
		if(move != null){
			if(move.x>move.y){
				movingE.move(new Vector2f(move.x*Math.signum(movingE.vel.x),0f));
			} else {
				movingE.move(new Vector2f(0f, move.y*Math.signum(movingE.vel.y)));
			}
			if(movingE.vel.y>0) Main.player.grounded = true;
			Main.player.gRect.changeTex(0, 1000,false); // change to landed texture
		}
	}
	
	public static void renderNormals(Polygon e){
		for (int i = 0; i < e.shape.size(); i++) {
			Vector2f a = e.shape.get(i);
			Vector2f b = e.shape.get((i + 1) % e.shape.size());
			Vector2f edge = new Vector2f(b.x-a.x, b.y-a.y);
			new Line(a, b).render();
			
			Vector2f normal = new Vector2f(-(edge.y),(edge.x));
			
			if(!normal.equals(new Vector2f(0,0))){
				normal.normalise();
			}
			
			currentNormal = new Line(new Vector2f(normal.x+b.x, normal.y+b.y),
									 new Vector2f(normal.x*100+b.x, normal.y*100+b.y));
			currentNormal.render();
		}
	}
}

package graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

public class GraphicRect {
	public Vector2f pos;
	public Vector2f size;
	private Texture tex;
	public String textureName;
	
	public GraphicRect(Vector2f pos, Vector2f size, String textureName) {
		this.pos = pos;
		this.size = size;
		this.textureName = textureName;
		tex = GraphicRectLoader.initTex(textureName);
	}
	
	public GraphicRect(Vector2f pos, Vector2f size, Texture tex) {
		this.pos = pos;
		this.size = size;
		this.tex = tex;
	}
	
	public void move(Vector2f move){
		pos.x += move.x;
		pos.y += move.y;
	}
	
	public void render(){
		GL11.glColor3f(255, 255, 255);
		tex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(pos.x,pos.y);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(pos.x,pos.y+size.y);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(pos.x+size.x,pos.y+size.y);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(pos.x+size.x,pos.y);
		GL11.glEnd();
	}
}

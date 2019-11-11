package graphics;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import main.Main;

public class GraphicRect extends Rect{
	private Texture tex;
	private ArrayList<Texture> textures = new ArrayList<Texture>();;
	private int texIndex;
	public String textureName;
	
	private long lastTime = 0;
	private long millisPerState;
	private int state = 0;
    private int stateCount = 1;
    private ArrayList<Integer> stateCounts = new ArrayList<Integer>();
    private boolean looping = false;
	
	public GraphicRect(Vector2f pos, Vector2f size, Texture tex, String textureName) { //reduces loading if the same texture is used for multiple objects
		super(pos,size);
		this.tex = tex;
		this.textureName = textureName;
	}

	public GraphicRect(Vector2f pos, Vector2f size, String textureName) { // NOT USED - for non-changing textures 
		super(pos,size);
		tex = GraphicRectLoader.initTex(textureName);
		textures.add(GraphicRectLoader.initTex(textureName));
	}
	
	public GraphicRect(Vector2f pos, Vector2f size, String textureName, int stateCount) { // for animations
		super(pos,size);
		tex = GraphicRectLoader.initTex(textureName);
		this.stateCount = stateCount;
		stateCounts.add(stateCount);
		this.textureName = textureName;
		textures.add(GraphicRectLoader.initTex(textureName));
	}
	
	public void addTexture(String textureName, int stateCount) {
		this.textureName = textureName;
		stateCounts.add(stateCount);
		textures.add(GraphicRectLoader.initTex(textureName));
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
	
	public void renderAnim(){
		updateState();
		float x1 = (float)state/stateCount;
		float x2 = ((float)state+1f)/stateCount;
		
		GL11.glColor3f(255, 255, 255);
		tex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(x1, 0);
	    GL11.glVertex2f(pos.x,pos.y);
	    GL11.glTexCoord2f(x1, 1);
	    GL11.glVertex2f(pos.x,pos.y+size.y);
	    GL11.glTexCoord2f(x2, 1);
	    GL11.glVertex2f(pos.x+size.x,pos.y+size.y);
	    GL11.glTexCoord2f(x2, 0);
	    GL11.glVertex2f(pos.x+size.x,pos.y);
		GL11.glEnd();
	}
	
	private void updateState(){
		if(Main.getMillis() - lastTime > millisPerState){
			state++;
			
			lastTime = Main.getMillis();
			if(looping){
				state%=stateCount; 
			} else{
				if(state >= stateCount){
					state = stateCount;
				}
			}
		}
			
	}
	
	public void changeTex(int texIndex, long animDuration, boolean looping){
		this.texIndex = texIndex;
		tex = textures.get(texIndex);
		state = 0;
		lastTime = Main.getMillis();
		this.millisPerState = animDuration/stateCounts.get(texIndex);
		stateCount = stateCounts.get(texIndex);
		this.looping = looping;
	}
	
	public int isDone(){
		if(state == stateCount) return texIndex;
		return -1;
	}
}

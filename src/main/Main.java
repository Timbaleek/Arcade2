package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import entities.Player;
import graphics.GraphicRect;
import graphics.GraphicRectLoader;
import util.CollisionHandler;
import util.Line;
import util.PolygonLoader;
import util.WorldLoader;

public class Main {

	final static int screenWidth = 1920;
	final static int screenHeight = 1080;

	public static void main(String[] args) {
		
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Display.setTitle("Arcade");

    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, screenWidth, screenHeight, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLineWidth(5f);
		init();
		
		while (!Display.isCloseRequested()) {
			 
		    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		    GL11.glClearColor(255, 255, 255, 1);
		    render();
		    update();
	 
		    Display.update();
		}
		
		Display.destroy();
	}

	public static float transparency = 0.5f;
	static final int numberOfWorlds = 1;
	static World currentWorld; 
	public static int currentWorldNumber = 1;
	
	static final float tileSize = 500;
	static GraphicRect[][] backgroundTiles = new GraphicRect[(int)Math.ceil(screenWidth/tileSize)+1][(int)Math.ceil(screenHeight/tileSize)+1];
	
	public static final float gravity = 0.02f;
	
	static Camera camera;
	public static Player player;
	private static void init() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		for(int i = 0; i < backgroundTiles.length; i++){
			for(int j = 0; j < backgroundTiles[i].length; j++){
				Texture tex = GraphicRectLoader.initTex("raster");
				backgroundTiles[i][j] = new GraphicRect(new Vector2f(i*tileSize,j*tileSize), new Vector2f(tileSize,tileSize), tex);
			}
		}
		
		currentWorld = WorldLoader.loadWorld(currentWorldNumber);
		player = new Player(PolygonLoader.load("res/worlds/world" + currentWorldNumber + "/playerPoly.txt"),
				GraphicRectLoader.load("res/worlds/world" + currentWorldNumber + "/playerRect.txt"));

		camera = new Camera(new Vector2f(0,0), 1);
		onPlayerMove();
	}
	
	private static void update() {
		player.updateInput();
		//player.vel.y += gravity;
		player.update();
		currentWorld.update();
		if(!(player.vel.x == 0 && player.vel.y == 0)) onPlayerMove();
		//changeWorld(1);
	}
	
	private static void onPlayerMove() { // to reduce the calculations to only when the player moves
		camera.pos.x = player.poly.pos.x-(screenWidth/2);
		camera.pos.y = player.poly.pos.y-(screenHeight/2);
		for(int i = 0; i < backgroundTiles.length; i++){
			for(int j = 0; j < backgroundTiles[i].length; j++){
				backgroundTiles[i][j].pos.x = camera.getLeftEdge() - (camera.getLeftEdge()%tileSize) + i*tileSize;
				backgroundTiles[i][j].pos.y = camera.getTopEdge() - (camera.getTopEdge()%tileSize) + j*tileSize;
			}
		}
	}

	private static void changeWorld(int worldNumber){
		currentWorldNumber = worldNumber;
		currentWorld = WorldLoader.loadWorld(currentWorldNumber);
		//TODO reset player
	}
	
	private static void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(-camera.pos.x, -camera.pos.y, 0);
		for(int i = 0; i < backgroundTiles.length; i++){
			for(int j = 0; j < backgroundTiles[i].length; j++){
				backgroundTiles[i][j].render();
			}
		}
		currentWorld.render();
		
		new Line(new Vector2f(0,0), new Vector2f(0,1000)).render();
		new Line(new Vector2f(0,0), new Vector2f(1000,0)).render();
		
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		//GLU.gluLookAt(camera.pos.x, camera.pos.y, 0f, player.rect.pos.x, player.rect.pos.y,0f,0f,0f,1f);
		//Vector2f oldPos = player.rect.pos;
		//player.rect.pos.x = -player.rect.size.x/2; player.rect.pos.y = -player.rect.size.y/2;
		//GL11.glRotatef(90f, 0, 0, 1);
		//splayer.rect.pos=oldPos;
		player.rect.renderAnim();
		//player.poly.render();
		//CollisionHandler.renderNormals(player.poly);
		GL11.glPopMatrix();
	}

	public static long getMillis() {
		return System.nanoTime()/1000000;
	}

}


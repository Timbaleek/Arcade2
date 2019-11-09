package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import arduinoCom.ArduinoCommunication;
import entities.Player;
import entities.Polygon;
import graphics.GraphicRect;
import graphics.GraphicRectLoader;
import util.CollisionHandler;
import util.Line;
import util.PolygonLoader;
import util.WorldLoader;
import worlds.World;

public class Main {

	public final static int screenWidth = 1920;
	public final static int screenHeight = 1080;

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

	public final static Vector2f nullVec = new Vector2f(0,0);
	
	public static float transparency = 0.5f;
	static final int numberOfWorlds = 1;
	public static World currentWorld; 
	public static int currentWorldNumber = 1;
	
	static final float tileSize = 500;
	static GraphicRect[][] backgroundTiles = new GraphicRect[(int)Math.ceil(screenWidth/tileSize)+1][(int)Math.ceil(screenHeight/tileSize)+1];
	
	public static final float gravity = 0.02f;
	
	static Camera camera;
	public static Player player;
	private static void init() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		try {
			ArduinoCommunication.arduinoListen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		player = new Player(PolygonLoader.load("res/worlds/world" + currentWorldNumber + "/playerPoly.txt").get(0),
				GraphicRectLoader.load("res/worlds/world" + currentWorldNumber + "/playerRect.txt"));
		
		for(int i = 0; i < backgroundTiles.length; i++){
			for(int j = 0; j < backgroundTiles[i].length; j++){
				Texture tex = GraphicRectLoader.initTex("raster");
				backgroundTiles[i][j] = new GraphicRect(new Vector2f(i*tileSize,j*tileSize), new Vector2f(tileSize,tileSize), tex);
			}
		}
		
		currentWorld = WorldLoader.loadWorld(currentWorldNumber);
		
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
		camera.pos.x = player.gRect.pos.x+(player.gRect.size.x/2)-(screenWidth/2);
		camera.pos.y = player.gRect.pos.y+(player.gRect.size.y/2)-(screenHeight/2);
		for(int i = 0; i < backgroundTiles.length; i++){
			for(int j = 0; j < backgroundTiles[i].length; j++){
				backgroundTiles[i][j].pos.x = camera.getLeftEdge() - (camera.getLeftEdge()%tileSize) + i*tileSize;
				backgroundTiles[i][j].pos.y = camera.getTopEdge() - (camera.getTopEdge()%tileSize) + j*tileSize;
			}
		}
	}

	public static void changeWorld(){
		currentWorldNumber++;
		currentWorld = WorldLoader.loadWorld(currentWorldNumber);
		player.respawn(currentWorld.spawnPoint);
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
		
		player.gRect.renderAnim();
		//SAT debugging
//		for(Polygon poly:player.polygons){
//			poly.render();
//			CollisionHandler.renderNormals(poly);
//		}
		//
		GL11.glPopMatrix();
	}

	public static long getMillis() {
		return System.nanoTime()/1000000;
	}

}


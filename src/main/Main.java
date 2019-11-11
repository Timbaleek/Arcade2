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
import worlds.*;
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
	static final int numberOfWorlds = 6;
	public static int currentWorldNumber = 0;
	public static World[] worlds = new World[numberOfWorlds];
	static final float tileSize = 500;
	static GraphicRect[][] backgroundTiles = new GraphicRect[(int)Math.ceil(screenWidth/tileSize)+1][(int)Math.ceil(screenHeight/tileSize)+1];
	
	public static final float gravity = 0.02f;
	public static final Vector2f spawnPoint = new Vector2f(1000,600);
	static boolean loading = true;
	
	public static Camera camera;
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
				GraphicRectLoader.load("res/worlds/world" + currentWorldNumber + "/playerRect.txt"),spawnPoint);
		
		for(int i = 0; i < backgroundTiles.length; i++){
			for(int j = 0; j < backgroundTiles[i].length; j++){
				Texture tex = GraphicRectLoader.initTex("raster");
				backgroundTiles[i][j] = new GraphicRect(new Vector2f(i*tileSize,j*tileSize), new Vector2f(tileSize,tileSize), tex, "bg");
			}
		}
		
		worlds[currentWorldNumber] = new World0(WorldLoader.loadWorld(currentWorldNumber));
		
		camera = new Camera(new Vector2f(player.gRect.pos.x+(player.gRect.size.x/2)-(screenWidth/2),player.gRect.pos.y+(player.gRect.size.y/2)-(screenHeight/2)), 1);
		onPlayerMove();
		
		loading = false;
	}
	
	private static void update() {
		if(player!=null){
			player.updateInput();
			//player.vel.y += gravity;
			player.update();
			
			worlds[currentWorldNumber].update();
			if(!(player.vel.x == 0 && player.vel.y == 0)) onPlayerMove();
		}
	}
	
	public static void updateArduinoInput(String inputLine) {
		if(inputLine.charAt(0) == '.'){
			loading = false;
		}
		worlds[currentWorldNumber].updateInput(inputLine);
	}
	
	private static void onPlayerMove() { // to reduce the calculations to only when the player moves
		camera.pos.x = player.gRect.pos.x+(player.gRect.size.x/2)-(screenWidth/2);
		if(camera.pos.x < 0) {
			camera.pos.x = 0;
		}
		camera.pos.y = player.gRect.pos.y+(player.gRect.size.y/2)-(screenHeight/2);
		if(camera.pos.y < 0) {
			camera.pos.y = 0;
		}
		for(int i = 0; i < backgroundTiles.length; i++){
			for(int j = 0; j < backgroundTiles[i].length; j++){
				backgroundTiles[i][j].pos.x = camera.getLeftEdge() - (camera.getLeftEdge()%tileSize) + i*tileSize;
				backgroundTiles[i][j].pos.y = camera.getTopEdge() - (camera.getTopEdge()%tileSize) + j*tileSize;
			}
		}
	}

	public static void changeWorld(){
		currentWorldNumber++;
//		try {
//			ArduinoCommunication.arduinoSend("n");
//		} catch (Exception e) {
//			System.out.println("Could not change lager");
//			e.printStackTrace();
//		}
		player = null;
		switch(currentWorldNumber){
		case 0:
			worlds[currentWorldNumber] = new World0(WorldLoader.loadWorld(currentWorldNumber));
		case 1:
			worlds[currentWorldNumber] = new World1(WorldLoader.loadWorld(currentWorldNumber));
		case 2:
			worlds[currentWorldNumber] = new World2(WorldLoader.loadWorld(currentWorldNumber));
		case 3:
			worlds[currentWorldNumber] = new World3(WorldLoader.loadWorld(currentWorldNumber));
		case 4:
			worlds[currentWorldNumber] = new World4(WorldLoader.loadWorld(currentWorldNumber));
		case 5:
			worlds[currentWorldNumber] = new World5(WorldLoader.loadWorld(currentWorldNumber));
		}
		
		GraphicRect elephant = new GraphicRect(new Vector2f(Main.screenWidth/2+300,Main.screenHeight/2+300), new Vector2f(600,600), "elephant");
		//TODO
		while(loading){
			elephant.render();
		}; //do nothing while loading
		
		player = new Player(PolygonLoader.load("res/worlds/world" + currentWorldNumber + "/playerPoly.txt").get(0),
							GraphicRectLoader.load("res/worlds/world" + currentWorldNumber + "/playerRect.txt"),spawnPoint);
	}
	
	private static void render() {
		if(player != null){
			GL11.glPushMatrix();
			GL11.glTranslatef(-camera.pos.x, -camera.pos.y, 0);
			
			for(int i = 0; i < backgroundTiles.length; i++){
				for(int j = 0; j < backgroundTiles[i].length; j++){
					backgroundTiles[i][j].render();
				}
			}
			worlds[currentWorldNumber].render();
			
			new Line(new Vector2f(0,0), new Vector2f(0,1000)).render();
			new Line(new Vector2f(0,0), new Vector2f(1000,0)).render();
			
			player.gRect.renderAnim();
			for(Polygon poly:player.polygons){
				poly.render();
				CollisionHandler.renderNormals(poly);
			}
			//
			GL11.glPopMatrix();
			//SAT debugging

		}
	}

	public static long getMillis() {
		return System.nanoTime()/1000000;
	}

}


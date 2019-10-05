package graphics;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GraphicRectLoader {
	
	public static Texture initTex(String textureName){
		Texture tex = null;
		String path = "res/textures/"+textureName+".png";
		try {
			tex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
			tex.setTextureFilter(GL11.GL_NEAREST);
		} catch (IOException e) {
			System.out.println("Texture:" + textureName + ".png not found");
			e.printStackTrace();
		}
		return tex;
	}

	public static GraphicRect load(String path) {
		GraphicRect g = null;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//read line by line
		while(scanner.hasNextLine()){
		    //process each line
		    String line = scanner.nextLine();
		    String[]object = line.split(",");
		    g = new GraphicRect(new Vector2f(Float.parseFloat(object[0]),Float.parseFloat(object[1])),
		    					new Vector2f(Float.parseFloat(object[2]),Float.parseFloat(object[3])),
		    					object[4]); //name
		}
		return g;
	}
}

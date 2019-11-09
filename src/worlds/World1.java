package worlds;


import org.lwjgl.util.vector.Vector2f;
import main.Main;

public class World1 extends World{

	public World1() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void updateInput(String inputLine) {
		int[] inputs = getInput(inputLine);
		switch(inputs[0]){
			case 1:
				if(inputs[1] == -1){
					Main.player.setVel(new Vector2f(-10,0));
				} else if(inputs[1] == 1){
					Main.player.setVel(new Vector2f(10,0));
				} else { //0
					Main.player.setVel(new Vector2f(0,0));
				}
				break;
			case 2:
				if(inputs[1] == -1){
					Main.player.setVel(new Vector2f(0,10));
				} else if(inputs[1] == 1){
					Main.player.setVel(new Vector2f(0,-10));
				} else { //0
					Main.player.setVel(new Vector2f(0,0));
				}
		}	
	}

}

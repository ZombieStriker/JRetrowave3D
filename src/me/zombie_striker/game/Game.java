package me.zombie_striker.game;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.geometry.Cube;
import me.zombie_striker.game.engine.geometry.Prisim;
import me.zombie_striker.game.engine.geometry.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game {


	private World world = new World();


	public void init(){
		Sprite triangle = null;
		try {
			triangle = new Sprite(new Location(0,0,10), ImageIO.read(getClass().getResourceAsStream("/textures/test.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}


		Cube cube = new Cube(new Location(0,0,20),2,2,2);
		world.toRender.add(cube);
		Prisim prism = new Prisim(new Location(8,0,20),2,2,2);
		world.toRender.add(prism);

		//world.toRender.add(triangle);
	}
	public void tick() {
	}
	public void render(){
		BufferedImage screen = new BufferedImage(Main.getWindow().getWidth(),Main.getWindow().getHeight(),BufferedImage.TYPE_INT_ARGB);
		world.render(screen);
		Main.getWindow().getDisplay().setDisplay(screen);
	}

	public World getWorld(){
		return world;
	}
}

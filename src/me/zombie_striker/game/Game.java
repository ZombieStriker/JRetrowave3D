package me.zombie_striker.game;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.BlockFace;
import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.geometry.Cube;
import me.zombie_striker.game.engine.geometry.Prisim;
import me.zombie_striker.game.engine.geometry.Sprite;
import me.zombie_striker.game.engine.geometry.WeirdPolygon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game {

	private int map[][] ={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{}
	};

	private int spawnX=map.length/2;
	private int spawnY = map.length/2;

	private World world = new World(spawnX,spawnY);


	public void init(){
		Sprite triangle = null;
		try {
			triangle = new Sprite(new Location(0,0,10), ImageIO.read(getClass().getResourceAsStream("/textures/test.png")), 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		world.toRender.add(triangle);

		for(int y = 0; y < map.length; y++){
			for(int x = 0; x < map[y].length; x++){
				int data = map[y][x];
				switch (data){
					case 1:
						Cube cube = new Cube(new Location(x,0,y),1,2,1);
						if(x < 1 || map[y][x-1]==1){
							cube.setHiddenFaces(BlockFace.FRONT,true);
						}
						if(x >=map[y].length-1 || map[y][x+1]==1){
							cube.setHiddenFaces(BlockFace.BACK,true);
						}
						if(y < 1||map[y-1][x]==1){
							cube.setHiddenFaces(BlockFace.LEFT,true);
						}
						if(y >= (map.length-2) ||map[y+1][x]==1){
							cube.setHiddenFaces(BlockFace.RIGHT,true);
						}
						world.toRender.add(cube);
						break;
				}
			}
		}


		Cube cube = new Cube(new Location(0,0,20),2,2,2);
		world.toRender.add(cube);
		Prisim prism = new Prisim(new Location(8,0,20),2,2,2);
		world.toRender.add(prism);
		WeirdPolygon wp = new WeirdPolygon(new Location(4,0,20),2,2,2);
		world.toRender.add(wp);

		//world.toRender.add(triangle);
	}
	public boolean forward = false;
	public boolean backwards = false;
	public boolean left = false;
	public boolean right = false;
	public boolean turn_right = false;
	public boolean turn_left = false;
	public boolean turn_up=false;
	public boolean turn_down=false;
	public void tick() {

		double cos = Math.cos(Math.toRadians(Main.game.getWorld().camera.getYaw()));
		double sin = Math.sin(Math.toRadians(Main.game.getWorld().camera.getYaw()));

		float k = 0.25F;// Move distance
		float rotation = 1;

		float xzLength =  (k);
		float dx = (float) (xzLength * sin);
		float dz = (float) (xzLength * cos);

		if(left){
			Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX()-dz);
			if(Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(),0.25)!=null) {
				Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX()+dz);
			}
			Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ()-dx);
			if(Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(),0.25)!=null) {
				Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ()+dx);
			}
		}
		if(right){
			Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX()+dz);
			if(Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(),0.25)!=null) {
				Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX() - dz);
			}
			Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ()+dx);
			if(Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(),0.25)!=null) {
				Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ() - dx);
			}
		}
		if(forward){
			Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ()+dz);
			if(Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(),0.25)!=null){
				Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ()-dz);
			}
			Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX()-dx);
			if(Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(),0.25)!=null){
				Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX()+dx);
			}
		}
		if(backwards){
			Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ()-dz);
			if(Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(),0.25)!=null){
				Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ()+dz);
			}
			Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX()+dx);
			if(Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(),0.25)!=null){
				Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX()-dx);
			}
		}
		if(turn_left){
			Main.game.getWorld().camera.setYaw((int)Main.game.getWorld().camera.getYaw()+rotation);
		}
		if(turn_right){
			Main.game.getWorld().camera.setYaw((int)Main.game.getWorld().camera.getYaw()-rotation);
		}

		if(turn_up){
			Main.game.getWorld().camera.setPitch((int)Main.game.getWorld().camera.getPitch()+rotation);
		}
		if(turn_down){
			Main.game.getWorld().camera.setPitch((int)Main.game.getWorld().camera.getPitch()-rotation);
		}



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

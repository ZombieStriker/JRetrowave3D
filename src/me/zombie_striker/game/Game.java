package me.zombie_striker.game;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.BlockFace;
import me.zombie_striker.game.engine.data.Vector3D;
import me.zombie_striker.game.engine.geometry.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game {

	public boolean k = false;
	public boolean i = false;
	public boolean forward = false;
	public boolean backwards = false;
	public boolean left = false;
	public boolean right = false;
	public boolean turn_right = false;
	public boolean turn_left = false;
	public boolean turn_up = false;
	public boolean turn_down = false;
	public boolean j = false;
	public boolean l = false;
	private final int[][] map = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{}
	};
	private final int spawnX = 0;//map.length/2;
	private final int spawnY = 0;//map.length/2;
	private final World world = new World(spawnX, spawnY);

	public void init() {
		try {
			/*Sprite triangle  = new Sprite(new Location(0,1,10), ImageIO.read(getClass().getResourceAsStream("/textures/test.png")), 7);
			world.toRender.add(triangle);
			Sprite triangle2  = new Sprite(new Location(0,1,-10), ImageIO.read(getClass().getResourceAsStream("/textures/test.png")), 7);
			world.toRender.add(triangle2);
			Sprite triangle3  = new Sprite(new Location(10,1,0), ImageIO.read(getClass().getResourceAsStream("/textures/test.png")), 7);
			world.toRender.add(triangle3);
			Sprite triangle4  = new Sprite(new Location(-10,1,0), ImageIO.read(getClass().getResourceAsStream("/textures/test.png")), 7);
			world.toRender.add(triangle4);*/
			FlatPlane plane = new FlatPlane(new Vector3D(0, 2, 10), ImageIO.read(getClass().getResourceAsStream("/textures/test.png")), 1,1);
			world.toRender.add(plane);
			FlatPlane floor = new FlatPlane(new Vector3D(0, 0, 0), ImageIO.read(getClass().getResourceAsStream("/textures/test.png")), 10,10);
			floor.setPitch(Math.toRadians(90));
			world.toRender.add(floor);
			//SquareFlat floor = new SquareFlat(new Vector3D(0, 0, 0), ImageIO.read(getClass().getResourceAsStream("/textures/test.png")), 10);
			//world.toRender.add(floor);
			//Sprite sprite = new Sprite(new Vector3D(4, 0, 0), ImageIO.read(getClass().getResourceAsStream("/textures/test.png")), 1);
			//world.toRender.add(sprite);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Cube cube2 = new Cube(new Vector3D(0, 0, 20), 2, 2, 2);
		world.toRender.add(cube2);
		Prisim prism2 = new Prisim(new Vector3D(10, 0, 20), 2, 2, 2);
		world.toRender.add(prism2);
		if (true)
			return;

		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				int data = map[y][x];
				switch (data) {
					case 1:
						Cube cube = new Cube(new Vector3D(x, 0, y), 1, 2, 1);
						if (x < 1 || map[y][x - 1] == 1) {
							cube.setHiddenFaces(BlockFace.FRONT, true);
						}
						if (x >= map[y].length - 1 || map[y][x + 1] == 1) {
							cube.setHiddenFaces(BlockFace.BACK, true);
						}
						if (y < 1 || map[y - 1][x] == 1) {
							cube.setHiddenFaces(BlockFace.LEFT, true);
						}
						if (y >= (map.length - 2) || map[y + 1][x] == 1) {
							cube.setHiddenFaces(BlockFace.RIGHT, true);
						}
						world.toRender.add(cube);
						break;
				}
			}
		}


		Prisim prism = new Prisim(new Vector3D(8, 0, 20), 2, 2, 2);
		world.toRender.add(prism);
		WeirdPolygon wp = new WeirdPolygon(new Vector3D(4, 0, 20), 2, 2, 2);
		world.toRender.add(wp);

		//world.toRender.add(triangle);
	}

	public void tick() {

		double cos = Math.cos(Math.toRadians(Main.game.getWorld().camera.getYaw()));
		double sin = Math.sin(Math.toRadians(Main.game.getWorld().camera.getYaw()));

		float k = 0.25F;// Move distance
		float rotation = 1;

		float xzLength = (k);
		float dx = (float) (xzLength* sin);
		float dz = (float) (xzLength* cos);

		if (left) {
			Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX() - dz);
			if (Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(), 0.25) != null) {
				Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX() + dz);
			}
			Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ() - dx);
			if (Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(), 0.25) != null) {
				Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ() + dx);
			}
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.updateTriangles();
			}
		}
		if (right) {
			Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX() + dz);
			if (Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(), 0.25) != null) {
				Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX() - dz);
			}
			Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ() + dx);
			if (Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(), 0.25) != null) {
				Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ() - dx);
			}
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.updateTriangles();
			}
		}
		if (forward) {
			Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ() + dz);
			if (Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(), 0.25) != null) {
				Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ() - dz);
			}
			Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX() - dx);
			if (Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(), 0.25) != null) {
				Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX() + dx);
			}
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.updateTriangles();
			}
		}
		if (backwards) {
			Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ() - dz);
			if (Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(), 0.25) != null) {
				Main.game.getWorld().camera.getLocation().setZ(Main.game.getWorld().camera.getLocation().getZ() + dz);
			}
			Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX() + dx);
			if (Main.game.world.collidesWith(Main.game.getWorld().camera.getLocation(), 0.25) != null) {
				Main.game.getWorld().camera.getLocation().setX(Main.game.getWorld().camera.getLocation().getX() - dx);
			}
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.updateTriangles();
			}
		}

		if (turn_left) {
			Main.game.getWorld().camera.setYaw((int) Main.game.getWorld().camera.getYaw() + rotation);
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.updateTriangles();
			}
		}
		if (turn_right) {
			Main.game.getWorld().camera.setYaw((int) Main.game.getWorld().camera.getYaw() - rotation);
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.updateTriangles();
			}
		}

		if (turn_up) {
			Main.game.getWorld().camera.setPitch((int) Main.game.getWorld().camera.getPitch() - rotation);
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.updateTriangles();
			}
		}
		if (turn_down) {
			Main.game.getWorld().camera.setPitch((int) Main.game.getWorld().camera.getPitch() + rotation);
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.updateTriangles();
			}
		}

		if (j) {
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.setYaw(renderableObject.getYaw() + Math.toRadians(rotation));
			}
		}

		if (l) {
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.setYaw(renderableObject.getYaw() - Math.toRadians(rotation));
			}
		}

		if (i) {
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.setPitch(renderableObject.getPitch() + Math.toRadians(rotation));
			}
		}

		if (this.k) {
			for (RenderableObject renderableObject : world.toRender) {
				renderableObject.setPitch(renderableObject.getPitch() - Math.toRadians(rotation));
			}
		}
	}

	public void render() {
		BufferedImage screen = new BufferedImage(Main.getWindow().getWidth(), Main.getWindow().getHeight(), BufferedImage.TYPE_INT_ARGB);
		world.render(screen);
		Main.getWindow().getDisplay().setDisplay(screen);
	}

	public World getWorld() {
		return world;
	}
}

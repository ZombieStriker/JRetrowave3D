package me.zombie_striker.game;

import me.zombie_striker.game.objects.physics.CollidableBlock;
import me.zombie_striker.game.objects.physics.TestCube;
import me.zombie_striker.jretrowave3d.GameEngine;
import me.zombie_striker.jretrowave3d.TickManager;
import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Light;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.geometry.Cube;
import me.zombie_striker.jretrowave3d.geometry.Floor;
import me.zombie_striker.jretrowave3d.geometry.PolyCylinder;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;

import java.awt.image.BufferedImage;

public class Game implements GameEngine {

	private final int spawnX = 500;//map.length/2;
	private final int spawnY = 500;//map.length/2;
	private final World world = new World(spawnX, spawnY);
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
	public boolean crouch = false;
	public boolean jump = false;
	public boolean escaped = false;

	private Light headlamp = new Light(new Vector3D(spawnX,1,spawnY),4);

	public void init() {
		Light light = new Light(new Vector3D(500, 5, 500), 3);
		world.registerLight(light);
		Light light2 = new Light(new Vector3D(500, 5, 516), 5);
		world.registerLight(light2);
		world.registerLight(headlamp);

		//Floor floor = new Floor(new Vector3D(500, 0, 500), 10, 10);
		//world.registerObjectToRender(floor);
		//Floor cieling = new Floor(new Vector3D(495, 4, 495), 10, 10);
		//world.registerObjectToRender(cieling);
		TestCube testCube = new TestCube(getWorld(),new Vector3D(505,10,505),1,1,1);
		CollidableBlock collidableBlock = new CollidableBlock(getWorld(),new Vector3D(480,-1,480),40,1,40);
		PolyCylinder cylinder = new PolyCylinder(new Vector3D(500, 0, 506), 20, 2, 2, 2);
		world.registerObjectToRender(cylinder);
		PolyCylinder cylinder2 = new PolyCylinder(new Vector3D(500, 0, 500 - 6), 20, 2, 2, 2);
		world.registerObjectToRender(cylinder2);
		PolyCylinder cylinder3 = new PolyCylinder(new Vector3D(506, 0, 500), 20, 2, 2, 2);
		world.registerObjectToRender(cylinder3);
		PolyCylinder cylinder4 = new PolyCylinder(new Vector3D(500 - 6, 0, 500), 20, 2, 2, 2);
		world.registerObjectToRender(cylinder4);
		Cube cube2 = new Cube(new Vector3D(500, 0, 520), 2, 2, 2);
		world.registerObjectToRender(cube2);
		if (true)
			return;
	}

	public void tick() {

		headlamp.teleport(world.camera.getLocation());


		double cos = Math.cos(getWorld().camera.getYawRadians());
		double sin = Math.sin(getWorld().camera.getYawRadians());

		float k = 0.09F;// Move distance
		float rotation = 1;

		float xzLength = (k);
		float dx = (float) (xzLength * sin);
		float dz = (float) (xzLength * cos);

		if (left) {
			getWorld().camera.getLocation().setX(getWorld().camera.getLocation().getX() - dz);
			if (world.renderCollidesWith(getWorld().camera.getLocation(), 0.25) != null) {
				getWorld().camera.getLocation().setX(getWorld().camera.getLocation().getX() + dz);
			}
			getWorld().camera.getLocation().setZ(getWorld().camera.getLocation().getZ() - dx);
			if (world.renderCollidesWith(getWorld().camera.getLocation(), 0.25) != null) {
				getWorld().camera.getLocation().setZ(getWorld().camera.getLocation().getZ() + dx);
			}
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
		}
		if (right) {
			getWorld().camera.getLocation().setX(getWorld().camera.getLocation().getX() + dz);
			if (world.renderCollidesWith(getWorld().camera.getLocation(), 0.25) != null) {
				getWorld().camera.getLocation().setX(getWorld().camera.getLocation().getX() - dz);
			}
			getWorld().camera.getLocation().setZ(getWorld().camera.getLocation().getZ() + dx);
			if (world.renderCollidesWith(getWorld().camera.getLocation(), 0.25) != null) {
				getWorld().camera.getLocation().setZ(getWorld().camera.getLocation().getZ() - dx);
			}
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
		}
		if (forward) {
			getWorld().camera.getLocation().setZ(getWorld().camera.getLocation().getZ() + dz);
			if (world.renderCollidesWith(getWorld().camera.getLocation(), 0.25) != null) {
				getWorld().camera.getLocation().setZ(getWorld().camera.getLocation().getZ() - dz);
			}
			getWorld().camera.getLocation().setX(getWorld().camera.getLocation().getX() - dx);
			if (world.renderCollidesWith(getWorld().camera.getLocation(), 0.25) != null) {
				getWorld().camera.getLocation().setX(getWorld().camera.getLocation().getX() + dx);
			}
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
		}
		if (backwards) {
			getWorld().camera.getLocation().setZ(getWorld().camera.getLocation().getZ() - dz);
			if (world.renderCollidesWith(getWorld().camera.getLocation(), 0.25) != null) {
				getWorld().camera.getLocation().setZ(getWorld().camera.getLocation().getZ() + dz);
			}
			getWorld().camera.getLocation().setX(getWorld().camera.getLocation().getX() + dx);
			if (world.renderCollidesWith(getWorld().camera.getLocation(), 0.25) != null) {
				getWorld().camera.getLocation().setX(getWorld().camera.getLocation().getX() - dx);
			}
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
		}

		if (jump) {
			getWorld().camera.getLocation().setY(getWorld().camera.getLocation().getY() + k);
			if (world.renderCollidesWith(getWorld().camera.getLocation(), 0.25) != null) {
				getWorld().camera.getLocation().setY(getWorld().camera.getLocation().getY() - k);
			}
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
		}
		if (crouch) {
			getWorld().camera.getLocation().setY(getWorld().camera.getLocation().getY() - k);
			if (world.renderCollidesWith(getWorld().camera.getLocation(), 0.25) != null) {
				getWorld().camera.getLocation().setY(getWorld().camera.getLocation().getY() + k);
			}
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
		}
		if (turn_left) {
			getWorld().camera.setYaw((int) getWorld().camera.getYaw() + rotation);
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
		}
		if (turn_right) {
			getWorld().camera.setYaw((int) getWorld().camera.getYaw() - rotation);
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
		}

		if (turn_up) {
			getWorld().camera.setPitch((int) getWorld().camera.getPitch() - rotation);
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
		}
		if (turn_down) {
			getWorld().camera.setPitch((int) getWorld().camera.getPitch() + rotation);
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
		}

		if (j) {
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.setYaw(renderableObject.getYaw() + Math.toRadians(rotation));
			}
		}

		if (l) {
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.setYaw(renderableObject.getYaw() - Math.toRadians(rotation));
			}
		}

		if (i) {
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.setPitch(renderableObject.getPitch() + Math.toRadians(rotation));
			}
		}

		if (this.k) {
			for (RenderableObject renderableObject : world.getRenderableObjects()) {
				renderableObject.setPitch(renderableObject.getPitch() - Math.toRadians(rotation));
			}
		}

		TickManager.tick();
	}


	public World getWorld() {
		return world;
	}

	public boolean isEscaped() {
		return escaped;
	}
}

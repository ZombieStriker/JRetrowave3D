package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.data.Plane;
import me.zombie_striker.game.engine.data.Triangle;
import me.zombie_striker.game.engine.utils.MathUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite implements RenderableObject {

	public static final int SCALAR = 100;

	private Location center;
	private int size;

	private BufferedImage bi ;

	public Sprite(Location center, BufferedImage bi, int size) {
		this.bi = bi;
		this.center = center;
		this.size = size;
	}

	@Override
	public Triangle[] getObjectsToRender(World world) {
		Triangle[] ts = new Triangle[2];
		double dx = Math.cos(Math.toRadians(world.camera.getYaw()));
		double dz = Math.sin(Math.toRadians(world.camera.getYaw()));
		ts[0] = new Triangle(new Location(center.getX()+dx,center.getY(),center.getZ()+dz),
				new Location(center.getX()-dx,center.getY(),center.getZ()-dz),
				new Location(center.getX()+dx,center.getY()+size,center.getZ()+dz)
		,new Color(0,0,0));
		ts[1] = new Triangle(new Location(center.getX()+dx,center.getY()+size,center.getZ()+dz),
				new Location(center.getX()-dx,center.getY(),center.getZ()-dz),
				new Location(center.getX()-dx,center.getY()+size,center.getZ()-dz)
				,new Color(11, 14, 78));
		return ts;
	}

	@Override
	public boolean isInside(Location location,double size) {
		return MathUtil.distanceSquared(location,center) <= (2*size)*(2*size);
	}
}

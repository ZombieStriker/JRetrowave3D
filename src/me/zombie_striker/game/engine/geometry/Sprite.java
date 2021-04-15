package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.data.Plane;

import java.awt.image.BufferedImage;

public class Sprite implements RenderableObject {

	public static final int SCALAR = 100;

	private Plane[] plane = new Plane[1];

	private BufferedImage bi ;

	public Sprite(Location center, BufferedImage bi) {
		this.bi = bi;
		plane[0] = new Plane(new Location(center.getX(), center.getY(), center.getZ()), new Location(center.getX()+bi.getWidth(), center.getY()+(bi.getHeight()*SCALAR), center.getZ()));
		plane[0].setTexture(bi);
	}

	@Override
	public Plane[] getObjectsToRender(World world) {
		return plane;
	}
}

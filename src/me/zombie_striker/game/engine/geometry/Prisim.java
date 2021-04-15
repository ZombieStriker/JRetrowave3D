package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.data.Plane;

public class Prisim implements RenderableObject {

	private Plane[] sides = new Plane[3];

	public Prisim(Location center, double width, double height, double length) {
		Location toprightfront = new Location(center.getX() + width, center.getY() + height, center.getZ() + length);
		Location topback = new Location(center.getX() + (width / 2), center.getY() + height, center.getZ());
		Location topleftfront = new Location(center.getX(), center.getY() + height, center.getZ() + length);

		Location bottomrightfront = new Location(center.getX() + width, center.getY(), center.getZ() + length);
		Location bottomback = new Location(center.getX() + (width / 2), center.getY(), center.getZ());
		Location bottomleftfront = new Location(center.getX(), center.getY(), center.getZ() + length);

		sides[0] = new Plane(bottomleftfront, toprightfront);//front
		sides[1] = new Plane(bottomback, toprightfront);//right
		sides[2] = new Plane(bottomback, topleftfront);//left
	}

	@Override
	public Plane[] getObjectsToRender(World world) {
		return sides;
	}
}

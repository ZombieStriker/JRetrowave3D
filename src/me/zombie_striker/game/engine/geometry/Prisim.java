package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.data.Plane;
import me.zombie_striker.game.engine.data.Triangle;

public class Prisim implements RenderableObject {

	private Triangle[] sides = new Triangle[6];

	public Prisim(Location center, double width, double height, double length) {
		Location toprightback = new Location(center.getX() + width, center.getY() + height, center.getZ() + length);
		Location topfront = new Location(center.getX() + (width / 2), center.getY() + height, center.getZ());
		Location topleftback = new Location(center.getX(), center.getY() + height, center.getZ() + length);

		Location bottomrightback = new Location(center.getX() + width, center.getY(), center.getZ() + length);
		Location bottomfront = new Location(center.getX() + (width / 2), center.getY(), center.getZ());
		Location bottomleftback = new Location(center.getX(), center.getY(), center.getZ() + length);

		sides[0] = new Triangle(topleftback,bottomleftback,bottomrightback);
		sides[1] = new Triangle(topleftback,toprightback,bottomrightback);
		sides[2] = new Triangle(topleftback,bottomleftback,bottomfront);
		sides[3] = new Triangle(topleftback,topfront,bottomfront);
		sides[4] = new Triangle(toprightback,bottomrightback,bottomfront);
		sides[5] = new Triangle(toprightback,topfront,bottomfront);

		/*sides[0] = new Plane(bottomleftfront, toprightfront);//front
		sides[1] = new Plane(bottomback, toprightfront);//right
		sides[2] = new Plane(bottomback, topleftfront);//left*/
	}

	@Override
	public Triangle[] getObjectsToRender(World world) {
		return sides;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
}

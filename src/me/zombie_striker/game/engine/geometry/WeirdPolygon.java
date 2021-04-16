package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.data.Triangle;

import java.awt.*;

public class WeirdPolygon implements RenderableObject {


	private Triangle[] sides = new Triangle[6];

	private Location bottomCorner1;
	private Location bottomCorner2;
	private Location topCorner;

	public WeirdPolygon(Location center, double width, double height, double length) {
		Location toprightback = new Location(center.getX() + width, center.getY() + height, center.getZ() + length);
		Location topback = new Location(center.getX() + (width / 2), center.getY() + height, center.getZ()+length);
		Location topleftback = new Location(center.getX(), center.getY() + height, center.getZ() + length);

		Location bottomrightback = new Location(center.getX() + width, center.getY(), center.getZ() + length);
		Location bottomfront = new Location(center.getX() + (width / 2), center.getY(), center.getZ());
		Location bottomleftback = new Location(center.getX(), center.getY(), center.getZ() + length);

		topCorner = new Location(bottomfront.getX(),toprightback.getY(),bottomfront.getZ());
		bottomCorner1 = bottomleftback;
		bottomCorner2 = bottomrightback;

		sides[0] = new Triangle(topleftback,bottomleftback,bottomrightback, new Color(255,255,255));
		sides[1] = new Triangle(topleftback,toprightback,bottomrightback, new Color(255,255,255));
		sides[2] = new Triangle(topleftback,bottomleftback,bottomfront, new Color(255,255,255));
		sides[3] = new Triangle(topleftback,topback,bottomfront, new Color(255,255,255));
		sides[4] = new Triangle(toprightback,bottomrightback,bottomfront, new Color(255,255,255));
		sides[5] = new Triangle(toprightback,topback,bottomfront, new Color(255,255,255));

		/*sides[0] = new Plane(bottomleftfront, toprightfront);//front
		sides[1] = new Plane(bottomback, toprightfront);//right
		sides[2] = new Plane(bottomback, topleftfront);//left*/
	}


	@Override
	public Triangle[] getObjectsToRender(World world) {
		return sides;
	}


	@Override
	public boolean isInside(Location location, double size) {
		if(location.getY()+size < topCorner.getY() && location.getY()-size >= bottomCorner1.getY()){
			if(location.getZ()+size < bottomCorner1.getZ() && location.getZ()-size >= bottomCorner2.getZ()) {
				if (location.getX()+size < bottomCorner1.getX() && location.getX()-size >= bottomCorner2.getX()) {
					return true;
				}
			}
		}
		return false;
	}
}

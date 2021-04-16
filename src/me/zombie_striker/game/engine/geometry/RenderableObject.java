package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.data.Plane;
import me.zombie_striker.game.engine.data.Triangle;

public interface RenderableObject{

	public Triangle[] getObjectsToRender(World world);

	public boolean isInside(Location location, double size);
}

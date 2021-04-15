package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Plane;
import me.zombie_striker.game.engine.data.Triangle;

public interface RenderableObject extends Comparable {

	public Triangle[] getObjectsToRender(World world);
}

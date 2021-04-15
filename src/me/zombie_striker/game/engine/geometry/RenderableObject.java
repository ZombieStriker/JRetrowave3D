package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Plane;

public interface RenderableObject {

	public Plane[] getObjectsToRender(World world);
}

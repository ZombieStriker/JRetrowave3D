package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Plane;

public class SpriteTriangle implements RenderableObject {
	@Override
	public Plane[] getObjectsToRender(World world) {
		return new Plane[0];
	}
}

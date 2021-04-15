package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Plane;
import me.zombie_striker.game.engine.data.Triangle;

public class SpriteTriangle implements RenderableObject {
	@Override
	public Triangle[] getObjectsToRender(World world) {
		return new Triangle[0];
	}
}
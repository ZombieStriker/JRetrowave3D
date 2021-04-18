package me.zombie_striker.jretrowave3d.physics.boundingbox;

import me.zombie_striker.jretrowave3d.data.Vector3D;

public interface BoundingBox {

	boolean collides(Vector3D point);
	boolean collides(BoundingBox box);
}

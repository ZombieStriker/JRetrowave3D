package me.zombie_striker.jretrowave3d.v1.physics.boundingbox;

import me.zombie_striker.jretrowave3d.v1.data.Vector3D;

public interface BoundingBox {

	boolean collides(Vector3D point);
	boolean collides(BoundingBox box);
	boolean collides(BoundingBox box,Vector3D point, Vector3D lastLocation);
	void teleport(Vector3D location);
	Vector3D getLocation();
}

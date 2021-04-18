package me.zombie_striker.jretrowave3d.physics.boundingbox;

import me.zombie_striker.jretrowave3d.data.Vector3D;

public class SphereBoundingBox implements BoundingBox{

	private Vector3D center;
	private double distance;

	public SphereBoundingBox(Vector3D center, double distance){
		this.center = center;
		this.distance = distance;
	}

	@Override
	public boolean collides(Vector3D point) {
		return center.distanceSquared(point) <= distance*distance;
	}

	@Override
	public boolean collides(BoundingBox box) {
		return false;
	}
}

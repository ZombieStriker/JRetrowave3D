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
		if(box instanceof SphereBoundingBox){
			SphereBoundingBox sphere = (SphereBoundingBox) box;
			if(center.distanceSquared(sphere.getCenter()) < (distance+sphere.getRadius())*(distance+ sphere.getRadius()))
				return true;
		}else if(box instanceof BoxBoundingBox){
			BoxBoundingBox box2 = (BoxBoundingBox) box;
			return box2.collides(this);
		}
		return false;
	}

	@Override
	public boolean collides(BoundingBox box, Vector3D point, Vector3D lastLocation) {
		return false;
	}

	public Vector3D getCenter(){
		return center;
	}
	public double getRadius(){
		return distance;
	}

	@Override
	public void teleport(Vector3D location) {
		Vector3D newCenter = new Vector3D(location);
		newCenter.add(distance,distance,distance);

	}
}

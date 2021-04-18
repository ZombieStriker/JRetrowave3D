package me.zombie_striker.jretrowave3d.physics.boundingbox;

import me.zombie_striker.jretrowave3d.data.Vector3D;

public class BoxBoundingBox implements BoundingBox{

	private Vector3D mincorder;
	private Vector3D maxCorner;

	public BoxBoundingBox(Vector3D min, Vector3D max){
		this.mincorder = min;
		this.maxCorner = max;
	}

	@Override
	public boolean collides(Vector3D point) {
		if(point.getX() < maxCorner.getX() && point.getX() > mincorder.getX())
			if(point.getZ() < maxCorner.getZ() && point.getZ() > mincorder.getZ())
				if(point.getY() < maxCorner.getY() && point.getY() > mincorder.getY())
					return true;
		return false;
	}

	@Override
	public boolean collides(BoundingBox box) {
		return false;
	}
}

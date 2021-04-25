package me.zombie_striker.jretrowave3d.v1.physics.boundingbox;

import me.zombie_striker.jretrowave3d.v1.data.Vector3D;

public class BoxBoundingBox implements BoundingBox {

	private Vector3D mincorder;
	private Vector3D maxCorner;
	private float width;
	private float height;
	private float length;

	public BoxBoundingBox(Vector3D min, Vector3D max) {
		this.mincorder = min;
		this.maxCorner = max;
		this.width = max.getX() - min.getX();
		this.height = max.getY() - min.getY();
		this.length = max.getZ() - min.getZ();
	}
	public BoxBoundingBox(Vector3D location, float width, float height, float length){
		this.mincorder = location;
		this.maxCorner = new Vector3D(location);
		this.maxCorner.add(width,height,length);
		this.width = width;
		this.height = height;
		this.length = length;
	}

    public BoxBoundingBox(Vector3D location, float size) {
		this(location,size,size,size);
    }

    @Override
	public boolean collides(Vector3D point) {
		if (point.getY() <= maxCorner.getY() && point.getY() >= mincorder.getY())
			if (point.getX() <= maxCorner.getX() && point.getX() >= mincorder.getX())
				if (point.getZ() <= maxCorner.getZ() && point.getZ() >= mincorder.getZ())
					return true;
		return false;
	}

	@Override
	public boolean collides(BoundingBox box) {
		if (box.collides(mincorder))
			return true;
		if (box.collides(new Vector3D(mincorder.getX(), mincorder.getY(), maxCorner.getZ())))
			return true;
		if (box.collides(new Vector3D(maxCorner.getX(), mincorder.getY(), mincorder.getZ())))
			return true;
		if (box.collides(new Vector3D(maxCorner.getX(), mincorder.getY(), maxCorner.getZ())))
			return true;
		if (box.collides(new Vector3D(mincorder.getX(), maxCorner.getY(), mincorder.getZ())))
			return true;
		if (box.collides(new Vector3D(mincorder.getX(), maxCorner.getY(), maxCorner.getZ())))
			return true;
		if (box.collides(new Vector3D(maxCorner.getX(), maxCorner.getY(), mincorder.getZ())))
			return true;
		if (box.collides(maxCorner))
			return true;
		return false;
	}

	public boolean collides(Vector3D point, double xStretch, double yStretch, double zStretch) {
		if (point.getY() <= maxCorner.getY() + yStretch && point.getY() >= mincorder.getY())
			if (point.getX() <= maxCorner.getX() + xStretch && point.getX() >= mincorder.getX())
				if (point.getZ() <= maxCorner.getZ() + zStretch && point.getZ() >= mincorder.getZ())
					return true;
		return false;
	}

	@Override
	public boolean collides(BoundingBox box, Vector3D point, Vector3D lastLocation) {
		double difX = point.getX()-lastLocation.getX();
		double difY = point.getY()-lastLocation.getY();
		double difZ = point.getZ()-lastLocation.getZ();
		return collides(point,difX,difY,difZ);
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getLength() {
		return length;
	}

	@Override
	public void teleport(Vector3D location) {
		Vector3D dif = new Vector3D(width, height, length);
		mincorder = location;
		maxCorner = new Vector3D(location);
		maxCorner.add(dif);
	}

	@Override
	public Vector3D getLocation() {
		return mincorder;
	}
}

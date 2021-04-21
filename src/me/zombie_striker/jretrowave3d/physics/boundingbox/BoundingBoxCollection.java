package me.zombie_striker.jretrowave3d.physics.boundingbox;

import me.zombie_striker.jretrowave3d.data.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class BoundingBoxCollection implements BoundingBox {

	private List<BoundingBox> boxes = new ArrayList<>();
	private Vector3D mincorner;

	public BoundingBoxCollection(Vector3D minCorner) {
		this.mincorner = minCorner;
	}

	public void add(BoundingBox box) {
		boxes.add(box);
		updateBoundingBoxes();
	}

	public void remove(BoundingBox box) {
		boxes.remove(box);
		updateBoundingBoxes();
	}

	private void b(BoundingBox obj) {
		for (int i = 0; i < 3; i++)
			if (mincorner.getX() > obj.getLocation().getX())
				mincorner.setX(obj.getLocation().getX());


		for (int i = 0; i < 3; i++)
			if (mincorner.getY() > obj.getLocation().getY())
				mincorner.setY(obj.getLocation().getY());


		for (int i = 0; i < 3; i++)
			if (mincorner.getZ() > obj.getLocation().getZ())
				mincorner.setZ(obj.getLocation().getZ());
	}

	public void updateBoundingBoxes() {
		for (BoundingBox obj : boxes) {
			b(obj);
		}
	}

	@Override
	public boolean collides(Vector3D point) {
		for (BoundingBox b : boxes)
			if (b.collides(point))
				return true;
		return false;
	}

	@Override
	public boolean collides(BoundingBox box) {
		for (BoundingBox b : boxes)
			if (b.collides(box))
				return true;
		return false;
	}

	@Override
	public boolean collides(BoundingBox box, Vector3D point, Vector3D lastLocation) {
		Vector3D offset = new Vector3D(point);
		offset.subtract(mincorner);
		Vector3D offset2 = new Vector3D(point);
		offset2.subtract(offset);
		Vector3D offset3 = new Vector3D(lastLocation);
		offset3.subtract(offset);
		for (BoundingBox b : boxes)
			if (b.collides(box, offset2, offset3))
				return true;
		return false;
	}

	@Override
	public void teleport(Vector3D location) {
		Vector3D offset = new Vector3D(location);
		offset.subtract(mincorner);
		for (BoundingBox object : getCollection()) {
			Vector3D newLoc = new Vector3D(object.getLocation());
			newLoc.subtract(offset);
			object.teleport(newLoc);
		}
		mincorner=location;
	}

	public List<BoundingBox> getCollection() {
		return boxes;
	}

	@Override
	public Vector3D getLocation() {
		return mincorner;
	}
}

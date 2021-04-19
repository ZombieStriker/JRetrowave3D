package me.zombie_striker.jretrowave3d.physics.boundingbox;

import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public class BoundingBoxCollection implements BoundingBox {

	private List<BoundingBox> boxes = new ArrayList<>();
	private Vector3D mincorner;

	public BoundingBoxCollection(Vector3D minCorner){
		this.mincorner= minCorner;
	}
	public void add(BoundingBox box){
		boxes.add(box);
		updateBoundingBoxes();
	}
	public void remove(BoundingBox box){
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
		for(BoundingBox b : boxes)
			if(b.collides(point))
				return true;
		return false;
	}

	@Override
	public boolean collides(BoundingBox box) {
		for(BoundingBox b : boxes)
			if(b.collides(box))
				return true;
		return false;
	}

	@Override
	public boolean collides(BoundingBox box, Vector3D point, Vector3D lastLocation) {
		for(BoundingBox b : boxes)
			if(b.collides(box,point,lastLocation))
				return true;
		return false;
	}

	@Override
	public void teleport(Vector3D location) {
		Vector3D offset = new Vector3D(location);
		offset.subtract(mincorner);
		for(BoundingBox object : getCollection()){
			Vector3D newLoc = new Vector3D(object.getLocation());
			newLoc.subtract(offset);
			object.teleport(newLoc);
		}
		mincorner.subtract(offset);
	}

	public List<BoundingBox> getCollection(){
		return boxes;
	}

	@Override
	public Vector3D getLocation() {
		return mincorner;
	}
}

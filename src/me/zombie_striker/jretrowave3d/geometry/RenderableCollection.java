package me.zombie_striker.jretrowave3d.geometry;

import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class RenderableCollection extends RenderableObject {

	private List<RenderableObject> objects = new ArrayList<>();

	private Vector3D mincorner;
	private Vector3D maxCorner;

	public RenderableCollection(int maxtriangles, Vector3D startingPoint) {
		super(maxtriangles);
		mincorner = startingPoint;
		maxCorner = startingPoint;
	}

	@Override
	public Vector3D getLocation() {
		return mincorner;
	}

	public void addObject(RenderableObject object) {
		this.objects.add(object);
		updateTriangleCount();
	}

	public void removeObject(RenderableObject object) {
		this.objects.remove(object);
		updateTriangleCount();
	}

	private void b(Triangle t, boolean mincornercheck) {
		if (mincornercheck) {
			for (int i = 0; i < 3; i++)
				if (mincorner.getX() > t.getTrues()[i].getX())
					mincorner.setX(t.getTrues()[i].getX());


			for (int i = 0; i < 3; i++)
				if (mincorner.getY() > t.getTrues()[i].getY())
					mincorner.setY(t.getTrues()[i].getY());


			for (int i = 0; i < 3; i++)
				if (mincorner.getZ() > t.getTrues()[i].getZ())
					mincorner.setZ(t.getTrues()[i].getZ());
		} else {
			for (int i = 0; i < 3; i++)
				if (maxCorner.getX() < t.getTrues()[i].getX())
					maxCorner.setX(t.getTrues()[i].getX());


			for (int i = 0; i < 3; i++)
				if (maxCorner.getY() < t.getTrues()[i].getY())
					maxCorner.setY(t.getTrues()[i].getY());


			for (int i = 0; i < 3; i++)
				if (maxCorner.getZ() < t.getTrues()[i].getZ())
					maxCorner.setZ(t.getTrues()[i].getZ());

		}
	}

	public void updateTriangleCount() {
		List<Triangle> triangles = new ArrayList<>();
		for (RenderableObject obj : objects) {
			for (Triangle t : obj.getTriangles()) {
				b(t, false);
				b(t, true);

				triangles.add(t);
			}
		}
		setTriangles(triangles.toArray(new Triangle[triangles.size()]));
	}

	@Override
	public Triangle[] getTrianglesForRendering(World world) {
		return getTriangles();
	}

	@Override
	public Triangle[] getAllTriangles(World world) {
		return getTriangles();
	}
	public List<RenderableObject> getCollection(){
		return objects;
	}

	@Override
	public boolean isInside(Vector3D location, double size) {
		for(RenderableObject obj : objects){
			if(obj.isInside(location,size))
				return true;
		}
		return false;
	}

	@Override
	public Vector3D getCenter(World world) {
		return new Vector3D((maxCorner.getX() + mincorner.getX()) / 2, (maxCorner.getY() + mincorner.getY()) / 2, (maxCorner.getZ() + mincorner.getZ()) / 2);
	}

	@Override
	public double getYaw() {
		return 0;
	}

	@Override
	public void setYaw(double yaw) {

	}

	@Override
	public double getPitch() {
		return 0;
	}

	@Override
	public void setPitch(double pitch) {

	}

	@Override
	public void teleport(Vector3D location) {
		Vector3D offset = new Vector3D(location);
		offset.subtract(mincorner);
		for(RenderableObject object : getCollection()){
			Vector3D newLoc = new Vector3D(object.getLocation());
			newLoc.subtract(offset);
			object.teleport(newLoc);
		}
		mincorner.subtract(offset);
		maxCorner.subtract(offset);
	}
}

package me.zombie_striker.jretrowave3d.geometry;

import me.zombie_striker.jretrowave3d.JRetroWave3D;
import me.zombie_striker.jretrowave3d.LightManager;
import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RenderableCollection extends RenderableObject{

	private float yaw = 0;
	private float pitch = 0;
	private Vector3D bottomCorner;

	public List<RenderableObject> collection = new ArrayList<>();

	public RenderableCollection(Vector3D location, RenderableObject... renders){
		super(0);
		this.bottomCorner = location;
		for(RenderableObject r : renders){
			collection.add(r);
		}
	}

	@Override
	public Triangle[] getTriangles() {
		int triangleCount = 0;
		for(RenderableObject r : collection){
			triangleCount+=r.getTriangles().length;
		}
		Triangle[] triangles = new Triangle[triangleCount];
		int index = 0;
		for(RenderableObject r : collection){
			for(Triangle t : r.getTriangles()) {
				triangles[index] = t;
				index++;
			}
		}
		return triangles;
	}

	@Override
	public Vector3D getLocation() {
		return bottomCorner;
	}

	@Override
	public void setCalculateLightColor(boolean b) {
		for(RenderableObject r : collection){
			r.setCalculateLightColor(b);
		}
	}

	@Override
	public Triangle[] getTrianglesForRendering(World world) {
		return getTriangles();
	}

	@Override
	public Triangle[] getAllTriangles(World world) {
		return getTriangles();
	}

	@Override
	public boolean isInside(Vector3D location, float size) {
		return false;
	}

	@Override
	public Vector3D getCenter(World world) {
		return bottomCorner;
	}

	@Override
	public float getYaw() {
		return 0;
	}

	@Override
	public void setYawRadians(float yaw) {

	}

	@Override
	public float getPitch() {
		return 0;
	}

	@Override
	public void setPitchRadians(float pitch) {

	}

	@Override
	public RenderableObject clone() {
		return null;
	}

	@Override
	public void teleport(Vector3D location) {
		Vector3D offset = new Vector3D(location.getX() - bottomCorner.getX(), location.getY() - bottomCorner.getY(), location.getZ() - bottomCorner.getZ());
		for (RenderableObject r : collection) {
			Vector3D defaultOffset = bottomCorner.clone().subtract(r.getLocation());
			r.teleport(new Vector3D(bottomCorner).add(offset).subtract(defaultOffset));
		}
		bottomCorner = location;
	}

	@Override
	public RenderableObject setSize(float width, float height, float length) {
		for(RenderableObject r : collection){
			r.setSize(width,height,length);
		}
		return this;
	}

	@Override
	public RenderableObject setSize(float size) {
		for(RenderableObject r : collection){
			r.setSize(size);
		}
		return this;
	}

	public void addRender(RenderableObject render) {
		collection.add(render);
	}

	@Override
	public void updateTriangles() {
		for(RenderableObject r : collection)
			r.updateTriangles();
	}

@Override
	public void shiftTriangles(Vector3D direction) {
	for(RenderableObject r : collection)
		r.shiftTriangles(direction);
	}
}

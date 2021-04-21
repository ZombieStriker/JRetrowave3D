package me.zombie_striker.jretrowave3d.geometry.shapes;

import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;

import java.awt.*;

public class PolyCylinder extends RenderableObject {

	private Vector3D bottomCorner;
	private Vector3D topCorner;
	private float yaw = 0;
	private float pitch = 0;
	private int sides;
	private Vector3D center;

	public PolyCylinder(Vector3D center, int sides, float width, float height, float length) {
		super(sides * 2);
		Vector3D toprightfront = new Vector3D(center.getX() + width, center.getY() + height, center.getZ() + length);
		Vector3D bottomleftback = new Vector3D(center.getX(), center.getY(), center.getZ());
		this.center = center;
		this.sides = sides;
		bottomCorner = bottomleftback;
		topCorner = toprightfront;

		resetTriangles();
	}

	private void resetTriangles() {
		float width = topCorner.getX() - bottomCorner.getX();
		float height = topCorner.getY() - bottomCorner.getY();
		float length = topCorner.getZ() - bottomCorner.getZ();

		double nextTheta = Math.PI * 2 / sides;
		int index = 0;
		for (float theta = 0; theta < Math.PI * 2 + 0; theta += nextTheta) {
			float x = (float) (Math.cos(theta) * width - (Math.sin(theta) * length));
			float z = (float) (Math.cos(theta) * length + (Math.sin(theta) * width));
			float x2 = (float) (Math.cos(theta + nextTheta) * width - (Math.sin(theta + nextTheta) * length));
			float z2 = (float) (Math.cos(theta + nextTheta) * length + (Math.sin(theta + nextTheta) * width));
			Vector3D point = new Vector3D(center.getX() + x, center.getY(), center.getZ() + z);
			Vector3D point2 = new Vector3D(center.getX() + x2, center.getY(), center.getZ() + z2);
			Vector3D point3 = new Vector3D(center.getX() + x2, center.getY() + height, center.getZ() + z2);
			Triangle t = new Triangle(point3, point, point2, new Color(0, 255, 255));
			getTriangles()[index] = t;
			index++;
		}
		for (double theta = 0; theta < Math.PI * 2 + 0; theta += nextTheta) {
			float x = (float) ((Math.cos(theta) * width) - (Math.sin(theta) * length));
			float z = (float) ((Math.cos(theta) * length) + (Math.sin(theta) * width));
			float x2 = (float) ((Math.cos(theta + nextTheta) * width) - (Math.sin(theta + nextTheta) * length));
			float z2 = (float) ((Math.cos(theta + nextTheta) * length) + (Math.sin(theta + nextTheta) * width));
			Vector3D point = new Vector3D(center.getX() + x, center.getY() + height, center.getZ() + z);
			Vector3D point2 = new Vector3D(center.getX() + x, center.getY(), center.getZ() + z);
			Vector3D point3 = new Vector3D(center.getX() + x2, center.getY() + height, center.getZ() + z2);
			Triangle t = new Triangle(point3, point, point2, new Color(0, 255, 255));
			getTriangles()[index] = t;
			index++;
		}
	}

	@Override
	public Vector3D getLocation() {
		return new Vector3D(bottomCorner);
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
		return center;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
		setUpdateTrianglesWithGPU();
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
		setUpdateTrianglesWithGPU();
	}

	@Override
	public void teleport(Vector3D location) {
		Vector3D dif = new Vector3D(topCorner);
		dif.subtract(bottomCorner);
		bottomCorner = location;
		topCorner = new Vector3D(location);
		topCorner.add(dif);
		setUpdateTrianglesWithGPU();
	}

	public RenderableObject clone(){
		PolyCylinder shape = new PolyCylinder(bottomCorner,sides,topCorner.getX()-bottomCorner.getX(),topCorner.getY()-bottomCorner.getY(),topCorner.getZ()-bottomCorner.getZ());
		shape.setTriangles(getTriangles());
		shape.bottomCorner =new Vector3D(bottomCorner);
		shape.topCorner=new Vector3D(topCorner);
		return shape;
	}

	@Override
	public RenderableObject setSize(float width, float height, float length) {
		resize(topCorner, new Vector3D(bottomCorner).add(width,height,length));
		topCorner = new Vector3D(bottomCorner).add(width,height,length);
		return this;
	}

	@Override
	public RenderableObject setSize(float size) {
		return null;
	}
}

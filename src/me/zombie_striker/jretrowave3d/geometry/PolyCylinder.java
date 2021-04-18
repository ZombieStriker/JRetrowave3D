package me.zombie_striker.jretrowave3d.geometry;

import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;

import java.awt.*;

public class PolyCylinder extends RenderableObject {

	private Vector3D bottomCorner;
	private Vector3D topCorner;
	private double yaw = 0;
	private double pitch = 0;
	private Vector3D center;

	public PolyCylinder(Vector3D center, int sides, double width, double height, double length) {
		super(sides * 2);
		Vector3D toprightfront = new Vector3D(center.getX() + width, center.getY() + height, center.getZ() + length);
		Vector3D bottomleftback = new Vector3D(center.getX(), center.getY(), center.getZ());
		this.center = center;

		bottomCorner = bottomleftback;
		topCorner = toprightfront;

		double nextTheta = Math.PI * 2 / sides;
		int index = 0;
		for (double theta = 0; theta < Math.PI * 2 + 0; theta += nextTheta) {
			double x = Math.cos(theta) * width - (Math.sin(theta) * length);
			double z = Math.cos(theta) * length + (Math.sin(theta) * width);
			double x2 = Math.cos(theta + nextTheta) * width - (Math.sin(theta + nextTheta) * length);
			double z2 = Math.cos(theta + nextTheta) * length + (Math.sin(theta + nextTheta) * width);
			Vector3D point = new Vector3D(center.getX() + x, center.getY(), center.getZ() + z);
			Vector3D point2 = new Vector3D(center.getX() + x2, center.getY(), center.getZ() + z2);
			Vector3D point3 = new Vector3D(center.getX() + x2, center.getY() + height, center.getZ() + z2);
			Triangle t = new Triangle(point3, point, point2, new Color(0, 255, 255));
			getTriangles()[index] = t;
			index++;
		}
		for(double theta = 0; theta < Math.PI*2 + 0; theta+=nextTheta){
			double x = (Math.cos(theta)*width)-(Math.sin(theta)*length);
			double z = (Math.cos(theta)*length)+(Math.sin(theta)*width);
			double x2 = (Math.cos(theta+nextTheta)*width)-(Math.sin(theta+nextTheta)*length);
			double z2 = (Math.cos(theta+nextTheta)*length)+(Math.sin(theta+nextTheta)*width);
			Vector3D point = new Vector3D(center.getX()+x, center.getY()+height, center.getZ()+z);
			Vector3D point2 = new Vector3D(center.getX()+x, center.getY(), center.getZ()+z);
			Vector3D point3 = new Vector3D(center.getX()+x2, center.getY()+height, center.getZ()+z2);
			Triangle t = new Triangle(point2,point3,point, new Color(0,255,255));
			getTriangles()[index] = t;
			index++;
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
	public boolean isInside(Vector3D location, double size) {
		return false;
	}

	@Override
	public Vector3D getCenter(World world) {
		return center;
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
		updateTriangles();
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
		updateTriangles();
	}

}
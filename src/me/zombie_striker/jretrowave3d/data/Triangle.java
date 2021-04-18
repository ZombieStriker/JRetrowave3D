package me.zombie_striker.jretrowave3d.data;

import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.utils.MathUtil;

import java.awt.*;

public class Triangle {

	private Vector3D[] triangle = new Vector3D[3];
	private Vector3D[] triangle_true = new Vector3D[3];
	private Vector3D[] exactLocation = new Vector3D[3];
	private Color color;
	private Material material;


	public Triangle(Triangle t1) {
		triangle[0] = new Vector3D(t1.getRelativeLocation()[0]);
		triangle[1] = new Vector3D(t1.getRelativeLocation()[1]);
		triangle[2] = new Vector3D(t1.getRelativeLocation()[2]);
		triangle_true[0] = new Vector3D(t1.getTrues()[0]);
		triangle_true[1] = new Vector3D(t1.getTrues()[1]);
		triangle_true[2] = new Vector3D(t1.getTrues()[2]);
		exactLocation[0] = new Vector3D(t1.getExactLocation()[0]);
		exactLocation[1] = new Vector3D(t1.getExactLocation()[1]);
		exactLocation[2] = new Vector3D(t1.getExactLocation()[2]);
		this.color = new Color(t1.getColor().getRGB());
	}

	public Vector3D[] getExactLocation() {
		return exactLocation;
	}

	public Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Color color) {
		triangle[0] = v1;
		triangle[1] = v2;
		triangle[2] = v3;
		triangle_true[0] = new Vector3D(v1);
		triangle_true[1] = new Vector3D(v2);
		triangle_true[2] = new Vector3D(v3);
		exactLocation[0] = new Vector3D(v1);
		exactLocation[1] = new Vector3D(v2);
		exactLocation[2] = new Vector3D(v3);
		this.color = color;
	}

	public Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Material texture) {
		triangle[0] = v1;
		triangle[1] = v2;
		triangle[2] = v3;
		triangle_true[0] = new Vector3D(v1);
		triangle_true[1] = new Vector3D(v2);
		triangle_true[2] = new Vector3D(v3);
		exactLocation[0] = new Vector3D(v1);
		exactLocation[1] = new Vector3D(v2);
		exactLocation[2] = new Vector3D(v3);
		this.material = texture;
	}

	public void subtract(double x, double y, double z) {
		for (Vector3D v3 : triangle) {
			v3.setX(v3.getX() - x);
			v3.setY(v3.getY() - y);
			v3.setZ(v3.getZ() - z);
		}
		for (Vector3D v3 : triangle_true) {
			v3.setX(v3.getX() - x);
			v3.setY(v3.getY() - y);
			v3.setZ(v3.getZ() - z);
		}
	}

	public void add(double x, double y, double z) {
		for (Vector3D v3 : triangle) {
			v3.setX(v3.getX() + x);
			v3.setY(v3.getY() + y);
			v3.setZ(v3.getZ() + z);
		}
		for (Vector3D v3 : triangle_true) {
			v3.setX(v3.getX() + x);
			v3.setY(v3.getY() + y);
			v3.setZ(v3.getZ() + z);
		}
	}

	public Vector3D getNormal(boolean getTrueVertexes) {
		Vector3D u;
		Vector3D v;
		if (getTrueVertexes) {
			v = new Vector3D(getTrues()[2].getX() - getTrues()[0].getX(), getTrues()[2].getY() - getTrues()[0].getY(), getTrues()[2].getZ() - getTrues()[0].getZ());
			u = new Vector3D(getTrues()[1].getX() - getTrues()[0].getX(), getTrues()[1].getY() - getTrues()[0].getY(), getTrues()[1].getZ() - getTrues()[0].getZ());
		}else{
			v = new Vector3D(getRelativeLocation()[2].getX() - getRelativeLocation()[0].getX(), getRelativeLocation()[2].getY() - getRelativeLocation()[0].getY(), getRelativeLocation()[2].getZ() - getRelativeLocation()[0].getZ());
			u = new Vector3D(getRelativeLocation()[1].getX() - getRelativeLocation()[0].getX(), getRelativeLocation()[1].getY() - getRelativeLocation()[0].getY(), getRelativeLocation()[1].getZ() - getRelativeLocation()[0].getZ());

		}

		double normalx = (u.getY() * v.getZ()) - (u.getZ() * v.getY());
		double normaly = (u.getZ() * v.getX()) - (u.getX() * v.getZ());
		double normalz = (u.getX() * v.getY()) - (u.getY() * v.getX());

		Vector3D normal = new Vector3D(normalx, normaly, normalz);
		return normal;
	}

	public Material getMaterial() {
		return material;
	}

	public Color getColor() {
		return color;
	}

	public Vector3D[] getPoints() {
		return triangle;
	}

	public double getAverageDistanceSquared(World world) {
		double dis = 0;
		dis += MathUtil.distanceSquared(triangle[0], world.camera.getLocation());
		dis += MathUtil.distanceSquared(triangle[1], world.camera.getLocation());
		dis += MathUtil.distanceSquared(triangle[2], world.camera.getLocation());
		return dis / 3;
	}

	public double getAverageDistance(World world) {
		double dis = 0;
		dis += MathUtil.distanceSquared(triangle[0], world.camera.getLocation());
		dis += MathUtil.distanceSquared(triangle[1], world.camera.getLocation());
		dis += MathUtil.distanceSquared(triangle[2], world.camera.getLocation());
		return Math.sqrt(dis) / 3;
	}

	public Vector3D[] getTrues() {
		return triangle_true;
	}

	public Vector3D[] getRelativeLocation() {
		return triangle;
	}

	public Double getFurthestDistance(World world) {
		double dis = MathUtil.distanceSquared(triangle[0], world.camera.getLocation());
		double temp = MathUtil.distanceSquared(triangle[1], world.camera.getLocation());
		if (temp > dis)
			dis = temp;
		temp = MathUtil.distanceSquared(triangle[2], world.camera.getLocation());
		if (temp > dis)
			dis = temp;
		return Math.sqrt(dis);
	}

	public Double getClosestDistance(World world) {
		double dis = MathUtil.distanceSquared(triangle[0], world.camera.getLocation());
		double temp = MathUtil.distanceSquared(triangle[1], world.camera.getLocation());
		if (temp < dis)
			dis = temp;
		temp = MathUtil.distanceSquared(triangle[2], world.camera.getLocation());
		if (temp < dis)
			dis = temp;
		return Math.sqrt(dis);
	}

	public Vector3D getCenter() {
		double maxheight = Math.max(exactLocation[0].getY(),exactLocation[1].getY());
		maxheight = Math.max(maxheight,exactLocation[2].getY());
		double minheight = Math.min(exactLocation[0].getY(),exactLocation[1].getY());
		minheight = Math.min(minheight,exactLocation[2].getY());

		double maxwidth = Math.max(exactLocation[0].getX(),exactLocation[1].getX());
		maxwidth = Math.max(maxwidth,exactLocation[2].getX());
		double minwidth = Math.min(exactLocation[0].getX(),exactLocation[1].getX());
		minwidth = Math.min(minwidth,exactLocation[2].getX());


		double maxlength = Math.max(exactLocation[0].getZ(),exactLocation[1].getZ());
		maxlength = Math.max(maxlength,exactLocation[2].getZ());
		double minlength = Math.min(exactLocation[0].getZ(),exactLocation[1].getZ());
		minlength = Math.min(minlength,exactLocation[2].getZ());

		return new Vector3D((maxwidth+minwidth)/2,(maxheight+minheight)/2,(maxlength+minlength)/2);
	}
}

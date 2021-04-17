package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.Main;
import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Triangle;
import me.zombie_striker.game.engine.data.Vector3D;
import me.zombie_striker.game.engine.utils.MathUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlatPlane extends RenderableObject {

	public static final int SCALAR = 100;

	private Vector3D center;
	private int size;

	private BufferedImage bi;

	private double yaw = 0;
	private double pitch = 0;

	public FlatPlane(Vector3D center, BufferedImage bi, int height, int size) {
		super(2);
		Vector3D leftback = new Vector3D(center.getX() - size, center.getY(), center.getZ());
		Vector3D rightback = new Vector3D(center.getX() + size, center.getY(), center.getZ());
		Vector3D leftfront = new Vector3D(center.getX() - size, center.getY()+height, center.getZ());
		Vector3D rightfront = new Vector3D(center.getX() + size, center.getY()+height, center.getZ());
		getTriangles()[0] = new Triangle(leftback, rightback, leftfront, new Color(6, 18, 78)); //front bottom
		getTriangles()[1] = new Triangle(rightback, rightfront, leftfront, new Color(241, 135, 6)); //back bottom
		this.bi = bi;
		this.center = center;
		this.size = size;
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
	}

	@Override
	public Vector3D getCenter(World world) {
		return center;
	}

	@Override
	public Triangle[] getTrianglesForRendering(World world) {
		Triangle[] ts = getAllTrianglesRelativeToPlayer(world);
		updateTriangles(ts);
		return ts;
	}

	@Override
	public boolean isInside(Vector3D location, double size) {
		return MathUtil.distanceSquared(location, center) <= (2 * size) * (2 * size) && location.getY() < center.getY() + size && location.getY() >= center.getY();
	}

	@Override
	public Triangle[] getAllTriangles(World world) {
		return getAllTrianglesRelativeToPlayer(world);
	}

	public Triangle[] getAllTrianglesRelativeToPlayer(World world) {
		Triangle[] ts = new Triangle[2];
		double dx = Math.cos(yaw) - Math.sin(yaw);
		double dz = Math.sin(yaw) + Math.cos(yaw);
		ts[0] = new Triangle(getTriangles()[0]/*new Vector3D(center.getX() + dx, center.getY(), center.getZ() + dz),
				new Vector3D(center.getX() - dx, center.getY(), center.getZ() - dz),
				new Vector3D(center.getX() + dx, center.getY() + size, center.getZ() + dz)
				, new Color(0, 0, 0)*/);
		ts[1] = new Triangle(getTriangles()[1]/*new Vector3D(center.getX() + dx, center.getY() + size, center.getZ() + dz),
				new Vector3D(center.getX() - dx, center.getY(), center.getZ() - dz),
				new Vector3D(center.getX() - dx, center.getY() + size, center.getZ() - dz)
				, new Color(240, 10, 246)*/);
		return ts;
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

	@Override
	public void updateTriangles() {
	}

	public void updateTriangles(Triangle[] array) {
		for (Triangle t : array) {
			for (int i = 0; i < t.getTrues().length; i++) {
				t.getVertexes()[i] = new Vector3D(t.getTrues()[i]);
				t.getVertexes()[i].rotatePitch(getPitch(), getCenter(Main.game.getWorld()));
				t.getVertexes()[i].rotateYaw(getYaw(), getCenter(Main.game.getWorld()));
				t.getVertexes()[i].rotateYaw(-Main.game.getWorld().camera.getYawRadians(), Main.game.getWorld().camera.getLocation());
				t.getVertexes()[i].rotatePitch(Main.game.getWorld().camera.getPitchRadians(), Main.game.getWorld().camera.getLocation());
			}
		}
	}

}

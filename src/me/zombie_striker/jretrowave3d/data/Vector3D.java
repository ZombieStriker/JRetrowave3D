package me.zombie_striker.jretrowave3d.data;

public class Vector3D {

	private double x;
	private double y;
	private double z;

	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3D(Vector3D vector3D) {
		this.x = vector3D.getX();
		this.y = vector3D.getY();
		this.z = vector3D.getZ();
	}

	public static boolean within(Vector3D mincorner, Vector3D maxCorner, Vector3D location) {
		if (mincorner.getX() <= location.getX() && maxCorner.getX() >= location.getX()) {
			if (mincorner.getY() <= location.getY() && maxCorner.getY() >= location.getY()) {
				if (mincorner.getZ() <= location.getZ() && maxCorner.getZ() >= location.getZ()) {
					return true;
				}
			}
		}
		return false;
	}


	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public Vector3D multiply(double d) {
		x *= d;
		y *= d;
		z *= d;
		return this;
	}


	public void rotateYaw(double yawRadians, Vector3D center) {


		double x1 = getX() - center.getX();
		double z1 = getZ() - center.getZ();

		double cos = Math.cos(yawRadians);
		double sin = Math.sin(yawRadians);

		setX(center.getX() + ((x1 * cos) - (z1 * sin)));
		setZ(center.getZ() + ((z1 * cos) + (x1 * sin)));
	}

	public void rotatePitch(double pitchRadians, Vector3D center) {


		double y1 = getY() - center.getY();
		double z1 = getZ() - center.getZ();

		double cos = Math.cos(pitchRadians);
		double sin = Math.sin(pitchRadians);

		setY(center.getY() + ((y1 * cos) + (z1 * sin)));
		setZ(center.getZ() + ((z1 * cos) - (y1 * sin)));
	}

	public double distanceSquared(Vector3D p2) {
		double x = getX() - p2.getX();
		double y = getY() - p2.getY();
		double z = getZ() - p2.getZ();
		return (x * x) + (y * y) + (z * z);
	}

	public void add(Vector3D dif) {
		this.x += dif.getX();
		this.y += dif.getY();
		this.z += dif.getZ();
	}

	public void subtract(Vector3D dif) {
		this.x -= dif.getX();
		this.y -= dif.getY();
		this.z -= dif.getZ();
	}

	public double length() {
		return Math.sqrt(distanceSquared(new Vector3D(0, 0, 0)));
	}

	public void normalize() {
		double length = length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
	}

	public void add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
}

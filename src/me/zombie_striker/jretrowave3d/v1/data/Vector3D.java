package me.zombie_striker.jretrowave3d.v1.data;

public class Vector3D {

	private float x;
	private float y;
	private float z;

	public Vector3D(float x, float y, float z) {
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


	public Vector3D clone(){
		return new Vector3D(this);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public Vector3D multiply(double d) {
		x *= d;
		y *= d;
		z *= d;
		return this;
	}


	public void rotateYaw(double yawRadians, Vector3D center) {


		float x1 = getX() - center.getX();
		float z1 = getZ() - center.getZ();

		float cos = (float) Math.cos(yawRadians);
		float sin = (float) Math.sin(yawRadians);

		setX(center.getX() + ((x1 * cos) - (z1 * sin)));
		setZ(center.getZ() + ((z1 * cos) + (x1 * sin)));
	}

	public void rotatePitch(double pitchRadians, Vector3D center) {


		float y1 = getY() - center.getY();
		float z1 = getZ() - center.getZ();

		float cos = (float) Math.cos(pitchRadians);
		float sin = (float) Math.sin(pitchRadians);

		setY(center.getY() + ((y1 * cos) + (z1 * sin)));
		setZ(center.getZ() + ((z1 * cos) - (y1 * sin)));
	}

	public double distanceSquared(Vector3D p2) {
		double x = getX() - p2.getX();
		double y = getY() - p2.getY();
		double z = getZ() - p2.getZ();
		return (x * x) + (y * y) + (z * z);
	}

	public Vector3D add(Vector3D dif) {
		this.x += dif.getX();
		this.y += dif.getY();
		this.z += dif.getZ();
		return this;
	}

	public Vector3D subtract(Vector3D dif) {
		this.x -= dif.getX();
		this.y -= dif.getY();
		this.z -= dif.getZ();
		return this;
	}

	public double length() {
		return Math.sqrt(distanceSquared(new Vector3D(0, 0, 0)));
	}

	public Vector3D normalize() {
		double length = length();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		return this;
	}

	public Vector3D add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	public Vector3D subtract(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

    public float distance(Vector3D location) {
		return (float) Math.sqrt(distanceSquared(location));
    }
}

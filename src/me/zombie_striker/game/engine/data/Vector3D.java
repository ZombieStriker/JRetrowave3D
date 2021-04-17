package me.zombie_striker.game.engine.data;

public class Vector3D {

	private double x;
	private double y;
	private double z;

	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3D(Vector3D vector3D){
		this.x = vector3D.getX();
		this.y = vector3D.getY();
		this.z = vector3D.getZ();
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
}
package me.zombie_striker.game.engine;

import me.zombie_striker.game.engine.data.Location;

public class Camera {


	private Location personLocation;
	private double yaw = 0;
	private double pitch = 0;
	private double fov = Math.toRadians(90);

	public Camera(Location personLocation) {
		this.personLocation = personLocation;
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		if (yaw < 0) {
			yaw += 360;
		}
		this.yaw = yaw % 360;
		//apatureLocation = new Location((int)(personLocation.getX()+ Math.cos(Math.toRadians(yaw))), personLocation.getY(), (int)(personLocation.getZ()+ Math.sin(Math.toRadians(yaw))));
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		if (pitch < -90) {
			pitch = -90;
		}
		if (pitch > 90) {
			pitch = 90;
		}
		this.pitch = pitch;
		//apatureLocation = new Location((int)(personLocation.getX()+ Math.cos(Math.toRadians(yaw))), personLocation.getY(), (int)(personLocation.getZ()+ Math.sin(Math.toRadians(yaw))));
	}

	public double getYawRadians() {
		return Math.toRadians(yaw);
	}

	public double getPitchRadians() {
		return Math.toRadians(pitch);
	}

	public double getFOV() {
		return fov;
	}

	public void setFOV(float f) {
		this.fov = f;
	}

	//public Location getApertureLocation() {
	//	return apatureLocation;
	//}
	public Location getLocation() {
		return personLocation;
	}
}

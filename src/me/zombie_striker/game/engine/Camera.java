package me.zombie_striker.game.engine;

import me.zombie_striker.game.engine.data.Location;

public class Camera {


	private Location apatureLocation;
	private Location personLocation;
	private float yaw = 0;
	private double fov = Math.toRadians(90);

	public Camera(Location location, Location personLocation) {
		this.apatureLocation = location;
		this.personLocation = personLocation;
	}

	public float getYaw() {
		return yaw;
	}

	public double getFOV(){
		return fov;
	}
	public void setFOV(float f){
		this.fov=f;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
		apatureLocation = new Location((int)(personLocation.getX()+ Math.cos(Math.toRadians(yaw))), personLocation.getY(), (int)(personLocation.getZ()+ Math.sin(Math.toRadians(yaw))));
	}

	public Location getApertureLocation() {
		return apatureLocation;
	}
	public Location getPersonLocation(){
		return personLocation;
	}
}

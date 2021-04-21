package me.zombie_striker.jretrowave3d.data;

import me.zombie_striker.jretrowave3d.LightManager;

public class Light {

	private Vector3D location;
	private double intensity;

	public Light(Vector3D v, double intensity){
		this.location = v;
		this.intensity = intensity;
	}
	public Vector3D getLocation(){
		return location;
	}
	public double getIntensity(){
		return intensity;
	}
	public void teleport(Vector3D location){
		boolean same = this.location.equals(location);
		this.location = location;
		if(!same)
			LightManager.updateLights();
	}
}

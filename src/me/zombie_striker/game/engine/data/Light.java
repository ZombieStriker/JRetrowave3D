package me.zombie_striker.game.engine.data;

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
}

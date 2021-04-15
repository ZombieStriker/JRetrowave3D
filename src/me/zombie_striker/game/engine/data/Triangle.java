package me.zombie_striker.game.engine.data;

public class Triangle {

	private Location2D[] triangle = new Location2D[3];
	private Location location;


	public Triangle(Location location, Location2D v1, Location2D v2, Location2D v3) {
		triangle[0]=v1;
		triangle[1]=v2;
		triangle[2]=v3;
		this.location = location;
	}

	public Location2D[] getPoints() {
		return triangle;
	}
	public Location getLocation(){
		return location;
	}
}

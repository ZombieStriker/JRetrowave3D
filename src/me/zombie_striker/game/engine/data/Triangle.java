package me.zombie_striker.game.engine.data;

public class Triangle {

	private Location[] triangle = new Location[3];


	public Triangle(Location v1, Location v2, Location v3) {
		triangle[0]=v1;
		triangle[1]=v2;
		triangle[2]=v3;
	}

	public Location[] getPoints() {
		return triangle;
	}
}

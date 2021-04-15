package me.zombie_striker.game.engine.data;


import java.awt.image.BufferedImage;

public class Plane {

	private Location[] points = new Location[2];
	private BufferedImage texture;

	public Plane(Location p1, Location p2) {
		points[0] = p1;
		points[1] = p2;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage bi) {
		this.texture = texture;
	}

	public Location getLocation() {
		return points[0];
	}
	public Location getLocation2() {
		return points[1];
	}
}

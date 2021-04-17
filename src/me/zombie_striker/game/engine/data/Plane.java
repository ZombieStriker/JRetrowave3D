package me.zombie_striker.game.engine.data;


import java.awt.image.BufferedImage;

public class Plane {

	private Vector3D[] points = new Vector3D[2];
	private BufferedImage texture;

	public Plane(Vector3D p1, Vector3D p2) {
		points[0] = p1;
		points[1] = p2;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage bi) {
		this.texture = texture;
	}

	public Vector3D getLocation() {
		return points[0];
	}
	public Vector3D getLocation2() {
		return points[1];
	}
}

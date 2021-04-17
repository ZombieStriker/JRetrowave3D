package me.zombie_striker.game.engine.data;

public class Vector2D {
	private double x;
	private double y;

	public Vector2D(double x, double y){
		this.x=x;
		this.y=y;
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

	public Vector2D multiply(double d){
		x*=d;
		y*=d;
		return this;
	}
}

package me.zombie_striker.jretrowave3d.data;

public class Vector2D {
	private float x;
	private float y;

	public Vector2D(float x, float y){
		this.x=x;
		this.y=y;
	}
	public Vector2D(Vector2D vector2D) {
		this.x = vector2D.getX();
		this.y = vector2D.getY();
	}

	public Vector2D clone(){
		return new Vector2D(this.x,this.y);
	}
	public Vector2D add(float x, float y){
		this.x +=x;
		this.y+=y;
		return this;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Vector2D multiply(float d){
		return multiply(d,d);
	}

	public Vector2D multiply(float xd,float yd){
		x*=xd;
		y*=yd;
		return this;
	}

	public boolean isInfinite() {
		return Double.isInfinite(x) || Double.isInfinite(y);
	}
}

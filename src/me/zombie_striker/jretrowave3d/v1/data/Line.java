package me.zombie_striker.jretrowave3d.v1.data;

public class Line {
	private Vector3D start;
	private Vector3D end;

	public Line(Vector3D v1, Vector3D v2){
		this.start = v1;
		this.end= v2;
	}
	public Vector3D getStart(){
		return start;
	}
	public Vector3D getEnd(){
		return end;
	}
}

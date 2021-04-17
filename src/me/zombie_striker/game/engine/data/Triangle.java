package me.zombie_striker.game.engine.data;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.utils.MathUtil;

import java.awt.*;

public class Triangle {

	private Vector3D[] triangle = new Vector3D[3];
	private Vector3D[] triangle_true = new Vector3D[3];
	private Color color;
	private Material material;


	public void subtract(double x, double y, double z){
		for(Vector3D v3 : triangle){
			v3.setX(v3.getX()-x);
			v3.setY(v3.getY()-y);
			v3.setZ(v3.getZ()-z);
		}
		for(Vector3D v3 : triangle_true){
			v3.setX(v3.getX()-x);
			v3.setY(v3.getY()-y);
			v3.setZ(v3.getZ()-z);
		}
	}
	public void add(double x, double y, double z){
		for(Vector3D v3 : triangle){
			v3.setX(v3.getX()+x);
			v3.setY(v3.getY()+y);
			v3.setZ(v3.getZ()+z);
		}
		for(Vector3D v3 : triangle_true){
			v3.setX(v3.getX()+x);
			v3.setY(v3.getY()+y);
			v3.setZ(v3.getZ()+z);
		}
	}


	public Triangle(Triangle t1) {
		triangle[0]=new Vector3D(t1.getVertexes()[0]);
		triangle[1]=new Vector3D(t1.getVertexes()[1]);
		triangle[2]=new Vector3D(t1.getVertexes()[2]);
		triangle_true[0]=new Vector3D(t1.getTrues()[0]);
		triangle_true[1]=new Vector3D(t1.getTrues()[1]);
		triangle_true[2]=new Vector3D(t1.getTrues()[2]);
		this.color = new Color(t1.getColor().getRGB());
	}
	public Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Color color) {
		triangle[0]=v1;
		triangle[1]=v2;
		triangle[2]=v3;
		triangle_true[0]=new Vector3D(v1);
		triangle_true[1]=new Vector3D(v2);
		triangle_true[2]=new Vector3D(v3);
		this.color = color;
	}
	public Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Material texture) {
		triangle[0]=v1;
		triangle[1]=v2;
		triangle[2]=v3;
		triangle_true[0]=new Vector3D(v1);
		triangle_true[1]=new Vector3D(v2);
		triangle_true[2]=new Vector3D(v3);
		this.material = texture;
	}

	public Material getMaterial(){
		return material;
	}

	public Color getColor(){
		return color;
	}

	public Vector3D[] getPoints() {
		return triangle;
	}

	public double getAverageDistanceSquared(World world){
		double dis = 0;
		dis += MathUtil.distanceSquared(triangle[0],world.camera.getLocation());
		dis += MathUtil.distanceSquared(triangle[1],world.camera.getLocation());
		dis += MathUtil.distanceSquared(triangle[2],world.camera.getLocation());
		return dis/3;
	}
	public double getAverageDistance(World world){
		return Math.sqrt(getAverageDistanceSquared(world));
	}

	public Vector3D[] getTrues() {
		return triangle_true;
	}
	public Vector3D[] getVertexes() {
		return triangle;
	}
}

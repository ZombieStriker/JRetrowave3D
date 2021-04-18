package me.zombie_striker.jretrowave3d.geometry;

import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.BlockFace;
import me.zombie_striker.jretrowave3d.data.Material;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;

import java.awt.*;
import java.util.Arrays;

public class Floor extends RenderableObject {


	private double yaw = 0;
	private double pitch = 0;
	private Vector3D bottomCorner;
	private Vector3D topCorner;

	public Floor(Vector3D center, double width, double length) {
		super(2);

		Vector3D bottomrightfront = new Vector3D(center.getX() + width, center.getY(), center.getZ() + length);
		Vector3D bottomrightback = new Vector3D(center.getX() + width, center.getY(), center.getZ());
		Vector3D bottomleftfront = new Vector3D(center.getX(), center.getY(), center.getZ() + length);
		Vector3D bottomleftback = new Vector3D(center.getX(), center.getY(), center.getZ());

		bottomCorner = bottomleftback;
		topCorner = bottomrightfront;


		/*sides[0]= new Plane(bottomleftfront,toprightfront);//front
		sides[1]= new Plane(bottomleftback,toprightback);//back
		sides[2]= new Plane(bottomleftback,topleftfront);//left
		sides[3]= new Plane(bottomrightback,toprightfront);//right*/

		getTriangles()[0] = new Triangle(bottomleftback,bottomrightback, bottomrightfront,  new Color(245, 5, 165)); // left top
		getTriangles()[1] = new Triangle(bottomrightfront, bottomleftfront, bottomleftback, new Color(245, 5, 165)); // right top
		//sides[4]= new Plane(topleftback,toprightfront);//top
		//sides[5]= new Plane(bottomleftfront,bottomrightfront);//bottom
	}

	public Floor(Vector3D center, double width, double length, Material textures) {
		super(2);
		Material[] array = new Material[4];
		Arrays.fill(array, textures);
		init(center, width,  length, array);
	}

	public Floor(Vector3D center, double width, double length, Material[] textures) {
		super(2);
		init(center, width, length, textures);
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
		updateTriangles();
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
		updateTriangles();
	}


	public void init(Vector3D center, double width, double length, Material[] textures) {

		Vector3D bottomrightfront = new Vector3D(center.getX() + width, center.getY(), center.getZ() + length);
		Vector3D bottomrightback = new Vector3D(center.getX() + width, center.getY(), center.getZ());
		Vector3D bottomleftfront = new Vector3D(center.getX(), center.getY(), center.getZ() + length);
		Vector3D bottomleftback = new Vector3D(center.getX(), center.getY(), center.getZ());

		bottomCorner = bottomleftback;
		topCorner = bottomrightfront;

		getTriangles()[0] = new Triangle(bottomleftback,bottomrightback, bottomrightfront,  new Color(245, 5, 165)); // left top
		getTriangles()[1] = new Triangle(bottomrightfront, bottomleftfront, bottomleftback, new Color(245, 5, 165)); // right top
		//sides[4]= new Plane(topleftback,toprightfront);//top
		//sides[5]= new Plane(bottomleftfront,bottomrightfront);//bottom
	}

	@Override
	public Triangle[] getTrianglesForRendering(World world) {
			return getTriangles();
		/*Plane[] planes = new Plane[2];
		if(world.camera.getPersonLocation() .getX()< sides[2].getLocation().getX()){
			planes[0] = sides[2];
		}else{
			planes[0] = sides[3];
		}
		if(world.camera.getPersonLocation() .getZ()< sides[0].getLocation().getZ()){
			planes[1] = sides[1];
		}else{
			planes[1] = sides[0];
		}
		return planes;*/
		/*
		Triangle[] sides = new Triangle[4];
		if (world.camera.getLocation().getX() + 0.5 <= this.sides[2].getPoints()[0].getX() || world.camera.getLocation().getX() + 0.5 <= this.sides[2].getPoints()[1].getX()) {
			if (!hiddenFaces[0]) {
				sides[0] = this.sides[2];
				sides[1] = this.sides[6];
			}
		} else if (world.camera.getLocation().getX() + 0.5 >= this.sides[3].getPoints()[0].getX() || world.camera.getLocation().getX() + 0.5 >= this.sides[3].getPoints()[1].getX()) {
			if (!hiddenFaces[1]) {
				sides[0] = this.sides[3];
				sides[1] = this.sides[7];
			}
		}
		if (world.camera.getLocation().getZ() + 0.5 >= this.sides[0].getPoints()[0].getZ() || world.camera.getLocation().getZ() + 0.5 >= this.sides[0].getPoints()[1].getZ()) {
			if (!hiddenFaces[3]) {
				sides[2] = this.sides[0];
				sides[3] = this.sides[4];
			}
		} else if (world.camera.getLocation().getZ() + 0.5 <= this.sides[1].getPoints()[0].getZ() || world.camera.getLocation().getZ() + 0.5 <= this.sides[1].getPoints()[1].getZ()) {
			if (!hiddenFaces[2]) {
				sides[2] = this.sides[1];
				sides[3] = this.sides[5];
			}
		}
		return sides;*/
	}

	@Override
	public void teleport(Vector3D location) {
		Vector3D dif = new Vector3D(topCorner);
		dif.subtract(bottomCorner);
		bottomCorner = location;
		topCorner = new Vector3D(location);
		topCorner.add(dif);
	}
	@Override
	public Triangle[] getAllTriangles(World world) {
		return getTriangles();
	}

	@Override
	public boolean isInside(Vector3D location, double size) {
		if (location.getX() - size <= topCorner.getX() && location.getX() + size >= bottomCorner.getX()) {
			if (location.getY() - size <= topCorner.getY() && location.getY() + size >= bottomCorner.getY()) {
				if (location.getZ() - size <= topCorner.getZ() && location.getZ() + size >= bottomCorner.getZ()) {
					return true;
				}
			}

		}

		return false;
	}

	@Override
	public Vector3D getCenter(World world) {
		return new Vector3D((topCorner.getX() + bottomCorner.getX()) / 2, (topCorner.getY() + bottomCorner.getY()) / 2, (topCorner.getZ() + bottomCorner.getZ()) / 2);
	}

}

package me.zombie_striker.jretrowave3d.rotation;

import me.zombie_striker.jretrowave3d.data.Vector3D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RotatableWrapper {

	protected float[] x = new float[1];
	protected float[] y= new float[1];
	protected float[] z= new float[1];

	protected float[] xc= new float[1];
	protected float[] yc= new float[1];
	protected float[] zc= new float[1];

	protected float[] result_x= new float[1];
	protected float[] result_y= new float[1];
	protected float[] result_z= new float[1];

	protected float[] yaw= new float[1];
	protected float[] pitch= new float[1];

	protected Vector3D[] pointers;

	private List<Vector3D> coords = new LinkedList<>();
	private List<Vector3D> center = new LinkedList<>();
	private List<Float> yaws = new LinkedList<>();
	private List<Float> pitches = new LinkedList<>();

	public void add(Vector3D coord, Vector3D center, float yaw, float pitch){
		this.coords.add(coord);
		this.center.add(center);
		this.yaws.add(yaw);
		this.pitches.add(pitch);
	}

	public void add(Vector3D coord, Vector3D center, double yaw, double pitch){
		this.coords.add(coord);
		this.center.add(center);
		this.yaws.add((float) yaw);
		this.pitches.add((float) pitch);
	}

	public void standardize(){
		List<Vector3D> coords2 = new ArrayList<Vector3D>(coords);
		List<Float> yaws2 = new ArrayList<Float>(yaws);
		List<Float> pitches2 = new ArrayList<Float>(pitches);
		List<Vector3D> center2 = new ArrayList<Vector3D>(center);
		this.x = new float[coords2.size()];
		this.y = new float[coords2.size()];
		this.z = new float[coords2.size()];
		this.xc = new float[center2.size()];
		this.yc = new float[center2.size()];
		this.zc = new float[center2.size()];
		this.yaw = new float[yaws2.size()];
		this.pitch = new float[pitches2.size()];
		this.result_x = new float[coords2.size()];
		this.result_y = new float[coords2.size()];
		this.result_z = new float[coords2.size()];
		this.pointers = new Vector3D[coords2.size()];
		int index = 0;
		for(Vector3D v : coords2){
			this.pointers[index]=v;
			this.x[index]=v.getX();
			this.y[index]=v.getY();
			this.z[index]=v.getZ();
			index++;
		}
		index = 0;
		for(Vector3D v : center2){
			this.xc[index]=v.getX();
			this.yc[index]=v.getY();
			this.zc[index]=v.getZ();
			index++;
		}
		index = 0;
		for(float v : yaws2){
			this.yaw[index]=v;
			index++;
		}
		index = 0;
		for(float v : pitches2){
			this.pitch[index]=v;
			index++;
		}
	}

	public int getResultLength(){
		return result_x.length;
	}

	public float getResultX(int index){
		return result_x[index];
	}
	public float getResultY(int index){
		return result_y[index];
	}
	public float getResultZ(int index){
		return result_z[index];
	}


	public float[] getX() {
		return x;
	}
	public float[] getY(){
		return y;
	}
	public float[] getZ(){
		return z;
	}

	public float[] getXC() {
		return xc;
	}
	public float[] getYC(){
		return yc;
	}
	public float[] getZC(){
		return zc;
	}
	public float[] getPitch(){
		return pitch;
	}
	public float[] getYaw(){
		return yaw;
	}

	public Vector3D getPointer(int i) {
		return pointers[i];
	}

	public void clear() {
		coords.clear();
		yaws.clear();
		pitches.clear();
		center.clear();
	}
}

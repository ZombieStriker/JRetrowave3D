package me.zombie_striker.jretrowave3d.geometry;

import me.zombie_striker.jretrowave3d.JRetroWave3D;
import me.zombie_striker.jretrowave3d.LightManager;
import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.data.Triangle;

public abstract class RenderableObject{

	private Triangle[] triangles;
	private boolean isRelativeToScreen = false;

	public RenderableObject(int triangles){
		this.triangles =new Triangle[triangles];
	}

	public abstract Vector3D getLocation();

	public abstract Triangle[] getTrianglesForRendering(World world);
	public abstract Triangle[] getAllTriangles(World world);

	public abstract boolean isInside(Vector3D location, double size);

	public abstract Vector3D getCenter(World world);

	public abstract double getYaw();
	public abstract void setYaw(double yaw);

	public abstract double getPitch();
	public abstract void setPitch(double pitch);

	public Triangle[] getTriangles(){
		return triangles;
	}

	public void setTriangles(Triangle[] array){
		this.triangles = array;
	}

	public void updateTriangles() {
		for (Triangle t : triangles) {
			if(t!=null)
			for (int i = 0; i < t.getTrues().length; i++) {
				t.getRelativeLocation()[i] = new Vector3D(t.getTrues()[i]);
				t.getRelativeLocation()[i].rotatePitch(getPitch(), getCenter(JRetroWave3D.getGame().getWorld()));
				t.getRelativeLocation()[i].rotateYaw(getYaw(), getCenter(JRetroWave3D.getGame().getWorld()));
				if (!isRelativeToScreen) {
					t.getRelativeLocation()[i].rotateYaw(-JRetroWave3D.getGame().getWorld().camera.getYawRadians(), JRetroWave3D.getGame().getWorld().camera.getLocation());
					t.getRelativeLocation()[i].rotatePitch(JRetroWave3D.getGame().getWorld().camera.getPitchRadians(), JRetroWave3D.getGame().getWorld().camera.getLocation());
				}
			}
			LightManager.setTriangleToRelight(t);
		}
	}

	public void setRelativeToScreen(boolean b){
		this.isRelativeToScreen = b;
	}

	public boolean isRelativeToScreen(){
		return isRelativeToScreen;
	}

	public abstract RenderableObject clone();

	public void resize(Vector3D oldMax, Vector3D newMax){
		Vector3D dif1 = new Vector3D(oldMax);
		dif1.subtract(getLocation());
		Vector3D newDif = new Vector3D(newMax);
		newDif.subtract(getLocation());
		for (Triangle t : triangles) {
			if (t != null)
				for (int i = 0; i < t.getTrues().length; i++) {
					double currentLocationX = t.getTrues()[i].getX()-getLocation().getX();
					double currentLocationY = t.getTrues()[i].getY()-getLocation().getY();
					double currentLocationZ = t.getTrues()[i].getZ()-getLocation().getZ();

					double scaleX = newDif.getX()/dif1.getX();
					double scaleY = newDif.getY()/dif1.getY();
					double scaleZ = newDif.getZ()/dif1.getZ();


				t.getTrues()[i] = new Vector3D(
						(currentLocationX*scaleX)+getLocation().getX(),
						(currentLocationY*scaleY)+getLocation().getY(),
						(currentLocationZ*scaleZ)+getLocation().getZ());
				}
		}
		updateTriangles();
	}

	/*public void shiftTriangles(Vector3D min, Vector3D max, Vector3D newmin, Vector3D newmax){
		for (Triangle t : triangles) {
			if(t!=null)
				for (int i = 0; i < t.getTrues().length; i++) {
					t.getTrues()[i] = new Vector3D(t.getTrues()[i]);
					t.getTrues()[i].add(direction);
				}
		}
		updateTriangles();
	}*/
	public void shiftTriangles(Vector3D direction){
		for (Triangle t : triangles) {
			if(t!=null)
				for (int i = 0; i < t.getTrues().length; i++) {
					t.getTrues()[i] = new Vector3D(t.getTrues()[i]);
					t.getTrues()[i].add(direction);
					}
		}
		updateTriangles();
	}

	public abstract void teleport(Vector3D location);

	public abstract RenderableObject setSize(double width, double height, double length);

	public abstract RenderableObject setSize(double size);
}

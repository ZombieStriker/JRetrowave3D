package me.zombie_striker.jretrowave3d.v1.geometry;

import me.zombie_striker.jretrowave3d.v1.JRetroWave3D;
import me.zombie_striker.jretrowave3d.v1.LightManager;
import me.zombie_striker.jretrowave3d.v1.World;
import me.zombie_striker.jretrowave3d.v1.data.Triangle;
import me.zombie_striker.jretrowave3d.v1.data.Vector3D;

public abstract class RenderableObject {

	private Triangle[] triangles;
	private boolean isRelativeToScreen = false;

	public RenderableObject(int triangles) {
		this.triangles = new Triangle[triangles];
	}

	public abstract Vector3D getLocation();

	public abstract Triangle[] getTrianglesForRendering(World world);

	public abstract Triangle[] getAllTriangles(World world);

	public abstract boolean isInside(Vector3D location, float size);

	public abstract Vector3D getCenter(World world);

	public abstract float getYaw();

	public abstract void setYawRadians(float yaw);

	public abstract float getPitch();

	public abstract void setPitchRadians(float pitch);

	public Triangle[] getTriangles() {
		return triangles;
	}

	public void setTriangles(Triangle[] array) {
		this.triangles = array;
	}


	public void updateTriangles() {
		for (Triangle t : triangles) {
			if (t != null)
				for (int i = 0; i < t.getTrues().length; i++) {
					t.getRelativeLocation()[i] = new Vector3D(t.getTrues()[i]);

					if(getPitch()!=0)
					t.getRelativeLocation()[i].rotatePitch(getPitch(),getLocation());// getCenter(JRetroWave3D.getGame().getWorld()));
					if(getYaw()!=0)
					t.getRelativeLocation()[i].rotateYaw(getYaw(),getLocation());// getCenter(JRetroWave3D.getGame().getWorld()));
					if (!isRelativeToScreen) {
						t.getRelativeLocation()[i].rotateYaw(-JRetroWave3D.getGame().getWorld().camera.getYawRadians(), JRetroWave3D.getGame().getWorld().camera.getLocation());
						t.getRelativeLocation()[i].rotatePitch(JRetroWave3D.getGame().getWorld().camera.getPitchRadians(), JRetroWave3D.getGame().getWorld().camera.getLocation());
					}
				}
			LightManager.setTriangleToRelight(t);
			t.updateNormal(false);
			t.updateNormal(true);
		}
	}

	public boolean isRelativeToScreen() {
		return isRelativeToScreen;
	}

	public void setRelativeToScreen(boolean b) {
		this.isRelativeToScreen = b;
	}

	public void setCalculateLightColor(boolean b){
		for(Triangle t : getTriangles()){
			t.setCalclight(b);
		}
	}

	public abstract RenderableObject clone();

	public void resize(Vector3D oldMax, Vector3D newMax) {
		Vector3D dif1 = new Vector3D(oldMax);
		dif1.subtract(getLocation());
		Vector3D newDif = new Vector3D(newMax);
		newDif.subtract(getLocation());
		for (Triangle t : triangles) {
			if (t != null)
				for (int i = 0; i < t.getTrues().length; i++) {
					float currentLocationX = t.getTrues()[i].getX() - getLocation().getX();
					float currentLocationY = t.getTrues()[i].getY() - getLocation().getY();
					float currentLocationZ = t.getTrues()[i].getZ() - getLocation().getZ();

					float scaleX = newDif.getX() / dif1.getX();
					float scaleY = newDif.getY() / dif1.getY();
					float scaleZ = newDif.getZ() / dif1.getZ();


					t.getTrues()[i] = new Vector3D(
							(currentLocationX * scaleX) + getLocation().getX(),
							(currentLocationY * scaleY) + getLocation().getY(),
							(currentLocationZ * scaleZ) + getLocation().getZ());
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
	public void shiftTriangles(Vector3D direction) {
		for (Triangle t : triangles) {
			if (t != null)
				for (int i = 0; i < t.getTrues().length; i++) {
					t.getTrues()[i] = new Vector3D(t.getTrues()[i]);
					t.getTrues()[i].add(direction);
				}
		}
		updateTriangles();
	}

	public abstract void teleport(Vector3D location);

	public abstract RenderableObject setSize(float width, float height, float length);

	public abstract RenderableObject setSize(float size);
}

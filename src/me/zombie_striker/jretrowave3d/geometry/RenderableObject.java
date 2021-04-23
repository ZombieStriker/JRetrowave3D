package me.zombie_striker.jretrowave3d.geometry;

import me.zombie_striker.jretrowave3d.JRetroWave3D;
import me.zombie_striker.jretrowave3d.LightManager;
import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.rotation.RotatableWrapper;

public abstract class RenderableObject {

	private static RotatableWrapper rotatableWrapperScreenRel = new RotatableWrapper();
	private static RotatableWrapper rotatableWrapper1 = new RotatableWrapper();
	private static RotatableWrapper rotatableWrapper2 = new RotatableWrapper();
	private static boolean updateWithGPU = false;
	private Triangle[] triangles;
	private boolean isRelativeToScreen = false;

	public RenderableObject(int triangles) {
		this.triangles = new Triangle[triangles];
	}

	public static void setUpdateTrianglesWithGPU() {
		updateWithGPU = true;
	}

	public static boolean shouldUpdateWithGPU() {
		return updateWithGPU;
	}

	public static void updateTrianglesGPU() {
		/*
		rotatableWrapperScreenRel.standardize();
		if(rotatableWrapperScreenRel.getResultLength()>0) {
			Range range = Range.create(MathUtil.getMultipleClosestTo(rotatableWrapperScreenRel.getResultLength(),256));
			KernelRotatePitchYaw rotateRel = new KernelRotatePitchYaw(rotatableWrapperScreenRel);
			System.out.println(rotatableWrapperScreenRel.getResultLength() + "===" + range.getLocalSize_0() + " " + range.getLocalSize_1() + " " + range.getLocalSize_2() + "  || " + range.getGlobalSize_0() + " " + range.getLocalSize_1() + " " + range.getGlobalSize_2());
			rotateRel.execute(range);
			for(int i = 0; i < rotateRel.getSize(); i++){
				Vector3D v = rotatableWrapperScreenRel.getPointer(i);
				v.setX(rotateRel.getResultX(i));
				v.setY(rotateRel.getResultY(i));
				v.setZ(rotateRel.getResultZ(i));
			}
		}
		rotatableWrapper1.standardize();
		if(rotatableWrapper1.getResultLength()>0) {

			Range range2 = Range.create(MathUtil.getMultipleClosestTo(rotatableWrapper1.getResultLength(),256));
			System.out.println("W1 "+rotatableWrapper1.getResultLength() + "===" + range2.getLocalSize_0() + " " + range2.getLocalSize_1() + " " + range2.getLocalSize_2() + "  || " + range2.getGlobalSize_0() + " " + range2.getLocalSize_1() + " " + range2.getGlobalSize_2());
			KernelRotatePitchYaw rotate1 = new KernelRotatePitchYaw(rotatableWrapper1);
			rotate1.execute(range2);

			for (int i = 0; i < rotatableWrapper1.getResultLength(); i++) {
				rotatableWrapper2.add(new Vector3D(rotatableWrapper1.getResultX(i), rotatableWrapper1.getResultY(i), rotatableWrapper1.getResultZ(i)), JRetroWave3D.getGame().getWorld().camera.getLocation(), -JRetroWave3D.getGame().getWorld().camera.getYawRadians(), JRetroWave3D.getGame().getWorld().camera.getPitchRadians());
			}
			rotatableWrapper2.standardize();
			Range range3 = Range.create(MathUtil.getMultipleClosestTo(rotatableWrapper2.getResultLength(),256));
			KernelRotateYawPitch rotate2 = new KernelRotateYawPitch(rotatableWrapper2);
			rotate2.execute(range3);

			for (int i = 0; i < rotate2.getSize(); i++) {
				Vector3D v = rotatableWrapper2.getPointer(i);
				v.setX(rotate2.getResultX(i));
				v.setY(rotate2.getResultY(i));
				v.setZ(rotate2.getResultZ(i));
			}
		}
		rotatableWrapperScreenRel.clear();
		rotatableWrapper1.clear();
		rotatableWrapper2.clear();
*/

	}

	public abstract Vector3D getLocation();

	public abstract Triangle[] getTrianglesForRendering(World world);

	public abstract Triangle[] getAllTriangles(World world);

	public abstract boolean isInside(Vector3D location, float size);

	public abstract Vector3D getCenter(World world);

	public abstract float getYaw();

	public abstract void setYaw(float yaw);

	public abstract float getPitch();

	public abstract void setPitch(float pitch);

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

	public boolean isRelativeToScreen() {
		return isRelativeToScreen;
	}

	public void setRelativeToScreen(boolean b) {
		this.isRelativeToScreen = b;
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
		setUpdateTrianglesWithGPU();
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
		setUpdateTrianglesWithGPU();
	}

	public abstract void teleport(Vector3D location);

	public abstract RenderableObject setSize(float width, float height, float length);

	public abstract RenderableObject setSize(float size);
}

package me.zombie_striker.jretrowave3d.geometry;

import me.zombie_striker.game.Main;
import me.zombie_striker.jretrowave3d.JRetroWave3D;
import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.data.Triangle;

public abstract class RenderableObject{

	private Triangle[] sides;

	public RenderableObject(int triangles){
		sides=new Triangle[triangles];
	}

	public abstract Triangle[] getTrianglesForRendering(World world);
	public abstract Triangle[] getAllTriangles(World world);

	public abstract boolean isInside(Vector3D location, double size);

	public abstract Vector3D getCenter(World world);

	public abstract double getYaw();
	public abstract void setYaw(double yaw);

	public abstract double getPitch();
	public abstract void setPitch(double pitch);

	public Triangle[] getTriangles(){
		return sides;
	}

	public void updateTriangles() {
		for (Triangle t : sides) {
			if(t!=null)
			for (int i = 0; i < t.getTrues().length; i++) {
				t.getRelativeLocation()[i] = new Vector3D(t.getTrues()[i]);
				t.getRelativeLocation()[i].rotatePitch(getPitch(), getCenter(JRetroWave3D.getGame().getWorld()));
				t.getRelativeLocation()[i].rotateYaw(getYaw(), getCenter(JRetroWave3D.getGame().getWorld()));
				t.getRelativeLocation()[i].rotateYaw(-JRetroWave3D.getGame().getWorld().camera.getYawRadians(), JRetroWave3D.getGame().getWorld().camera.getLocation());
				t.getRelativeLocation()[i].rotatePitch(JRetroWave3D.getGame().getWorld().camera.getPitchRadians(), JRetroWave3D.getGame().getWorld().camera.getLocation());
			}
		}
	}

	public abstract void teleport(Vector3D location);
}

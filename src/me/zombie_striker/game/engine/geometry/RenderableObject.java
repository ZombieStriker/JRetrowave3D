package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.Main;
import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Vector3D;
import me.zombie_striker.game.engine.data.Triangle;

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
			for (int i = 0; i < t.getTrues().length; i++) {
				t.getVertexes()[i] = new Vector3D(t.getTrues()[i]);
				t.getVertexes()[i].rotatePitch(getPitch(), getCenter(Main.game.getWorld()));
				t.getVertexes()[i].rotateYaw(getYaw(), getCenter(Main.game.getWorld()));
				t.getVertexes()[i].rotateYaw(-Main.game.getWorld().camera.getYawRadians(), Main.game.getWorld().camera.getLocation());
				t.getVertexes()[i].rotatePitch(Main.game.getWorld().camera.getPitchRadians(), Main.game.getWorld().camera.getLocation());
			}
		}
	}
}

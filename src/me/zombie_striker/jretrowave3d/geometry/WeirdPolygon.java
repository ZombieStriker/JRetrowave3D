package me.zombie_striker.jretrowave3d.geometry;

import me.zombie_striker.game.Main;
import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;

import java.awt.*;

public class WeirdPolygon extends RenderableObject {


	private Vector3D bottomCorner1;
	private Vector3D bottomCorner2;
	private Vector3D topCorner;
	private double yaw = 0;
	private double pitch = 0;


	public WeirdPolygon(Vector3D center, double width, double height, double length) {
		super(6);
		Vector3D toprightback = new Vector3D(center.getX() + width, center.getY() + height, center.getZ() + length);
		Vector3D topback = new Vector3D(center.getX() + (width / 2), center.getY() + height, center.getZ() + length);
		Vector3D topleftback = new Vector3D(center.getX(), center.getY() + height, center.getZ() + length);

		Vector3D bottomrightback = new Vector3D(center.getX() + width, center.getY(), center.getZ() + length);
		Vector3D bottomfront = new Vector3D(center.getX() + (width / 2), center.getY(), center.getZ());
		Vector3D bottomleftback = new Vector3D(center.getX(), center.getY(), center.getZ() + length);

		topCorner = new Vector3D(bottomfront.getX(), toprightback.getY(), bottomfront.getZ());
		bottomCorner1 = bottomleftback;
		bottomCorner2 = bottomrightback;

		getTriangles()[0] = new Triangle(topleftback, bottomleftback, bottomrightback, new Color(255, 255, 255));
		getTriangles()[1] = new Triangle(topleftback, toprightback, bottomrightback, new Color(255, 255, 255));
		getTriangles()[2] = new Triangle(topleftback, bottomleftback, bottomfront, new Color(255, 255, 255));
		getTriangles()[3] = new Triangle(topleftback, topback, bottomfront, new Color(255, 255, 255));
		getTriangles()[4] = new Triangle(toprightback, bottomrightback, bottomfront, new Color(255, 255, 255));
		getTriangles()[5] = new Triangle(toprightback, topback, bottomfront, new Color(255, 255, 255));

		/*sides[0] = new Plane(bottomleftfront, toprightfront);//front
		sides[1] = new Plane(bottomback, toprightfront);//right
		sides[2] = new Plane(bottomback, topleftfront);//left*/
	}

	@Override
	public Triangle[] getTrianglesForRendering(World world) {
		return getTriangles();
	}

	@Override
	public boolean isInside(Vector3D location, double size) {
		if (location.getY() + size < topCorner.getY() && location.getY() - size >= bottomCorner1.getY()) {
			if (location.getZ() + size < bottomCorner1.getZ() && location.getZ() - size >= bottomCorner2.getZ()) {
				if (location.getX() + size < bottomCorner1.getX() && location.getX() - size >= bottomCorner2.getX()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Triangle[] getAllTriangles(World world) {
		return getTriangles();
	}

	@Override
	public Vector3D getCenter(World world) {
		return new Vector3D(bottomCorner1.getX() - bottomCorner2.getX(), topCorner.getY() - bottomCorner1.getY(), topCorner.getZ());
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		double deltaYaw = yaw - this.yaw;
		this.yaw = yaw;
		for (Triangle t : getTriangles()) {
			for (Vector3D p : t.getPoints()) {
				p.rotateYaw(deltaYaw, getCenter(Main.game.getWorld()));
			}
		}
	}


	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		double deltaPitch = pitch - this.pitch;
		this.pitch = pitch;
		for (Triangle t : getTriangles()) {
			for (Vector3D p : t.getPoints()) {
				p.rotatePitch(deltaPitch, getCenter(Main.game.getWorld()));
			}
		}
	}

}

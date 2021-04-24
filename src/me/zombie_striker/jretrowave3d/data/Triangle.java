package me.zombie_striker.jretrowave3d.data;

import me.zombie_striker.jretrowave3d.LightManager;
import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.utils.MathUtil;

import java.awt.*;

public class Triangle {

	private Vector3D[] triangle = new Vector3D[3];
	private Vector3D[] triangle_true = new Vector3D[3];
	private Vector3D normal=null;
	private Vector3D normal_true=null;
	private Color color;
	private Material material;


	public Triangle(Triangle t1) {
		triangle[0] = new Vector3D(t1.getRelativeLocation()[0]);
		triangle[1] = new Vector3D(t1.getRelativeLocation()[1]);
		triangle[2] = new Vector3D(t1.getRelativeLocation()[2]);
		triangle_true[0] = new Vector3D(t1.getTrues()[0]);
		triangle_true[1] = new Vector3D(t1.getTrues()[1]);
		triangle_true[2] = new Vector3D(t1.getTrues()[2]);
		/*exactLocation[0] = new Vector3D(t1.getExactLocation()[0]);
		exactLocation[1] = new Vector3D(t1.getExactLocation()[1]);
		exactLocation[2] = new Vector3D(t1.getExactLocation()[2]);*/
		this.color = new Color(t1.getColor().getRGB());
	}
	//TODO: Test if we need exact location

	/*public Vector3D[] getExactLocation() {
		return exactLocation;
	}*/

	public Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Color color) {
		triangle[0] = v1;
		triangle[1] = v2;
		triangle[2] = v3;
		triangle_true[0] = new Vector3D(v1);
		triangle_true[1] = new Vector3D(v2);
		triangle_true[2] = new Vector3D(v3);
		//exactLocation[0] = new Vector3D(v1);
		//exactLocation[1] = new Vector3D(v2);
		//exactLocation[2] = new Vector3D(v3);
		this.color = color;
	}

	public Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Material texture) {
		triangle[0] = v1;
		triangle[1] = v2;
		triangle[2] = v3;
		triangle_true[0] = new Vector3D(v1);
		triangle_true[1] = new Vector3D(v2);
		triangle_true[2] = new Vector3D(v3);
	//	exactLocation[0] = new Vector3D(v1);
		//exactLocation[1] = new Vector3D(v2);
	//	exactLocation[2] = new Vector3D(v3);
		this.material = texture;
	}

	public void subtract(float x, float y, float z) {
		for (Vector3D v3 : triangle) {
			v3.setX(v3.getX() - x);
			v3.setY(v3.getY() - y);
			v3.setZ(v3.getZ() - z);
		}
		for (Vector3D v3 : triangle_true) {
			v3.setX(v3.getX() - x);
			v3.setY(v3.getY() - y);
			v3.setZ(v3.getZ() - z);
		}
		LightManager.setTriangleToRelight(this);
		updateNormal(false);
		updateNormal(true);
	}

	public void add(float x, float y, float z) {
		for (Vector3D v3 : triangle) {
			v3.setX(v3.getX() + x);
			v3.setY(v3.getY() + y);
			v3.setZ(v3.getZ() + z);
		}
		for (Vector3D v3 : triangle_true) {
			v3.setX(v3.getX() + x);
			v3.setY(v3.getY() + y);
			v3.setZ(v3.getZ() + z);
		}
		LightManager.setTriangleToRelight(this);
		updateNormal(false);
		updateNormal(true);
	}

	public Vector3D getNormal(boolean trueVertexes){
		if(normal_true!= null && trueVertexes){
			return normal_true;
		}
		if(normal!=null && !trueVertexes){
			return normal;
		}
		if(normal_true==null && trueVertexes){
			return updateNormal(true);
		}
		return updateNormal(false);
	}

	public Vector3D updateNormal(boolean getTrueVertexes) {
		Vector3D u;
		Vector3D v;
		if (getTrueVertexes) {
			v = new Vector3D(getTrues()[2].getX() - getTrues()[0].getX(), getTrues()[2].getY() - getTrues()[0].getY(), getTrues()[2].getZ() - getTrues()[0].getZ());
			u = new Vector3D(getTrues()[1].getX() - getTrues()[0].getX(), getTrues()[1].getY() - getTrues()[0].getY(), getTrues()[1].getZ() - getTrues()[0].getZ());
		}else{
			v = new Vector3D(getRelativeLocation()[2].getX() - getRelativeLocation()[0].getX(), getRelativeLocation()[2].getY() - getRelativeLocation()[0].getY(), getRelativeLocation()[2].getZ() - getRelativeLocation()[0].getZ());
			u = new Vector3D(getRelativeLocation()[1].getX() - getRelativeLocation()[0].getX(), getRelativeLocation()[1].getY() - getRelativeLocation()[0].getY(), getRelativeLocation()[1].getZ() - getRelativeLocation()[0].getZ());

		}

		float normalx = (u.getY() * v.getZ()) - (u.getZ() * v.getY());
		float normaly = (u.getZ() * v.getX()) - (u.getX() * v.getZ());
		float normalz = (u.getX() * v.getY()) - (u.getY() * v.getX());

		Vector3D normal = new Vector3D(normalx, normaly, normalz);
		if(getTrueVertexes){
			this.normal_true=normal;
		}else{
			this.normal=normal;
		}
		return normal;
	}

	public Material getMaterial() {
		return material;
	}

	public Color getColor() {
		return color;
	}

	public Vector3D[] getPoints() {
		return triangle;
	}

	public double getAverageDistanceSquared(World world) {
		double dis = 0;
		dis += MathUtil.distanceSquared(triangle[0], world.camera.getLocation());
		dis += MathUtil.distanceSquared(triangle[1], world.camera.getLocation());
		dis += MathUtil.distanceSquared(triangle[2], world.camera.getLocation());
		return dis / 3;
	}

	public double getAverageDistance(World world) {
		return Math.sqrt(getAverageDistance(world));
	}

	public Vector3D[] getTrues() {
		return triangle_true;
	}

	public Vector3D[] getRelativeLocation() {
		return triangle;
	}

	public Float getFurthestDistance(World world) {
		double dis = MathUtil.distanceSquared(triangle[0], world.camera.getLocation());
		double temp = MathUtil.distanceSquared(triangle[1], world.camera.getLocation());
		if (temp > dis)
			dis = temp;
		temp = MathUtil.distanceSquared(triangle[2], world.camera.getLocation());
		if (temp > dis)
			dis = temp;
		return (float)Math.sqrt(dis);
	}

	public Float getClosestDistance(World world) {
		float dis = MathUtil.distanceSquared(triangle[0], world.camera.getLocation());
		float temp = MathUtil.distanceSquared(triangle[1], world.camera.getLocation());
		if (temp < dis)
			dis = temp;
		temp = MathUtil.distanceSquared(triangle[2], world.camera.getLocation());
		if (temp < dis)
			dis = temp;
		return (float)Math.sqrt(dis);
	}

	public Vector3D getCenter() {
		//TODO: Swapped from exact location
		float maxheight = Math.max(triangle_true[0].getY(),triangle_true[1].getY());
		maxheight = Math.max(maxheight,triangle_true[2].getY());
		float minheight = Math.min(triangle_true[0].getY(),triangle_true[1].getY());
		minheight = Math.min(minheight,triangle_true[2].getY());

		float maxwidth = Math.max(triangle_true[0].getX(),triangle_true[1].getX());
		maxwidth = Math.max(maxwidth,triangle_true[2].getX());
		float minwidth = Math.min(triangle_true[0].getX(),triangle_true[1].getX());
		minwidth = Math.min(minwidth,triangle_true[2].getX());


		float maxlength = Math.max(triangle_true[0].getZ(),triangle_true[1].getZ());
		maxlength = Math.max(maxlength,triangle_true[2].getZ());
		float minlength = Math.min(triangle_true[0].getZ(),triangle_true[1].getZ());
		minlength = Math.min(minlength,triangle_true[2].getZ());

		return new Vector3D((maxwidth+minwidth)/2,(maxheight+minheight)/2,(maxlength+minlength)/2);
	}


	private boolean calclight = true;
	public boolean shouldCalculateLight() {
	return calclight;
	}
	public void setCalclight(boolean b){
		this.calclight = b;
	}

    public Float getCenterDistance(World world) {
		Vector3D center = new Vector3D(
				(triangle[0].getX()+triangle[1].getX()+triangle[2].getX())/3,
		(triangle[0].getY()+triangle[1].getY()+triangle[2].getY())/3,
		(triangle[0].getZ()+triangle[1].getZ()+triangle[2].getZ())/3
		);
		float dis = MathUtil.distanceSquared(center, world.camera.getLocation());
		return (float)Math.sqrt(dis);
    }
}

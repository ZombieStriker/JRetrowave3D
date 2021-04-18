package me.zombie_striker.jretrowave3d.physics;

import me.zombie_striker.jretrowave3d.TickManager;
import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.TickableObject;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.physics.boundingbox.BoundingBox;

import java.util.Vector;

public class MovableObject extends StaticObject implements TickableObject {

	private double gravityPerTick = -0.002;
	private Vector3D velocity = new Vector3D(0,0,0);

	public MovableObject(World world, Vector3D location, BoundingBox box, RenderableObject renderableObject) {
		super(world, location, box, renderableObject);
		TickManager.registerTickableObject(this);
	}

	@Override
	public void tick() {
		this.velocity.setY(this.velocity.getY()+gravityPerTick);
		//System.out.println(this.velocity.getY());
		Vector3D current = getLocation();
		Vector3D goingTo = new Vector3D(getLocation());
		goingTo.add(velocity);
		teleport(goingTo);
		for(BoundingBox box : getWorld().getBoundingBoxes()){
			if(box != getBoundingBox() && box.collides(getBoundingBox(), goingTo,current)){
				teleport(current);
				this.velocity.setY(0);
				break;
			}
		}
		getRender().updateTriangles();
	}
}

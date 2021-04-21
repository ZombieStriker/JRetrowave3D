package me.zombie_striker.jretrowave3d.physics;

import me.zombie_striker.jretrowave3d.TickManager;
import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.TickableObject;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.events.EventManager;
import me.zombie_striker.jretrowave3d.events.types.ObjectFallOutOfWorldEvent;
import me.zombie_striker.jretrowave3d.events.types.ProjectileHitEvent;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.physics.boundingbox.BoundingBox;

public class MovableObject extends WorldObject implements TickableObject {

	private double gravityPerTick = -0.002;
	private Vector3D velocity = new Vector3D(0, 0, 0);
	private boolean applyGravity = true;
	private boolean hasVelocity = true;

	public MovableObject(World world, Vector3D location, BoundingBox box, RenderableObject renderableObject) {
		super(world, location, box, renderableObject);
		TickManager.registerTickableObject(this);
	}

	public boolean hasGravity() {
		return applyGravity;
	}
	public boolean hasVelocity(){
		return hasVelocity;
	}
	public void setHasVelocity(boolean b){
		this.hasVelocity = b;
	}
	public void setVelocity(Vector3D velocity){
		this.velocity = velocity;
	}
	public void setHasGravity(boolean b){
		this.applyGravity = b;
	}

	@Override
	public void tick() {
		if (hasGravity())
			this.velocity.setY(this.velocity.getY() + gravityPerTick);
		//System.out.println(this.velocity.getY());
		if (hasVelocity()) {
			Vector3D current = new Vector3D(getLocation());
			Vector3D goingTo = new Vector3D(getLocation());
			goingTo.add(velocity);
			teleport(goingTo);
			for (BoundingBox box : getWorld().getBoundingBoxes()) {
				if (box != getBoundingBox() && box.collides(getBoundingBox(), goingTo, current)) {
					teleport(current);
					this.velocity.setY(-(velocity.getY())/1.5);
					break;
				}
			}
		}
		if(getLocation().getY() < -1000){
			ObjectFallOutOfWorldEvent event = new ObjectFallOutOfWorldEvent(this);
			EventManager.call(event);
			if(!event.isCanceled()){
				getWorld().removeBoundingBox(getBoundingBox());
				getWorld().removeToRender(getRender());
				TickManager.removeTickableObject(this);
			}

		}
		getRender().updateTriangles();
	}
	public Vector3D getVelocity(){
	return velocity;
	}
	public void setGravity(double v){
		this.gravityPerTick = v;
	}
	public double getGravityPerTick(){
		return gravityPerTick;
	}
}

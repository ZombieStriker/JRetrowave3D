package me.zombie_striker.jretrowave3d.v1.physics;

import me.zombie_striker.jretrowave3d.v1.TickManager;
import me.zombie_striker.jretrowave3d.v1.World;
import me.zombie_striker.jretrowave3d.v1.data.TickableObject;
import me.zombie_striker.jretrowave3d.v1.data.Vector3D;
import me.zombie_striker.jretrowave3d.v1.events.EventManager;
import me.zombie_striker.jretrowave3d.v1.events.types.ObjectFallOutOfWorldEvent;
import me.zombie_striker.jretrowave3d.v1.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.v1.physics.boundingbox.BoundingBox;

public class TickingObject extends WorldObject implements TickableObject {

	private float gravityPerTick = -0.002f;
	private Vector3D velocity = new Vector3D(0, 0, 0);
	private boolean applyGravity = true;
	private boolean hasVelocity = true;

	public TickingObject(World world, Vector3D location, BoundingBox box, RenderableObject renderableObject) {
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
					this.velocity.setY(-(velocity.getY())/1.5f);
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
	}
	public Vector3D getVelocity(){
	return velocity;
	}
	public void setGravity(float v){
		this.gravityPerTick = v;
	}
	public float getGravityPerTick(){
		return gravityPerTick;
	}
}

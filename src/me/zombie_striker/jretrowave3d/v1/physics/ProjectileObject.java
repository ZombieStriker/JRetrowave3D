package me.zombie_striker.jretrowave3d.v1.physics;

import me.zombie_striker.jretrowave3d.v1.TickManager;
import me.zombie_striker.jretrowave3d.v1.World;
import me.zombie_striker.jretrowave3d.v1.data.Vector3D;
import me.zombie_striker.jretrowave3d.v1.events.EventManager;
import me.zombie_striker.jretrowave3d.v1.events.types.ProjectileHitEvent;
import me.zombie_striker.jretrowave3d.v1.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.v1.physics.boundingbox.BoundingBox;

public class ProjectileObject extends TickingObject {

	public ProjectileObject(World world, Vector3D velocity, Vector3D location, BoundingBox box, RenderableObject renderableObject) {
		super(world, location, box, renderableObject);
		this.setVelocity(velocity);
	}

	@Override
	public void tick() {
		if (hasGravity())
			this.getVelocity().setY(this.getVelocity().getY() + getGravityPerTick());
		if (hasVelocity()) {
			Vector3D current = new Vector3D(getLocation());
			Vector3D goingTo = new Vector3D(getLocation());
			goingTo.add(getVelocity());
			teleport(goingTo);
			for (BoundingBox box : getWorld().getBoundingBoxes()) {
				if (box != getBoundingBox() && box.collides(getBoundingBox(), goingTo, current)) {

					ProjectileHitEvent event = new ProjectileHitEvent(this,box,getWorld());
					EventManager.call(event);
					if(!event.isCanceled()){
						getWorld().removeBoundingBox(getBoundingBox());
						getWorld().removeToRender(getRender());
						TickManager.removeTickableObject(this);
					}

					break;
				}
			}
		}
	}
}

package me.zombie_striker.jretrowave3d.v1.events.types;

import me.zombie_striker.jretrowave3d.v1.World;
import me.zombie_striker.jretrowave3d.v1.events.Event;
import me.zombie_striker.jretrowave3d.v1.physics.ProjectileObject;
import me.zombie_striker.jretrowave3d.v1.physics.boundingbox.BoundingBox;

public class ProjectileHitEvent extends Event {

	private ProjectileObject projectileObject;
	private BoundingBox collide;
	private World world;
	private boolean iscanceled = false;

	public ProjectileHitEvent(ProjectileObject projectile, BoundingBox collidedWith, World world){
		this.world = world;
		this.projectileObject = projectile;
		this.collide = collidedWith;
	}

	public ProjectileObject getProjectile(){
		return projectileObject;
	}
	public boolean isCanceled(){
		return iscanceled;
	}
	public void setCanceled(boolean b){
		this.iscanceled=b;
	}
	public BoundingBox getHitBoundingBox(){
		return collide;
	}
	public World getWorld(){
		return world;
	}
}

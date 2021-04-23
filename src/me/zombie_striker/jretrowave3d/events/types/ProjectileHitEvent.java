package me.zombie_striker.jretrowave3d.events.types;

import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.events.Event;
import me.zombie_striker.jretrowave3d.physics.ProjectileObject;

public class ProjectileHitEvent extends Event {

	private ProjectileObject projectileObject;
	private World world;
	private boolean iscanceled = false;

	public ProjectileHitEvent(ProjectileObject projectile, World world){
		this.world = world;
		this.projectileObject = projectile;
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
	public World getWorld(){
		return world;
	}
}
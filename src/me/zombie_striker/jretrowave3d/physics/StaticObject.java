package me.zombie_striker.jretrowave3d.physics;

import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.physics.boundingbox.BoundingBox;

public abstract class StaticObject {

	private BoundingBox box;
	private RenderableObject render;

	private Vector3D location;

	private World world;

	public StaticObject(World world,Vector3D location, BoundingBox box, RenderableObject renderableObject){
		this.box = box;
		this.render = renderableObject;
		this.world = world;
		this.location = location;
		world.registerBoundingBox(box);
		world.registerObjectToRender(renderableObject);
	}
	public World getWorld(){
		return world;
	}

	public Vector3D getLocation(){
		return location;
	}

	public BoundingBox getBoundingBox(){
		return box;
	}
	public RenderableObject getRender(){
		return render;
	}
	public void teleport(Vector3D location){
		//Vector3D difference = new Vector3D(this.location.getX()-location.getX(),this.location.getY()-location.getY(),this.getLocation().getZ()-location.getZ());
		box.teleport(location);
		render.teleport(location);
		this.location = location;
	}

}

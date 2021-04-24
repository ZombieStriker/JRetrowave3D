package me.zombie_striker.jretrowave3d.physics;

import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.physics.boundingbox.BoundingBox;

public abstract class WorldObject {

	private BoundingBox box;
	private RenderableObject render;

	private Vector3D location;

	private World world;

	public WorldObject(World world, Vector3D location, BoundingBox box, RenderableObject renderableObject) {
		this.box = box;
		this.render = renderableObject;
		this.world = world;
		this.location = location;
		if (box != null)
			world.registerBoundingBox(box);
		if (renderableObject != null)
			world.registerObjectToRender(renderableObject);
	}

	public World getWorld() {
		return world;
	}

	public Vector3D getLocation() {
		return location;
	}

	public BoundingBox getBoundingBox() {
		return box;
	}

	public RenderableObject getRender() {
		return render;
	}

	public void teleport(Vector3D location) {
		//Vector3D difference = new Vector3D(this.location.getX()-location.getX(),this.location.getY()-location.getY(),this.getLocation().getZ()-location.getZ());
		if(box!=null)
		box.teleport(location);
		if(render!=null)
		render.teleport(location);
		this.location = location;
	}
	public void move(Vector3D difference){
		if(box!=null){
			box.teleport(box.getLocation().clone().add(difference));
		}
		if(render !=null){
			render.teleport(render.getLocation().clone().add(difference));
		}
		this.location.add(difference);
	}

	public static RenderableObject teleportAndSetSize(RenderableObject shape, Vector3D location, float x , float y , float z){
		shape.teleport(location);
		shape.setSize(x,y,z);
		return shape;
	}

	public void remove(){
		if(getRender()!=null)
		getWorld().removeToRender(getRender());
		if(box!=null)
		getWorld().removeBoundingBox(getBoundingBox());
	}

}

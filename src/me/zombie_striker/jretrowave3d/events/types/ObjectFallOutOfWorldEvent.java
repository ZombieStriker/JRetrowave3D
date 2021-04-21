package me.zombie_striker.jretrowave3d.events.types;

import me.zombie_striker.jretrowave3d.events.Event;
import me.zombie_striker.jretrowave3d.physics.MovableObject;

public class ObjectFallOutOfWorldEvent extends Event {

	public boolean canceled = false;
	public MovableObject object;

	public ObjectFallOutOfWorldEvent(MovableObject object){
		this.object = object;
	}

	public MovableObject getObject(){
		return object;
	}

	public void setCanceled(boolean b){
		this.canceled = b;
	}
	public boolean isCanceled(){
		return canceled;
	}
}

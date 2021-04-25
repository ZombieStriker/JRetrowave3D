package me.zombie_striker.jretrowave3d.v1.events.types;

import me.zombie_striker.jretrowave3d.v1.events.Event;
import me.zombie_striker.jretrowave3d.v1.physics.TickingObject;

public class ObjectFallOutOfWorldEvent extends Event {

	public boolean canceled = false;
	public TickingObject object;

	public ObjectFallOutOfWorldEvent(TickingObject object){
		this.object = object;
	}

	public TickingObject getObject(){
		return object;
	}

	public void setCanceled(boolean b){
		this.canceled = b;
	}
	public boolean isCanceled(){
		return canceled;
	}
}

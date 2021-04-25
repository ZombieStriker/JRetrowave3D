package me.zombie_striker.jretrowave3d.v1;

import me.zombie_striker.jretrowave3d.v1.data.TickableObject;

import java.util.LinkedList;
import java.util.List;

public class TickManager {

	private static List<TickableObject> ticks = new LinkedList<>();
	private static List<TickableObject> delayedTicks = new LinkedList<>();

	public static void tick(){
		for(TickableObject tick : new LinkedList<>(delayedTicks)){
			if(tick!=null)
			tick.tick();
		}
		delayedTicks.clear();
		for(TickableObject tick : new LinkedList<>(ticks)){
			if(tick!=null)
			tick.tick();
		}
	}

	public static void registerTickableObject(TickableObject tickableObject){
		ticks.add(tickableObject);
	}
	public static void removeTickableObject(TickableObject tickableObject){ticks.remove(tickableObject);}

	public static void runNextTick(TickableObject tickableObject) {
		delayedTicks.add(tickableObject);
	}
}

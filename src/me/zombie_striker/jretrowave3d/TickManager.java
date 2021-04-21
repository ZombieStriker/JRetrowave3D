package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.TickableObject;

import java.util.ArrayList;
import java.util.List;

public class TickManager {

	private static List<TickableObject> ticks = new ArrayList<>();
	private static List<TickableObject> delayedTicks = new ArrayList<>();

	public static void tick(){
		for(TickableObject tick : new ArrayList<>(delayedTicks)){
			tick.tick();
		}
		delayedTicks.clear();
		for(TickableObject tick : new ArrayList<>(ticks)){
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

package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.TickableObject;

import java.util.ArrayList;
import java.util.List;

public class TickManager {

	private static List<TickableObject> ticks = new ArrayList<>();

	public static void tick(){
		for(TickableObject tick : ticks){
			tick.tick();
		}
	}

	public static void registerTickableObject(TickableObject tickableObject){
		ticks.add(tickableObject);
	}
}

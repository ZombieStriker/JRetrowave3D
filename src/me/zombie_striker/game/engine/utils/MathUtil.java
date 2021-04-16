package me.zombie_striker.game.engine.utils;

import me.zombie_striker.game.engine.data.Location;

public class MathUtil {

	public static double distance(Location p1, Location p2) {
		return Math.sqrt(distanceSquared(p1, p2));
	}

	public static double distanceSquared(Location p1, Location p2) {
		int dif = 0;

		int x1 = (int) (p1.getX() - p2.getX());
		int z1 = (int) (p1.getZ() - p2.getZ());

		dif += (x1) * (x1);
		dif += (z1) * (z1);
		return dif;
	}
}

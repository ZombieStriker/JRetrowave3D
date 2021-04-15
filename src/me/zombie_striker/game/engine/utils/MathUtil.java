package me.zombie_striker.game.engine.utils;

import me.zombie_striker.game.engine.data.Location;

public class MathUtil {

	public static double distance(Location p1, Location p2){
		return Math.sqrt(distanceSquared(p1,p2));
	}
	public static double distanceSquared(Location p1, Location p2){
		int dif = 0;
		dif += (p1.getX()* p1.getX())-(p2.getX()*p2.getX());
		dif += (p1.getY()* p1.getY())-(p2.getY()*p2.getY());
		dif += (p1.getZ()* p1.getZ())-(p2.getZ()*p2.getZ());
		return dif;
	}
}

package me.zombie_striker.game.engine;

import me.zombie_striker.game.engine.data.Light;
import me.zombie_striker.game.engine.data.Triangle;
import me.zombie_striker.game.engine.data.Vector3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LightManager {

	public static Color getColorFromLightsources(Triangle t, World world){
		Vector3D normal = t.getNormal();
		Light closestLight = null;
		double closestdistance = Integer.MAX_VALUE;
		for(Light light : world.getLights()){
			double distance = -1;
			if(closestLight==null){
				closestdistance = light.getLocation().distanceSquared(t.getVertexes()[0]);
				closestLight = light;
			}else if(closestdistance > (distance=light.getLocation().distanceSquared(t.getVertexes()[0]))){
				closestdistance = distance;
				closestLight = light;
			}
		}
		if(closestLight==null){
			return t.getColor();
		}

		double dot = 1;

		double theta = Math.atan(normal.getX()/normal.getZ());

		dot = (Math.cos(theta)*normal.getZ()) - (Math.sin(theta)*normal.getX());

		if(normal.getX() > 0 && normal.getZ() > 0){
			if(normal.getX() > normal.getZ()){
				dot = -Math.cos(Math.cos(theta)*normal.getZ()) - (Math.sin(theta)*normal.getX());
			}else{

			}
		}

		System.out.println(theta);
		if(dot <  0){
			return new Color(0,0,0);
		}else{
			return new Color((int)Math.min(255,t.getColor().getRed()*dot),(int)Math.min(255,(t.getColor().getGreen()*dot)),(int)Math.min(255,(t.getColor().getBlue()*dot)));
		}
	}
}

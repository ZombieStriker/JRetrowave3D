package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.Light;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;

import java.awt.*;

public class LightManager {

	public static Color getColorFromLightsources(Triangle t, World world){
		Vector3D normal = t.getNormal(true);
		Light closestLight = null;
		double closestdistance = Integer.MAX_VALUE;
		for(Light light : world.getLights()){
			double distance = -1;
			if(closestLight==null){
				closestdistance = light.getLocation().distanceSquared(t.getExactLocation()[0]);
				closestLight = light;
			}else if(closestdistance > (distance=light.getLocation().distanceSquared(t.getExactLocation()[0]))){
				closestdistance = distance;
				closestLight = light;
			}
		}
		if(closestLight==null){
			return t.getColor();
		}

		double dot = 1;

		Vector3D direction = new Vector3D(closestLight.getLocation().getX() -t.getExactLocation()[0].getX(),
				0,//closestLight.getLocation().getY() -t.getTrues()[0].getY(),
				closestLight.getLocation().getZ() -t.getExactLocation()[0].getZ());

		direction.normalize();
		normal.normalize();

		//double theta = Math.atan(normal.getZ()/normal.getX());

		//double theta1 = Math.atan(normal.getX()/normal.getZ());
		//double theta2 = Math.atan(direction.getX()/direction.getZ());
		double theta1 = Math.asin(normal.getX());
		double theta2 = Math.asin(direction.getX());

		double theta = Math.PI/2;///*theta2+*/theta1;//+(Math.PI/4);




		if(Math.abs(direction.getZ()) > Math.abs(direction.getX())){
			dot = (Math.cos(theta) * direction.getX()) + (Math.sin(theta) * direction.getZ());
			dot *= (Math.cos(theta) * normal.getX()) - (Math.sin(theta) * normal.getZ());
		}else {
			dot = (Math.cos(theta) * direction.getZ()) + (Math.sin(theta) * direction.getX());
			dot *= (Math.cos(theta) * normal.getZ()) - (Math.sin(theta) * normal.getX());
		}

		dot=dot*(closestLight.getIntensity())/Math.sqrt(closestdistance);
		//System.out.println(theta);
		//System.out.println(dot+"       ||  "+theta + "        "+ direction.getX()+"      "+ direction.getZ());
		if(dot <  0){
			return new Color(0,0,0);
		}else{
			return new Color((int)Math.min(255,t.getColor().getRed()*dot),(int)Math.min(255,(t.getColor().getGreen()*dot)),(int)Math.min(255,(t.getColor().getBlue()*dot)));
		}
	}
}

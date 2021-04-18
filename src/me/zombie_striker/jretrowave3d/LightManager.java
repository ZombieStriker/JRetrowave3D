package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.Light;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;

import java.awt.*;

public class LightManager {

	public static Color getColorFromLightsources(Triangle t, World world){
		if(world.getLights().size()==0)
			return t.getColor();

		Vector3D normal = t.getNormal(true);
		//Light closestLight = null;
		//double closestdistance = Integer.MAX_VALUE;
		double dot=0;
		for(Light light : world.getLights()){
			//double distance;
			double tempdot=0;
			/*if(closestLight==null){
				closestdistance = light.getLocation().distanceSquared(t.getCenter());
				closestLight = light;
			}else */
			//if(closestdistance > (distance=light.getLocation().distanceSquared(t.getCenter()))){
			//	closestdistance = distance;
				tempdot = getBrightnessFromLight(t,world,light,normal, (light.getLocation().distanceSquared(t.getCenter())));
				if(tempdot > dot){
					//closestLight = light;
					dot += tempdot;
			//	}
			}
		}
		return new Color((int)Math.min(255,t.getColor().getRed()*dot),(int)Math.min(255,(t.getColor().getGreen()*dot)),(int)Math.min(255,(t.getColor().getBlue()*dot)));

	}

	private static double getBrightnessFromLight(Triangle t, World world, Light closestLight, Vector3D normal, double closestdistance){
		double dot = 1;

		Vector3D direction = new Vector3D(closestLight.getLocation().getX() -t.getExactLocation()[0].getX(),
				closestLight.getLocation().getY() -t.getExactLocation()[0].getY(),
				closestLight.getLocation().getZ() -t.getExactLocation()[0].getZ());

		direction.normalize();
		normal.normalize();

		//double theta = Math.atan(normal.getZ()/normal.getX());

		//double theta1 = Math.atan(normal.getX()/normal.getZ());
		//double theta2 = Math.atan(direction.getX()/direction.getZ());
		double theta1 = Math.asin(normal.getX());
		double theta2 = Math.asin(direction.getX());
		double theta3 = Math.asin(normal.getY());
		double theta4 = Math.asin(direction.getY());

		double theta = Math.PI/2;///*theta2+*/theta1;//+(Math.PI/4);




		if(Math.abs(direction.getZ()) > Math.abs(direction.getX())){
			dot = (Math.cos(theta) * direction.getX()) + (Math.sin(theta) * direction.getZ());
			dot *= (Math.cos(theta) * normal.getX()) - (Math.sin(theta) * normal.getZ());
			//dot *= /*(Math.cos(theta4) * direction.getZ())*/ - (Math.sin(theta3) * direction.getY());
			//dot *= (Math.cos(theta4) * normal.getY()) /*+ (Math.sin(theta3) * normal.getZ())*/;
		}else {
			dot = (Math.cos(theta) * direction.getZ()) + (Math.sin(theta) * direction.getX());
			dot *= (Math.cos(theta) * normal.getZ()) - (Math.sin(theta) * normal.getX());
			//dot *= (Math.cos(theta) * direction.getX()) - (Math.sin(theta) * direction.getY());
			//dot *= (Math.cos(theta) * normal.getY()) + (Math.sin(theta) * normal.getX());
		}

		dot=dot*(closestLight.getIntensity())/Math.sqrt(closestdistance);
		//System.out.println(theta);
		//System.out.println(dot+"       ||  "+theta + "        "+ direction.getX()+"      "+ direction.getZ());
		if(dot <  0){
			return 0;
		}else{
		return dot;
		}
	}
}

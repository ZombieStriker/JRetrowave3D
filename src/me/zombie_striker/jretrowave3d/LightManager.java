package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.Light;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;

import java.awt.*;
import java.util.HashMap;

public class LightManager {

	private static HashMap<Triangle, Color> cachedColor = new HashMap<>();

	public static void updateLights(){
		cachedColor.clear();
	}

	public static Color getColorFromLightsources(Triangle t, World world, boolean runOnGPU){
		if(world.getLights().size()==0)
			return t.getColor();

		Vector3D normal = t.getNormal(true);

		if(cachedColor.containsKey(t)){
			return cachedColor.get(t);
		}


		double dot=0;

		if(runOnGPU){
			//TODO :Figure out how to run on GPU
		}else {

			for (Light light : world.getLights()) {
				//double distance;
				double tempdot = 0;
				tempdot = getBrightnessFromLight(t, world, light, normal, (light.getLocation().distanceSquared(t.getCenter())));
				if (tempdot > dot) {
					//closestLight = light;
					dot += tempdot;
					//	}
				}
			}
		}
		Color color = new Color((int)Math.min(255,t.getColor().getRed()*dot),(int)Math.min(255,(t.getColor().getGreen()*dot)),(int)Math.min(255,(t.getColor().getBlue()*dot)));
		cachedColor.put(t,color);
		return color;

	}

	private static double getBrightnessFromLight(Triangle t, World world, Light closestLight, Vector3D normal, double closestdistance){
		double dot = 1;

		Vector3D direction = new Vector3D(closestLight.getLocation().getX() -t.getTrues()[0].getX(),
				closestLight.getLocation().getY() -t.getTrues()[0].getY(),
				closestLight.getLocation().getZ() -t.getTrues()[0].getZ());

		direction.normalize();
		normal.normalize();


		double theta = (Math.PI/2) ;///*theta2+*/theta1;//+(Math.PI/4);




		if(Math.abs(normal.getY()) > 0){
			if(Math.abs(direction.getZ()) > Math.abs(direction.getX())) {
				dot = (Math.cos(theta) * direction.getX()) + (Math.sin(theta) * direction.getY());
				dot *= (Math.cos(theta) * normal.getX()) - (Math.sin(theta) * normal.getY());
			}else{
				dot = (Math.cos(theta) * direction.getZ()) + (Math.sin(theta) * direction.getY());
				dot *= (Math.cos(theta) * normal.getZ()) - (Math.sin(theta) * normal.getY());
			}
		}else {
			if (Math.abs(direction.getZ()) > Math.abs(direction.getX())) {
				dot = (Math.cos(theta) * direction.getX()) + (Math.sin(theta) * direction.getZ());
				dot *= (Math.cos(theta) * normal.getX()) - (Math.sin(theta) * normal.getZ());
			} else {
				dot = (Math.cos(theta) * direction.getZ()) + (Math.sin(theta) * direction.getX());
				dot *= (Math.cos(theta) * normal.getZ()) - (Math.sin(theta) * normal.getX());
			}
		}
		dot=dot*(closestLight.getIntensity())/Math.sqrt(Math.sqrt(closestdistance));

		if(dot <  0){
			return 0;
		}else{
		return dot;
		}
	}

	public static void setTriangleToRelight(Triangle triangle) {
		cachedColor.remove(triangle);
	}
}

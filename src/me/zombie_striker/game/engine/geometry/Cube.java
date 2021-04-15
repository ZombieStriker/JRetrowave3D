package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.data.Plane;

public class Cube implements RenderableObject {

	private Plane[] sides = new Plane[6];

	public Cube(Location center, double width, double height, double length){
		Location toprightfront = new Location(center.getX()+width, center.getY()+height, center.getZ()+length);
		Location toprightback = new Location(center.getX()+width, center.getY()+height, center.getZ());
		Location topleftfront = new Location(center.getX(), center.getY()+height, center.getZ()+length);
		Location topleftback = new Location(center.getX(), center.getY()+height, center.getZ());

		Location bottomrightfront = new Location(center.getX()+width, center.getY(), center.getZ()+length);
		Location bottomrightback = new Location(center.getX()+width, center.getY(), center.getZ());
		Location bottomleftfront = new Location(center.getX(), center.getY(), center.getZ()+length);
		Location bottomleftback = new Location(center.getX(), center.getY(), center.getZ());

		sides[0]= new Plane(bottomleftfront,toprightfront);//front
		sides[1]= new Plane(bottomleftback,toprightback);//back
		sides[2]= new Plane(bottomleftback,topleftfront);//left
		sides[3]= new Plane(bottomrightback,toprightfront);//right
		//sides[4]= new Plane(topleftback,toprightfront);//top
		//sides[5]= new Plane(bottomleftfront,bottomrightfront);//bottom
	}

	@Override
	public Plane[] getObjectsToRender(World world) {
		Plane[] planes = new Plane[2];
		if(world.camera.getPersonLocation() .getX()< sides[2].getLocation().getX()){
			planes[0] = sides[2];
		}else{
			planes[0] = sides[3];
		}
		if(world.camera.getPersonLocation() .getZ()< sides[0].getLocation().getZ()){
			planes[1] = sides[1];
		}else{
			planes[1] = sides[0];
		}
		return planes;
	}
}

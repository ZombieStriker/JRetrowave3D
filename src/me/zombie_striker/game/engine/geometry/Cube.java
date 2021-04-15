package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.data.Plane;
import me.zombie_striker.game.engine.data.Triangle;

public class Cube implements RenderableObject {

	private Triangle[] sides = new Triangle[8];

	public Cube(Location center, double width, double height, double length){
		Location toprightfront = new Location(center.getX()+width, center.getY()+height, center.getZ()+length);
		Location toprightback = new Location(center.getX()+width, center.getY()+height, center.getZ());
		Location topleftfront = new Location(center.getX(), center.getY()+height, center.getZ()+length);
		Location topleftback = new Location(center.getX(), center.getY()+height, center.getZ());

		Location bottomrightfront = new Location(center.getX()+width, center.getY(), center.getZ()+length);
		Location bottomrightback = new Location(center.getX()+width, center.getY(), center.getZ());
		Location bottomleftfront = new Location(center.getX(), center.getY(), center.getZ()+length);
		Location bottomleftback = new Location(center.getX(), center.getY(), center.getZ());

		/*sides[0]= new Plane(bottomleftfront,toprightfront);//front
		sides[1]= new Plane(bottomleftback,toprightback);//back
		sides[2]= new Plane(bottomleftback,topleftfront);//left
		sides[3]= new Plane(bottomrightback,toprightfront);//right*/
		sides[0] = new Triangle(topleftfront,bottomleftfront,bottomrightfront); //front bottom
		sides[1] = new Triangle(topleftback,bottomleftback,bottomrightback); //back bottom
		sides[2] = new Triangle(topleftback,bottomleftback,bottomleftfront); //left bottom
		sides[3] = new Triangle(toprightback,bottomrightback,bottomrightfront); //right bottom

		sides[4] = new Triangle(topleftfront,toprightfront,bottomrightfront); //front top
		sides[5] = new Triangle(topleftback,toprightback,bottomrightback); //back top
		sides[6] = new Triangle(topleftback,topleftfront,bottomleftfront); // left top
		sides[7] = new Triangle(toprightback,toprightfront,bottomrightfront); // right top


		//sides[4]= new Plane(topleftback,toprightfront);//top
		//sides[5]= new Plane(bottomleftfront,bottomrightfront);//bottom
	}

	@Override
	public Triangle[] getObjectsToRender(World world) {
		/*Plane[] planes = new Plane[2];
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
		return planes;*/
		Triangle[] sides = new Triangle[4];
		if(world.camera.getPersonLocation().getX() < this.sides[2].getPoints()[0].getX()){
			sides[0]=this.sides[2];
			sides[1]=this.sides[6];
		}else if(world.camera.getPersonLocation().getX() > this.sides[3].getPoints()[0].getX()){
			sides[0]=this.sides[3];
			sides[1]=this.sides[7];
		}
		if(world.camera.getPersonLocation().getZ() > this.sides[0].getPoints()[0].getZ()){
			sides[2]=this.sides[0];
			sides[3]=this.sides[4];
		}else if(world.camera.getPersonLocation().getZ() < this.sides[1].getPoints()[0].getZ()){
			sides[2]=this.sides[1];
			sides[3]=this.sides[5];
		}
		return sides;
	}
}

package me.zombie_striker.game.engine.geometry;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.*;

import java.awt.*;
import java.util.Arrays;

public class Cube implements RenderableObject {

	private Triangle[] sides = new Triangle[8];
	private boolean[] hiddenFaces = new boolean[4];

	private Location bottomCorner;
	private Location topCorner;

	public Cube(Location center, double width, double height, double length){
		Location toprightfront = new Location(center.getX()+width, center.getY()+height, center.getZ()+length);
		Location toprightback = new Location(center.getX()+width, center.getY()+height, center.getZ());
		Location topleftfront = new Location(center.getX(), center.getY()+height, center.getZ()+length);
		Location topleftback = new Location(center.getX(), center.getY()+height, center.getZ());

		Location bottomrightfront = new Location(center.getX()+width, center.getY(), center.getZ()+length);
		Location bottomrightback = new Location(center.getX()+width, center.getY(), center.getZ());
		Location bottomleftfront = new Location(center.getX(), center.getY(), center.getZ()+length);
		Location bottomleftback = new Location(center.getX(), center.getY(), center.getZ());

		bottomCorner = bottomleftback;
		topCorner = toprightfront;


		/*sides[0]= new Plane(bottomleftfront,toprightfront);//front
		sides[1]= new Plane(bottomleftback,toprightback);//back
		sides[2]= new Plane(bottomleftback,topleftfront);//left
		sides[3]= new Plane(bottomrightback,toprightfront);//right*/
		sides[0] = new Triangle(topleftfront,bottomleftfront,bottomrightfront, new Color(248, 0, 0)); //front bottom
		sides[1] = new Triangle(topleftback,bottomleftback,bottomrightback, new Color(89, 255, 0)); //back bottom
		sides[2] = new Triangle(topleftback,bottomleftback,bottomleftfront, new Color(0, 28, 255)); //left bottom
		sides[3] = new Triangle(toprightback,bottomrightback,bottomrightfront, new Color(255, 192, 0)); //right bottom

		sides[4] = new Triangle(topleftfront,toprightfront,bottomrightfront, new Color(255, 0, 0)); //front top
		sides[5] = new Triangle(topleftback,toprightback,bottomrightback, new Color(11, 255, 0)); //back top
		sides[6] = new Triangle(topleftback,topleftfront,bottomleftfront, new Color(0, 70, 255)); // left top
		sides[7] = new Triangle(toprightback,toprightfront,bottomrightfront, new Color(255, 180, 0)); // right top


		//sides[4]= new Plane(topleftback,toprightfront);//top
		//sides[5]= new Plane(bottomleftfront,bottomrightfront);//bottom
	}
	public Cube(Location center, double width, double height, double length, Material textures){
		Material[] array = new Material[4];
		Arrays.fill(array,textures);
		init(center,width,height,length,array);
	}

	public void init(Location center, double width, double height, double length, Material[] textures){

		Location toprightfront = new Location(center.getX()+width, center.getY()+height, center.getZ()+length);
		Location toprightback = new Location(center.getX()+width, center.getY()+height, center.getZ());
		Location topleftfront = new Location(center.getX(), center.getY()+height, center.getZ()+length);
		Location topleftback = new Location(center.getX(), center.getY()+height, center.getZ());

		Location bottomrightfront = new Location(center.getX()+width, center.getY(), center.getZ()+length);
		Location bottomrightback = new Location(center.getX()+width, center.getY(), center.getZ());
		Location bottomleftfront = new Location(center.getX(), center.getY(), center.getZ()+length);
		Location bottomleftback = new Location(center.getX(), center.getY(), center.getZ());

		bottomCorner = bottomleftback;
		topCorner = toprightfront;
		/*sides[0]= new Plane(bottomleftfront,toprightfront);//front
		sides[1]= new Plane(bottomleftback,toprightback);//back
		sides[2]= new Plane(bottomleftback,topleftfront);//left
		sides[3]= new Plane(bottomrightback,toprightfront);//right*/
		sides[0] = new Triangle(toprightfront,bottomrightfront,bottomleftfront, textures[0]); //front bottom
		sides[1] = new Triangle(topleftback,bottomleftback,bottomrightback, textures[2]); //back bottom
		sides[2] = new Triangle(topleftfront,bottomleftfront,bottomleftback, textures[1]); //left bottom
		sides[3] = new Triangle(toprightback,bottomrightback,bottomrightfront, textures[3]); //right bottom

		sides[4] = new Triangle(topleftfront,toprightfront,bottomleftfront, textures[0]); //front top
		sides[5] = new Triangle(toprightback,topleftback,bottomrightback, textures[2]); //back top
		sides[6] = new Triangle(topleftfront,topleftback, bottomleftback,  textures[1]); // left top
		sides[7] = new Triangle(toprightback,toprightfront,bottomrightfront, textures[3]); // right top


		//sides[4]= new Plane(topleftback,toprightfront);//top
		//sides[5]= new Plane(bottomleftfront,bottomrightfront);//bottom
	}

	public Cube(Location center, double width, double height, double length, Material[] textures){
		init(center,width,height,length,textures);
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
		if(world.camera.getLocation().getX() +0.5<= this.sides[2].getPoints()[0].getX() || world.camera.getLocation().getX() +0.5<= this.sides[2].getPoints()[1].getX()){
			if(!hiddenFaces[0]) {
				sides[0] = this.sides[2];
				sides[1] = this.sides[6];
			}
		}else if(world.camera.getLocation().getX() +0.5>= this.sides[3].getPoints()[0].getX() || world.camera.getLocation().getX() +0.5>= this.sides[3].getPoints()[1].getX()){
			if(!hiddenFaces[1]) {
				sides[0] = this.sides[3];
				sides[1] = this.sides[7];
			}
		}
		if(world.camera.getLocation().getZ() +0.5>= this.sides[0].getPoints()[0].getZ() || world.camera.getLocation().getZ() +0.5>= this.sides[0].getPoints()[1].getZ()){
			if(!hiddenFaces[3]) {
				sides[2] = this.sides[0];
				sides[3] = this.sides[4];
			}
		}else if(world.camera.getLocation().getZ() +0.5<= this.sides[1].getPoints()[0].getZ() || world.camera.getLocation().getZ() +0.5<= this.sides[1].getPoints()[1].getZ()){
				if(!hiddenFaces[2]) {
					sides[2] = this.sides[1];
					sides[3] = this.sides[5];
				}
		}
		return sides;
	}

	@Override
	public boolean isInside(Location location,double size) {
		if(location.getX()-size <= topCorner.getX() && location.getX()+size >= bottomCorner.getX()){
			if(location.getY()-size <= topCorner.getY() && location.getY()+size >= bottomCorner.getY()){
				if(location.getZ()-size <= topCorner.getZ() && location.getZ()+size >= bottomCorner.getZ()){
					return true;
				}
			}

		}

		return false;
	}

	public void setHiddenFaces(BlockFace f, boolean b){
		hiddenFaces[f.getIndex()]=b;
	}
}

package me.zombie_striker.game.engine.data;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.utils.MathUtil;

import java.awt.*;

public class Triangle {

	private Location[] triangle = new Location[3];
	private Color color;
	private Material material;


	public Triangle(Location v1, Location v2, Location v3, Color color) {
		triangle[0]=v1;
		triangle[1]=v2;
		triangle[2]=v3;
		this.color = color;
	}
	public Triangle(Location v1, Location v2, Location v3, Material texture) {
		triangle[0]=v1;
		triangle[1]=v2;
		triangle[2]=v3;
		this.material = texture;
	}

	public Material getMaterial(){
		return material;
	}

	public Color getColor(){
		return color;
	}

	public Location[] getPoints() {
		return triangle;
	}

	public double getAverageDistanceSquared(World world){
		double dis = 0;
		dis += MathUtil.distanceSquared(triangle[0],world.camera.getLocation());
		dis += MathUtil.distanceSquared(triangle[1],world.camera.getLocation());
		dis += MathUtil.distanceSquared(triangle[2],world.camera.getLocation());
		return dis/3;
	}
	public double getAverageDistance(World world){
		return Math.sqrt(getAverageDistanceSquared(world));
	}
}

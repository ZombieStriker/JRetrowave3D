package me.zombie_striker.jretrowave3d.data;

import java.awt.*;

public class PolygonWrapper {

	private Polygon polygon;
	private int color;

	public PolygonWrapper(Polygon polygon,int color){
		this.polygon = polygon;
		this.color = color;
	}
	public int getColor(){
		return color;
	}
	public Polygon getPolygon(){
		return polygon;
	}
}

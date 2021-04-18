package me.zombie_striker.game.objects.physics;

import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.geometry.Cube;
import me.zombie_striker.jretrowave3d.physics.StaticObject;
import me.zombie_striker.jretrowave3d.physics.boundingbox.BoxBoundingBox;

public class CollidableBlock extends StaticObject {
	public CollidableBlock(World world, Vector3D min, double width, double height, double length) {
		super(world,min, new BoxBoundingBox(min,new Vector3D(min.getX()+width,min.getY()+height,min.getZ()+length)), new Cube(min,width,height,length));
	}
}

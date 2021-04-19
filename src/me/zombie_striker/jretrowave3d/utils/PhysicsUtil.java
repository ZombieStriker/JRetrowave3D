package me.zombie_striker.jretrowave3d.utils;

import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.physics.MovableObject;
import me.zombie_striker.jretrowave3d.physics.WorldObject;

import java.util.List;


public class PhysicsUtil {

	public static WorldObject getTargetWorldObjects(Vector3D startlocation, Vector3D direction, double maxdistance, List<WorldObject> boundingBoxArray) {
		Vector3D start = new Vector3D(startlocation);
		Vector3D step = new Vector3D(direction);
		step.normalize();
		step.multiply(0.01);
		for (double test = 0; test < maxdistance; test += 0.01) {
			start.add(step);
			boxloop:
			for (WorldObject box : boundingBoxArray) {
				if (box.getBoundingBox().collides(step)) {
					return box;
				}
			}
		}
		return null;
	}
	public static WorldObject getTargetMovableObjects(Vector3D startlocation, Vector3D direction, double maxdistance, List<MovableObject> boundingBoxArray) {
		Vector3D check = new Vector3D(startlocation);
		Vector3D step = new Vector3D(direction);
		step.normalize();
		step.multiply(0.01);
		for (double test = 0; test < maxdistance; test += 0.01) {
			check.add(step);
			boxloop:
			for (WorldObject box : boundingBoxArray) {
				if (box.getBoundingBox().collides(check)) {
					return box;
				}
			}
		}
		return null;
	}
}

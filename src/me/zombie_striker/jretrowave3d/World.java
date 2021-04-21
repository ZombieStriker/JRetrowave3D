package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.*;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.physics.boundingbox.BoundingBox;
import me.zombie_striker.jretrowave3d.utils.Draw;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

public class World {

	public Camera camera;
	private Set<RenderableObject> toRender = new HashSet<>();
	private Set<Light> lights = new HashSet<>();
	private Set<BoundingBox> boundingBoxes = new HashSet<>();

	public World(double x, double y, int z) {
		camera = new Camera(new Vector3D(x, y, z));
	}

	public void registerBoundingBox(BoundingBox box) {
		boundingBoxes.add(box);
	}

	public void registerObjectToRender(RenderableObject object) {
		this.toRender.add(object);
	}

	public RenderableObject renderCollidesWith(Vector3D location) {
		return renderCollidesWith(location, 0);
	}

	public RenderableObject renderCollidesWith(Vector3D location, double size) {
		for (RenderableObject r : toRender) {
			if (r.isInside(location, size))
				return r;
		}
		return null;
	}

	public void render(ScreenWrapper renderto) {
		ScreenWrapper bi = renderto;

		HashMap<Triangle, Double> triangleMap = new HashMap<>();
		HashMap<Triangle, Double> triangleHeightDifMap = new HashMap<>();
		List<Triangle> shouldDrawRelToScreen = new ArrayList<>();
		for (RenderableObject renderableObject : new ArrayList<>(toRender)) {
			for (Triangle t : renderableObject.getTrianglesForRendering(this)) {
				if (t != null) {
					//TODO: TESTING
					boolean pointInFieldOfView = false;
					Direction sideOfScreen = null;
					for (Vector3D loc : t.getPoints()) {
						if (loc.getZ() >= camera.getLocation().getZ()) {
							if (sideOfScreen == null) {
								if (loc.getX() >= camera.getLocation().getX()) {
									sideOfScreen = Direction.RIGHT;
								} else {
									sideOfScreen = Direction.LEFT;
								}
							}
							double theta = Math.atan((camera.getLocation().getX() - loc.getX()) / (camera.getLocation().getZ() - loc.getZ()));
							if (theta - (camera.getFOV() / 2) >= 0 && theta - (camera.getFOV() / 2) <= camera.getFOV()) {
								continue;
							} else if (-theta - (camera.getFOV() / 2) >= 0 && -theta - (camera.getFOV() / 2) <= camera.getFOV()) {
								continue;
							}
							if ((loc.getX() >= camera.getLocation().getX()) != (sideOfScreen == Direction.RIGHT)) {
								continue;
							}
							pointInFieldOfView = true;
							break;
						}
						//}
					}
					if (pointInFieldOfView) {
						triangleMap.put(t, t.getClosestDistance(this));
						triangleHeightDifMap.put(t, Math.abs(t.getCenter().getY() - camera.getLocation().getY()));
						if (renderableObject.isRelativeToScreen())
							shouldDrawRelToScreen.add(t);
					}
				}
			}
		}
		List<Map.Entry<Triangle, Double>> list = new LinkedList<Map.Entry<Triangle, Double>>(triangleMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Triangle, Double>>() {
			@Override
			public int compare(Map.Entry<Triangle, Double> o1, Map.Entry<Triangle, Double> o2) {
				if (false) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					if (o2.getValue().equals(o1.getValue())) {
						if (!triangleHeightDifMap.get(o1.getKey()).equals(triangleHeightDifMap.get(o2.getKey()))) {
							return triangleHeightDifMap.get(o2.getKey()).compareTo(triangleHeightDifMap.get(o1.getKey()));
						}
					}
					return o2.getValue().compareTo(o1.getValue());
				}
			}
		});
		Map<Triangle, Double> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<Triangle, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		for (Triangle plane : sortedMap.keySet()) {
			if (plane != null) {
				Draw.drawTriangle(renderto, this, (int) (((bi.getWidth() / 2))), (int) ((bi.getHeight() / 2)), plane, false, !shouldDrawRelToScreen.contains(plane));
			}
		}
	}

	public Set<Light> getLights() {
		return lights;
	}

	public Set<RenderableObject> getRenderableObjects() {
		return toRender;
	}

	public Set<BoundingBox> getBoundingBoxes() {
		return boundingBoxes;
	}

	public void registerLight(Light light) {
		this.lights.add(light);
	}

	public void removeBoundingBox(BoundingBox boundingBox) {
		this.boundingBoxes.remove(boundingBox);
	}

	public void removeToRender(RenderableObject renderableObject) {
		this.toRender.remove(renderableObject);
	}

	public boolean boundingBoxCollidesWith(BoundingBox box) {
		for(BoundingBox boundingBox : boundingBoxes){
			if(boundingBox.collides(box))
				return true;
		}
		return false;
	}
}

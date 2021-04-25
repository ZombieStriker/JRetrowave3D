package me.zombie_striker.jretrowave3d.v1;

import me.zombie_striker.jretrowave3d.data.*;
import me.zombie_striker.jretrowave3d.v1.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.v1.rendering.Screen;
import me.zombie_striker.jretrowave3d.v1.rendering.TriangleRenderer;
import me.zombie_striker.jretrowave3d.v1.physics.boundingbox.BoundingBox;
import me.zombie_striker.jretrowave3d.v1.utils.Draw;
import me.zombie_striker.jretrowave3d.v1.data.Light;
import me.zombie_striker.jretrowave3d.v1.data.ObjectChain;
import me.zombie_striker.jretrowave3d.v1.data.Triangle;
import me.zombie_striker.jretrowave3d.v1.data.Vector3D;

import java.util.*;

public class World {

	public Camera camera;
	private Set<RenderableObject> toRender = new HashSet<>();
	private Set<Light> lights = new HashSet<>();
	private Set<BoundingBox> boundingBoxes = new HashSet<>();

	public World(float x, float y, float z) {
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

	public RenderableObject renderCollidesWith(Vector3D location, float size) {
		for (RenderableObject r : toRender) {
			if (r.isInside(location, size))
				return r;
		}
		return null;
	}

	public ObjectChain<TriangleRenderer> render(Screen screen, ObjectChain<TriangleRenderer> startRenderChain, ObjectChain<TriangleRenderer> currentChain) {
		HashMap<Triangle, Float> triangleMap = new HashMap<>();
		HashMap<Triangle, Float> triangleHeightDifMap = new HashMap<>();
		List<Triangle> shouldDrawRelToScreen = new ArrayList<>();
		for (RenderableObject renderableObject : new ArrayList<>(toRender)) {
			for (Triangle t : renderableObject.getTrianglesForRendering(this)) {
				if (t != null) {
					//TODO: TESTING
					boolean pointInFieldOfView = false;
					for (Vector3D loc : t.getPoints()) {
						if (loc.getZ() >= camera.getLocation().getZ()) {
							double theta = Math.atan((camera.getLocation().getX() - loc.getX()) / (camera.getLocation().getZ() - loc.getZ()));
							/*if(!renderableObject.isRelativeToScreen()) {
								if (Math.sin(camera.getFOV() / 2) * loc.distance(camera.getLocation()) < loc.getX() - camera.getDirection().getX())
									continue;
								if (Math.sin(camera.getFOV() / 2) * loc.distance(camera.getLocation()) < camera.getDirection().getX() - loc.getX())
									continue;
							}*/
							Vector3D normal = t.getNormal(false).normalize();

							if (theta - (camera.getFOV() / 2) >= 0 && theta - (camera.getFOV() / 2) <= camera.getFOV()) {
								continue;
							} else if (-theta - (camera.getFOV() / 2) >= 0 && -theta - (camera.getFOV() / 2) <= camera.getFOV()) {
								continue;
							}
							pointInFieldOfView = true;
							break;
						}
						//}
					}
					if (pointInFieldOfView) {
						triangleMap.put(t, t.getCenterDistance(this));
						triangleHeightDifMap.put(t, Math.abs(t.getCenter().getY() - camera.getLocation().getY()));
						if (renderableObject.isRelativeToScreen())
							shouldDrawRelToScreen.add(t);
					}
				}
			}
		}
		List<Map.Entry<Triangle, Float>> list = new LinkedList<Map.Entry<Triangle, Float>>(triangleMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Triangle, Float>>() {
			@Override
			public int compare(Map.Entry<Triangle, Float> o1, Map.Entry<Triangle, Float> o2) {
				if (true) {
					if (o2.getValue().equals(o1.getValue())) {
						if (!triangleHeightDifMap.get(o1.getKey()).equals(triangleHeightDifMap.get(o2.getKey()))) {
							return triangleHeightDifMap.get(o1.getKey()).compareTo(triangleHeightDifMap.get(o2.getKey()));
						}
					}
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
		Map<Triangle, Float> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<Triangle, Float> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		ObjectChain<TriangleRenderer> cur = currentChain;

		if (screen != null)
			for (Triangle triangle : sortedMap.keySet()) {
				if (triangle != null) {
					cur = Draw.drawTriangle(screen, cur, this, triangle, !shouldDrawRelToScreen.contains(triangle));
				}
			}
		return cur;
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
		for (BoundingBox boundingBox : boundingBoxes) {
			if (box.collides(boundingBox))
				return true;
		}
		return false;
	}
}

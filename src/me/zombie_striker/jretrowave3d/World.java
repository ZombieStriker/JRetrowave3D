package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.Direction;
import me.zombie_striker.jretrowave3d.data.Light;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.physics.boundingbox.BoundingBox;
import me.zombie_striker.jretrowave3d.utils.Draw;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

public class World {

	private Set<RenderableObject> toRender = new HashSet<>();
	private Set<Light> lights = new HashSet<>();
	private Set<BoundingBox> boundingBoxes = new HashSet<>();

	public void registerBoundingBox(BoundingBox box){
		boundingBoxes.add(box);
	}
	public void registerObjectToRender(RenderableObject object){
		this.toRender.add(object);
	}

	public Camera camera;

	public World(double x, double y) {
		camera = new Camera(new Vector3D(x, 1, y));
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

	public void render(BufferedImage renderto) {
		BufferedImage bi = renderto;
		Graphics2D screen = bi.createGraphics();

		screen.setColor(new Color(100, 100, 100));
		screen.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		//screen.setColor(new Color(45, 45, 45));
		//screen.fillRect(0, bi.getHeight() / 2 + (bi.getHeight() / 80), bi.getWidth(), bi.getHeight());

		/*screen.setColor(new Color(183, 10, 238));
		for (int line = 1; line < 80; line++) {
			screen.fillRect(0, bi.getHeight() / 2 + bi.getHeight() / line, bi.getWidth(), 2);
		}*/

		HashMap<Triangle, Double> triangleMap = new HashMap<>();
		HashMap<Triangle, Double> triangleHeightDifMap = new HashMap<>();
		for (RenderableObject renderableObject : toRender) {
			for (Triangle t : renderableObject.getTrianglesForRendering(this)) {
				if (t != null) {
					//TODO: TESTING
					boolean pointInFieldOfView = false;
					Direction sideOfScreen = null;
					for (Vector3D loc : t.getPoints()) {
						/*if (camera.getYaw() > 45 && camera.getYaw() <= 135) {
							if (loc.getX() <= camera.getLocation().getX()) {
								pointInFieldOfView = true;
								break;
							}
						} else if (camera.getYaw() > 135 && camera.getYaw() <= 225) {
							if (loc.getZ() <= camera.getLocation().getZ()) {
								pointInFieldOfView = true;
								break;
							}
						} else if (camera.getYaw() > 225 && camera.getYaw() <= 315) {
							if (loc.getX() >= camera.getLocation().getX()) {
								pointInFieldOfView = true;
								break;
							}
						} else {*/
							if (loc.getZ() >= camera.getLocation().getZ()) {
								if(sideOfScreen == null){
									if(loc.getX() >= camera.getLocation().getX()){
										sideOfScreen=Direction.RIGHT;
									}else{
										sideOfScreen=Direction.LEFT;
									}
								}
								double theta = Math.atan((camera.getLocation().getX()-loc.getX())/(camera.getLocation().getZ()-loc.getZ()));
								if(theta-(camera.getFOV()/2) >= 0 && theta-(camera.getFOV()/2)<=camera.getFOV()) {
									continue;
								}else if(-theta-(camera.getFOV()/2) >= 0 && -theta-(camera.getFOV()/2)<=camera.getFOV()){
										continue;
								}
								if((loc.getX() >= camera.getLocation().getX()) != (sideOfScreen==Direction.RIGHT) ){
									continue;
								}
								pointInFieldOfView = true;
								break;
							}
						//}
					}
					if (pointInFieldOfView) {
						triangleMap.put(t, t.getClosestDistance(this));
						triangleHeightDifMap.put(t,Math.abs(t.getCenter().getY()-camera.getLocation().getY()));
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
					if(o2.getValue().equals(o1.getValue())) {
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

//renderableObject.getObjectsToRender(this)

		for (Triangle plane : sortedMap.keySet()) {
			if (plane != null) {
				Draw.drawTriangle(bi, screen, this, (int) (((bi.getWidth() / 2))), (int) ((bi.getHeight() / 2)), plane, true);
			}
		}
		//Triangle triangle = new Triangle(new Location(1,1,0),new Location2D(0,0),new Location2D(0,50), new Location2D(25,0));

		//Draw.drawTriangle(screen,renderto.getWidth()/2,renderto.getHeight()/2,triangle, new Color(255,0,0));


		screen.dispose();
	}

	public Set<Light> getLights() {
		return lights;
	}
	public Set<RenderableObject> getRenderableObjects(){
		return toRender;
	}
	public Set<BoundingBox> getBoundingBoxes(){
		return boundingBoxes;
	}

	public void registerLight(Light light) {
		this.lights.add(light);
	}
}

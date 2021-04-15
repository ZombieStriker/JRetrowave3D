package me.zombie_striker.game.engine;

import me.zombie_striker.game.engine.data.Location;
import me.zombie_striker.game.engine.data.Triangle;
import me.zombie_striker.game.engine.geometry.RenderableObject;
import me.zombie_striker.game.engine.utils.Draw;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class World {

	public List<RenderableObject> toRender = new ArrayList<>();

	public Camera camera = new Camera(new Location(0, 1, 0), new Location(-1, 1, 0));

	public void render(BufferedImage renderto) {
		BufferedImage bi = renderto;
		Graphics2D screen = bi.createGraphics();

		screen.setColor(new Color(100, 100, 100));
		screen.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		screen.setColor(new Color(45, 45, 45));
		screen.fillRect(0, bi.getHeight() / 2 + (bi.getHeight() / 80), bi.getWidth(), bi.getHeight());

		screen.setColor(new Color(183, 10, 238));
		for (int line = 1; line < 80; line++) {
			screen.fillRect(0, bi.getHeight() / 2 + bi.getHeight() / line, bi.getWidth(), 2);
		}


		for (RenderableObject renderableObject : toRender) {
			for (Triangle plane : renderableObject.getObjectsToRender(this)) {
				if (plane != null) {

					double cos = Math.cos(Math.toRadians(camera.getYaw()));
					double sin = Math.sin(Math.toRadians(camera.getYaw()));


					double personToCameraOffsetX = camera.getApertureLocation().getX() - camera.getPersonLocation().getX();// testing should be 1
					double personToCameraOffsetZ = camera.getApertureLocation().getZ() - camera.getPersonLocation().getZ(); //testing should be 0


					double fovcos = Math.cos(Math.toRadians(camera.getFOV()));
					double fovsin = Math.sin(Math.toRadians(camera.getFOV()));


					double halfheight = bi.getHeight() / 2;

						/*Triangle triangle3 = new Triangle(plane.getLocation(),
								new Location2D((int) (x2offz2), (int) (bi.getHeight() / zdif2)),
								new Location2D((int) (xoffz), (int) (bi.getHeight() / zdif)),
								new Location2D((int) (x2offz2 ), (int) ((-(ydif2 * halfheight) / zdif2) - (bi.getHeight() / zdif2)))
						);*/
					Draw.drawTriangle(bi, screen, this, (int) (((bi.getWidth() / 2))), (int) ((bi.getHeight() / 2)), plane, new Color(255, 255, 0), false);


						/*Triangle triangle2 = new Triangle(plane.getLocation(),
								new Location2D((int) (xoffz), (int) ((-(ydif2 * halfheight) / zdif) - (bi.getHeight() / zdif))),
								new Location2D((int) (xoffz), (int) (bi.getHeight() / zdif)),
								new Location2D((int) (x2offz2), (int) ((-(ydif2 * halfheight) / zdif2) - (bi.getHeight() / zdif2)))
						);
						Draw.drawTriangle(bi,screen, (int) (((bi.getWidth() / 2))), (int) ((bi.getHeight() / 2)), triangle2, new Color(236, 81, 10),true);
						*/
					/*	if (xdif != xdif2 && zdif != zdif2) {
							Triangle triangle = new Triangle(plane.getLocation(),
									new Location2D((int) (cos * x2offz2 + (sin * x2off)), (int) (cos * bi.getHeight() / zdif2)),
									new Location2D((int) (cos * xoffz + (sin * xoffz2)), (int) (cos * bi.getHeight() / zdif)),
									new Location2D((int) (cos * x2offz2 + (sin * x2offz2)), (int) (cos * (-(ydif2 * halfheight) / zdif2) + (bi.getHeight() / zdif2)))
							);
							Draw.drawTriangle(screen, (int) (((bi.getWidth() / 2))), (int) ((bi.getHeight() / 2)), triangle, null);
						} else if (xdif == xdif2) {
							Triangle triangle = new Triangle(plane.getLocation(),
									new Location2D((int) x2off, (int) (bi.getHeight() / zdif)),
									new Location2D((int) xoffz2, (int) (-ydif2 * (((halfheight) / zdif2)) + ((bi.getHeight() / zdif2)))),
									new Location2D((int) x2offz2, (int) (bi.getHeight() / zdif2))
							);
							Draw.drawTriangle(screen, (int) (((bi.getWidth() / 2))), (int) ((bi.getHeight() / 2)), triangle, null);
						} else {
							Triangle triangle = new Triangle(plane.getLocation(),
									new Location2D((int) (x2offz2), (int) (bi.getHeight() / zdif)),
									new Location2D((int) (xoffz), (int) (bi.getHeight() / zdif)),
									new Location2D((int) (x2offz2), (int) ((-(ydif2 * halfheight) / zdif) + (bi.getHeight() / zdif)))
							);
							Draw.drawTriangle(screen, (int) (((bi.getWidth() / 2))), (int) ((bi.getHeight() / 2)), triangle, null);
						}*/
				}
			}
		}
		//Triangle triangle = new Triangle(new Location(1,1,0),new Location2D(0,0),new Location2D(0,50), new Location2D(25,0));

		//Draw.drawTriangle(screen,renderto.getWidth()/2,renderto.getHeight()/2,triangle, new Color(255,0,0));


		screen.dispose();
	}
}
package me.zombie_striker.game.engine.utils;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Material;
import me.zombie_striker.game.engine.data.TextureStitchingAlgo;
import me.zombie_striker.game.engine.data.Triangle;
import me.zombie_striker.game.engine.data.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Draw {


	public static void drawLine(Graphics2D bi, int startX, int startY, int endX, int endY) {
		double slope = 1;

		if (startX == endX) {
			int height = Math.max(endY, startY) - Math.min(startY, endY);
			if (startY < endY) {
				bi.fillRect(startX, startY, 1, height);
			} else {
				bi.fillRect(startX, endY, 1, height);
			}
			return;
		}


		//if (startX < endX) {
		slope = (0.0 + endY - startY) / (endX - startX);
		/*} else {
			slope = (0.0 + startY - endY) / (startX - endX);
		}*/

		if (slope == 0) {
			if (startX < endX) {
				bi.fillRect(startX, startY, endX - startX, 1);
			} else {
				bi.fillRect(endX, startY, startX - endX, 1);
			}
		} else {


			if (startX < endX) {
				for (int x1 = startX; x1 < endX; x1++) {
					double steps = ((x1 - endX) * slope);
					if (slope < 0) {
						if (endY + (int) ((x1 - endX) * slope) + (-slope + 1) <= startY) {
							bi.fillRect(x1, endY + (int) steps, (1), (int) (-slope + 1));
						} else {
							int size = (int) (endY + (int) steps + (-slope) - startY);
							bi.fillRect(x1 + 1, endY + (int) steps - size, (1), size);
						}
					} else {
						int size = (int) Math.min(slope + 1, endY - (startY + steps));
						bi.fillRect(x1, startY + (int) ((x1 - startX) * slope), (1), size);
					}
				}
			} else {
				for (int x1 = endX; x1 < startX; x1++) {
					double steps = ((x1 - endX) * slope);

					if (slope < 0) {
						bi.fillRect(x1, endY + (int) steps, (1), (int) (-slope + 1));
					} else {
						int size = (int) Math.min(slope + 1, endY - (startY + steps));
						bi.fillRect(x1, startY + (int) steps, (1), size);
					}
				}

			}
		}
	}


	public static void fillTriangle(BufferedImage bi, Graphics2D g, int xoff, int yoff, Vector2D p1, Vector2D p2, Vector2D p3, Material material) {
		int[] x = new int[3];
		int[] y = new int[3];
		int n;
		// A simple triangle.
		x[0] = (int) (xoff + p1.getX());
		x[1] = (int) (xoff + p2.getX());
		x[2] = (int) (xoff + p3.getX());
		y[0] = (int) (yoff + p1.getY());
		y[1] = (int) (yoff + p2.getY());
		y[2] = (int) (yoff + p3.getY());

		int bix = 0;
		int biy = 0;
		int bixmin = Integer.MAX_VALUE;
		int biymin = Integer.MAX_VALUE;
		for (int temp : x) {
			if (temp > bix)
				bix = temp;
			if (temp < bixmin)
				bixmin = temp;
		}
		for (int temp : y) {
			if (temp > biy)
				biy = temp;
			if (temp < biymin)
				biymin = temp;
		}


		if (bix <= 0 || biy <= 0) {
			return;
		}
		if (bix - bixmin <= 0 || biy - biymin <= 0) {
			return;
		}

		n = 3;

		Polygon p = new Polygon(x, y, n);  // This polygon represents a triangle with the above
		//   vertices.
		//g.fillPolygon(p);
		//graphics2D.drawPolygon(p);

		//g2.fillPolygon(p);

		if (material != null) {

			TextureStitchingAlgo tse = TextureStitchingAlgo.DEFAULT_BOTTOM;
			int p1index = tse.getIndex1();
			int p2index = tse.getIndex2();
			int p3index = tse.getIndex3();
			double slope = (((double) (y[p2index] - y[p1index]) / (x[p2index] - x[p1index])));
			double slope2 = 0;//(((double) (y[p2index] - y[p3index]) / (x[p2index] - x[p3index])));
			double slope3 = (((double) (y[p3index] - y[p1index]) / (x[p3index] - x[p1index])));
			if (Double.isInfinite(slope)) {
				tse = TextureStitchingAlgo.DEFAULT;
				p1index = tse.getIndex1();
				p2index = tse.getIndex2();
				p3index = tse.getIndex3();
				slope = (((double) (y[p2index] - y[p1index]) / (x[p2index] - x[p1index])));
				slope2 = 0;///(((double) (y[p2index] - y[p3index]) / (x[p2index] - x[p3index])));
				slope3 = (((double) (y[p3index] - y[p1index]) / (x[p3index] - x[p1index])));
			}
			BufferedImage resize = null;
			try {
				resize = resize(material.getMaterial(), bix - bixmin, biy - biymin, slope, slope2, slope3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(resize, 0, 0, null);
			for (int x1 = bixmin; x1 < bix; x1++) {
				for (int y1 = biymin; y1 < biy; y1++) {
					if (p.contains(x1, y1)) {
						int y2 = (int) ((y1 - biymin));//* (slope <= 0 ? slope*(bixmin-x1) : slope*(x1-bixmin)));
						if (x1 >= 0 && y2 >= 0 && x1 - bixmin < resize.getWidth() && y2 < resize.getHeight()) {
							int rgb = resize.getRGB(x1 - bixmin, y2);
							if (x1 >= 0 && y1 >= 0 && x1 < bi.getWidth() && y1 < bi.getHeight()) {
								bi.setRGB(x1, y1, rgb);
							}
						}
					}
				}
			}
		} else {
			g.fillPolygon(p);
		}


	}


	public static BufferedImage resize(BufferedImage img,
									   int width, int height, double slope, double slope2, double slope3) throws IOException {
		int scaledWidth = width, scaledHeight = height;

		Image resized = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_FAST);

		BufferedImage buffered = new BufferedImage(scaledWidth, scaledHeight, Image.SCALE_REPLICATE);

		buffered.getGraphics().drawImage(resized, 0, 0, null);

		BufferedImage interoperated = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
		if (Double.isFinite(slope))
			for (double x = 0; x < buffered.getWidth(); x++) {

				for (double y = 0; y < buffered.getHeight(); y += 1) {

					int y1 = (int) (y + (slope3 * x));
					int y2 = (int) (y + (slope3 * x));
					int y12 = (int) (y * (((double) buffered.getHeight() - (x * slope3)) / buffered.getHeight()));
					int y22 = (int) (y * (((double) buffered.getHeight() - (x * slope3)) / buffered.getHeight()));


					if (y1 >= 0 && y1 < buffered.getHeight())
						if (y2 >= 0 && y2 < buffered.getHeight())
							if (y12 >= 0 && y12 < buffered.getHeight())
								if (y22 >= 0 && y22 < buffered.getHeight())
									if (slope > 0) {
										interoperated.setRGB((int) x, y2, buffered.getRGB((int) x, y12));
									} else {
										interoperated.setRGB((int) x, y1, buffered.getRGB((int) x, y22));
									}
				}
			}
		return interoperated;
	}


	public static void drawTriangle(BufferedImage bi, Graphics2D g, World world, int x, int y, Triangle triangle, boolean istop) {

		if (triangle.getColor() != null)
			g.setColor(triangle.getColor());
		double cosy = Math.cos(world.camera.getYawRadians());
		double siny = Math.sin(world.camera.getYawRadians());

		double cosp = Math.cos(world.camera.getPitchRadians());
		double sinp = Math.sin(world.camera.getPitchRadians());

		double cosr = Math.cos(world.camera.getRollRadians());
		double sinr = Math.sin(world.camera.getRollRadians());

		/*double cosPitch = Math.cos(world.camera.getPitchRadians());
		double sinPitch = Math.sin(world.camera.getPitchRadians()); // Pitch is the altitude of the forward vector off the xy plane, toward the down direction.
		double pitch = -Math.asin(world.camera.getPitchRadians() - (Math.PI/2));*/


		//System.out.println(cosPitch);
		//System.out.println(pitch);

		double zdifA = (triangle.getPoints()[0].getZ() - world.camera.getLocation().getZ());
		double ydifA = (triangle.getPoints()[0].getY() - world.camera.getLocation().getY());
		double xdifA = (triangle.getPoints()[0].getX() - world.camera.getLocation().getX());

		double zdifB = (triangle.getPoints()[1].getZ() - world.camera.getLocation().getZ());
		double ydifB = (triangle.getPoints()[1].getY() - world.camera.getLocation().getY());
		double xdifB = (triangle.getPoints()[1].getX() - world.camera.getLocation().getX());

		double zdifC = (triangle.getPoints()[2].getZ() - world.camera.getLocation().getZ());
		double ydifC = (triangle.getPoints()[2].getY() - world.camera.getLocation().getY());
		double xdifC = (triangle.getPoints()[2].getX() - world.camera.getLocation().getX());


		double zxdistance1 = (cosy * zdifA) - (siny * xdifA); // Col 3
		double xzdistance1 = (cosy * xdifA) + (siny * zdifA); //Col 2
		double zxdistance2 = (cosy * zdifB) - (siny * xdifB);
		double xzdistance2 = (cosy * xdifB) + (siny * zdifB);
		double zxdistance3 = (cosy * zdifC) - (siny * xdifC);
		double xzdistance3 = (cosy * xdifC) + (siny * zdifC);

		double x1 = (((xdifA) / (zdifA)));
		double x2 = (((xdifB) / (zdifB)));
		double x3 = (((xdifC) / (zdifC)));
		/*double x1 = (((xzdistance1) / (zxdistance1)));
		double x2 = (((xzdistance2) / (zxdistance2)));
		double x3 = (((xzdistance3) / (zxdistance3)));*/

		double y1 = ((((-ydifA / (zdifA)))));
		double y2 = ((((-ydifB / (zdifB)))));
		double y3 = ((((-ydifC / (zdifC)))));
		/*double y1 = ((((-ydifA / (zxdistance1)))));
		double y2 = ((((-ydifB / (zxdistance2)))));
		double y3 = ((((-ydifC / (zxdistance3)))));*/
		/*double x1 = (((xdif0) / (zdif0)));
		double x2 = (((xdif1) / (zdif1)));
		double x3 = (((xdif2) / (zdif2)));

		double y1 = ((((-ydifA / (zdif0)) )));
		double y2 = ((((-ydifB / (zdif1)) )));
		double y3 = ((((-ydifC / (zdif2)) )));*/


		//Location2D point1 = new Location2D(x1, y1+(((xyz[0][0]* bi.getHeight() / 8))));
		//Location2D point2 = new Location2D(x2, y2+((( xyz2[0][0]* bi.getHeight() / 8))));
		//Location2D point3 = new Location2D(x3, y3+((( xyz3[0][0]* bi.getHeight() / 8))));
		Vector2D point1 = new Vector2D(x1, y1);
		Vector2D point2 = new Vector2D(x2, y2);
		Vector2D point3 = new Vector2D(x3, y3);
		/*Vector2D point1 = new Vector2D( x1,  y1);
		Vector2D point2 = new Vector2D( x2, y2);
		Vector2D point3 = new Vector2D( x3,  y3);*/
		point1.multiply(bi.getHeight() / 2);
		point2.multiply(bi.getHeight() / 2);
		point3.multiply(bi.getHeight() / 2);

		if (point1.getX() > bi.getWidth())
			return;
		if (point1.getX() < -bi.getWidth())
			return;

		if (point2.getX() > bi.getWidth())
			return;
		if (point2.getX() < -bi.getWidth())
			return;

		if (point3.getX() > bi.getWidth())
			return;
		if (point3.getX() < -bi.getWidth())
			return;

		fillTriangle(bi, g, x, y, point1, point2, point3, triangle.getMaterial());

		//if (color == null)
		g.setColor(new Color(255, 0, 0));
		drawLine(g, (int) (point1.getX() + x), (int) (point1.getY() + y), (int) (point2.getX() + x), (int) (point2.getY() + y));
		//if (color == null)
		g.setColor(new Color(0, 255, 0));
		drawLine(g, (int) (point1.getX() + x), (int) (point1.getY() + y), (int) (point3.getX() + x), (int) (point3.getY() + y));
		//if (color == null)
		g.setColor(new Color(0, 0, 255));
		drawLine(g, (int) (point3.getX() + x), (int) (point3.getY() + y), (int) (point2.getX() + x), (int) (point2.getY() + y));
	}

}

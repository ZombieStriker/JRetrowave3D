package me.zombie_striker.game.engine.utils;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location2D;
import me.zombie_striker.game.engine.data.Material;
import me.zombie_striker.game.engine.data.TextureStitchingAlgo;
import me.zombie_striker.game.engine.data.Triangle;

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


	public static void fillTriangle(BufferedImage bi, Graphics2D g, int xoff, int yoff, Location2D p1, Location2D p2, Location2D p3, Material material) {
		int[] x = new int[3];
		int[] y = new int[3];
		int n;
		// A simple triangle.
		x[0] = xoff + p1.getX();
		x[1] = xoff + p2.getX();
		x[2] = xoff + p3.getX();
		y[0] = yoff + p1.getY();
		y[1] = yoff + p2.getY();
		y[2] = yoff + p3.getY();

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
					int y12 = (int) (y*(((double)buffered.getHeight()-(x*slope3))/buffered.getHeight()));
					int y22 = (int) (y*(((double)buffered.getHeight()-(x*slope3))/buffered.getHeight()));


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
		double cos = Math.cos(Math.toRadians(world.camera.getYaw()));
		double sin = Math.sin(Math.toRadians(world.camera.getYaw()));

		/*double cosPitch = Math.cos(world.camera.getPitchRadians());
		double sinPitch = Math.sin(world.camera.getPitchRadians()); // Pitch is the altitude of the forward vector off the xy plane, toward the down direction.
		double pitch = -Math.asin(world.camera.getPitchRadians() - (Math.PI/2));*/


		//System.out.println(cosPitch);
		//System.out.println(pitch);

		double zdif = cos * (triangle.getPoints()[0].getZ() - world.camera.getLocation().getZ()) - (sin * (triangle.getPoints()[0].getX() - world.camera.getLocation().getX()));
		double ydif = (triangle.getPoints()[0].getY() - world.camera.getLocation().getY());
		double xdif = cos * (triangle.getPoints()[0].getX() - world.camera.getLocation().getX()) + (sin * (triangle.getPoints()[0].getZ() - world.camera.getLocation().getZ()));

		double zdif1 = cos * (triangle.getPoints()[1].getZ() - world.camera.getLocation().getZ()) - (sin * (triangle.getPoints()[1].getX() - world.camera.getLocation().getX()));
		double ydif1 = (triangle.getPoints()[1].getY() - world.camera.getLocation().getY());
		double xdif1 = cos * (triangle.getPoints()[1].getX() - world.camera.getLocation().getX()) + (sin * (triangle.getPoints()[1].getZ() - world.camera.getLocation().getZ()));

		double zdif2 = cos * (triangle.getPoints()[2].getZ() - world.camera.getLocation().getZ()) - (sin * (triangle.getPoints()[2].getX() - world.camera.getLocation().getX()));
		double ydif2 = (triangle.getPoints()[2].getY() - world.camera.getLocation().getY());
		double xdif2 = cos * (triangle.getPoints()[2].getX() - world.camera.getLocation().getX()) + (sin * (triangle.getPoints()[2].getZ() - world.camera.getLocation().getZ()));

		//TODO: Look at this for heights
		/*double ydifAngle = Math.sin(world.camera.getPitchRadians());
		double ydifpitchX = ydifAngle/zdif*2;
		double ydifX = ydifpitchX*bi.getHeight()/2;

		double ydifAngle1 = Math.sin(world.camera.getPitchRadians());
		double ydifpitchX1 = ydifAngle1/zdif1*2;
		double ydifX1 = ydifpitchX1*bi.getHeight()/2;

		double ydifAngle2 = Math.sin(world.camera.getPitchRadians());
		double ydifpitchX2 = ydifAngle2/zdif2*2;
		double ydifX2 = ydifpitchX2*bi.getHeight()/2;*/




		Location2D point = new Location2D((int) ((xdif * bi.getHeight() / zdif)), (int) ( ( -(ydif * bi.getHeight() / zdif))));
		Location2D point2 = new Location2D((int) ((xdif1 * bi.getHeight() / zdif1)), (int) (-(ydif1 * bi.getHeight() / zdif1)));
		Location2D point3 = new Location2D((int) ((xdif2 * bi.getHeight() / zdif2)), (int) (-(ydif2 * bi.getHeight() / zdif2)));

		if (point.getX() > bi.getWidth())
			return;
		if (point.getX() < -bi.getWidth())
			return;

		if (point2.getX() > bi.getWidth())
			return;
		if (point2.getX() < -bi.getWidth())
			return;

		if (point3.getX() > bi.getWidth())
			return;
		if (point3.getX() < -bi.getWidth())
			return;

		fillTriangle(bi, g, x, y, point, point2, point3, triangle.getMaterial());

		//if (color == null)
		g.setColor(new Color(255, 0, 0));
		drawLine(g, point.getX() + x, point.getY() + y, point2.getX() + x, point2.getY() + y);
		//if (color == null)
		g.setColor(new Color(0, 255, 0));
		drawLine(g, point.getX() + x, point.getY() + y, point3.getX() + x, point3.getY() + y);
		//if (color == null)
		g.setColor(new Color(0, 0, 255));
		drawLine(g, point3.getX() + x, point3.getY() + y, point2.getX() + x, point2.getY() + y);
	}

}

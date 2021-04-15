package me.zombie_striker.game.engine.utils;

import me.zombie_striker.game.engine.World;
import me.zombie_striker.game.engine.data.Location2D;
import me.zombie_striker.game.engine.data.Triangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Draw {


	private static BufferedImage testTexture;

	static {
		try {
			testTexture = ImageIO.read(Draw.class.getResourceAsStream("/textures/test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


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


	public static void fillTriangle(BufferedImage bi, Graphics2D g, int xoff, int yoff, Location2D p1, Location2D p2, Location2D p3, boolean isTop) {
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
		g.fillPolygon(p);
		//graphics2D.drawPolygon(p);
/*
		int p1index = 0;
		int p2index = 1;
		int p3index = 2;
		if (!isTop) {
		} else {
			p1index = 0;
			p2index = 2;
			p3index = 1;
		}

		double slope = 0;
		if (isTop) {
			slope = ((double) y[p2index] - y[p1index]) / (x[p2index] - x[p1index]);
		} else {
			slope = ((double) y[p2index] - y[p1index]) / (x[p2index] - x[p1index]);
		}

		double slope2 = 0;
		if (isTop) {
			slope2 = ((double) y[p3index] - y[p1index]) / (x[p3index] - x[p1index]);
		} else {
			slope2 = ((double) y[p3index] - y[p1index]) / (x[p3index] - x[p1index]);
		}

		try {
			BufferedImage resized = resize(testTexture, bix - bixmin, biy - biymin);
			for (int x1 = bixmin; x1 < bix; x1++) {
				//for (int y1 = biymin; y1 < biy; y1++) {
					//if (p.contains(x1, y1)) {
						//g.drawImage(resized.getSubimage((x1-bixmin)%resized.getWidth(), (int) (((y1-biymin) + (bottomslope*(x1-bixmin))))%resized.getHeight(),1,1),x1,y1,null);

						int x3 = x1 - bixmin;
						if (resized.getWidth() > x3)
							if (0 <= x3) {
								if(slope > 0)
								for (double y1 =  (biymin+(slope * (x1 - bixmin))); y1 < biy-(slope * (x1 - bixmin)); y1+=1) {
									int y3 = (int) (((y1 - biymin)));
									if (bi.getWidth() > x1 && bi.getHeight() > y1 && resized.getHeight() > y3)
										if (x1 >= 0 && y1 >= 0 && y3 >= 0) {
											int rgb = resized.getRGB(x3, y3);
											bi.setRGB(x1, (int) y1, rgb);
										}
								}
						//	}
					//}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}


	public static BufferedImage resize(BufferedImage img,
									   int maxWidth, int maxHeight) throws IOException {
		int scaledWidth = maxWidth, scaledHeight = maxHeight;

		/*scaledWidth = maxWidth;
		scaledHeight = (int) (img.getHeight() * ( (double) scaledWidth / img.getWidth() ));

		if (scaledHeight> maxHeight) {
			scaledHeight = maxHeight;
			scaledWidth= (int) (img.getWidth() * ( (double) scaledHeight/ img.getHeight() ));

			if (scaledWidth > maxWidth) {
				scaledWidth = maxWidth;
				scaledHeight = maxHeight;
			}
		}*/

		Image resized = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_FAST);

		BufferedImage buffered = new BufferedImage(scaledWidth, scaledHeight, Image.SCALE_REPLICATE);

		buffered.getGraphics().drawImage(resized, 0, 0, null);

		return buffered;
	}


	public static void drawTriangle(BufferedImage bi, Graphics2D g, World world, int x, int y, Triangle triangle, Color color, boolean istop) {

		if (color != null)
			g.setColor(color);
		double cos = Math.cos(Math.toRadians(world.camera.getYaw()));
		double sin = Math.sin(Math.toRadians(world.camera.getYaw()));

		/*Location2D point = triangle.getPoints()[0];
		Location2D otherPoint = triangle.getPoints()[1];
		Location2D otherPoint3 = triangle.getPoints()[2];*/

		double zdif = cos * (triangle.getPoints()[0].getZ() - world.camera.getPersonLocation().getZ()) - (sin * (triangle.getPoints()[0].getX() - world.camera.getPersonLocation().getX()));
		double ydif = triangle.getPoints()[0].getY() - world.camera.getPersonLocation().getY();
		double xdif = cos * (triangle.getPoints()[0].getX() - world.camera.getPersonLocation().getX()) + (sin * (triangle.getPoints()[0].getZ() - world.camera.getPersonLocation().getZ()));

		double zdif1 = cos * (triangle.getPoints()[1].getZ() - world.camera.getPersonLocation().getZ()) - (sin * (triangle.getPoints()[1].getX() - world.camera.getPersonLocation().getX()));
		double ydif1 = triangle.getPoints()[1].getY() - world.camera.getPersonLocation().getY();
		double xdif1 = cos * (triangle.getPoints()[1].getX() - world.camera.getPersonLocation().getX()) + (sin * (triangle.getPoints()[1].getZ() - world.camera.getPersonLocation().getZ()));

		double zdif2 = cos * (triangle.getPoints()[2].getZ() - world.camera.getPersonLocation().getZ()) - (sin * (triangle.getPoints()[2].getX() - world.camera.getPersonLocation().getX()));
		double ydif2 = triangle.getPoints()[2].getY() - world.camera.getPersonLocation().getY();
		double xdif2 = cos * (triangle.getPoints()[2].getX() - world.camera.getPersonLocation().getX())+ (sin * (triangle.getPoints()[2].getZ() - world.camera.getPersonLocation().getZ()));


		Location2D point = new Location2D((int) ((xdif * bi.getHeight() / zdif)), (int) -(ydif * bi.getHeight() / zdif));
		Location2D otherPoint = new Location2D((int) ((xdif1 * bi.getHeight() / zdif1)), (int) -(ydif1 * bi.getHeight() / zdif1));
		Location2D otherPoint3 = new Location2D((int) ((xdif2 * bi.getHeight() / zdif2)), (int) -(ydif2 * bi.getHeight() / zdif2));

			/*System.out.println("P1 X is "+point.getX());
		System.out.println("P1 Y is "+point.getY());
		System.out.println("P2 X is "+otherPoint.getX());
		System.out.println("P2 Y is "+otherPoint.getY());
		System.out.println("P3 X is "+otherPoint3.getX());
		System.out.println("P3 Y is "+otherPoint3.getY());*/

		if (point.getX() > bi.getWidth())
			return;
		if (point.getX() < -bi.getWidth())
			return;

		if (otherPoint.getX() > bi.getWidth())
			return;
		if (otherPoint.getX() < -bi.getWidth())
			return;

		if (otherPoint3.getX() > bi.getWidth())
			return;
		if (otherPoint3.getX() < -bi.getWidth())
			return;

		fillTriangle(bi, g, x, y, point, otherPoint, otherPoint3, istop);

		//if (color == null)
		g.setColor(new Color(255, 0, 0));
		drawLine(g, point.getX() + x, point.getY() + y, otherPoint.getX() + x, otherPoint.getY() + y);
		//if (color == null)
		g.setColor(new Color(0, 255, 0));
		drawLine(g, point.getX() + x, point.getY() + y, otherPoint3.getX() + x, otherPoint3.getY() + y);
		//if (color == null)
		g.setColor(new Color(0, 0, 255));
		drawLine(g, otherPoint3.getX() + x, otherPoint3.getY() + y, otherPoint.getX() + x, otherPoint.getY() + y);
	}

}

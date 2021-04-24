package me.zombie_striker.jretrowave3d.utils;

import me.zombie_striker.jretrowave3d.LightManager;
import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.*;
import me.zombie_striker.jretrowave3d.graphics.Screen;
import me.zombie_striker.jretrowave3d.graphics.TriangleRenderer;

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

		if (startY == endY) {
			int width = Math.max(endX, startX) - Math.min(startX, endX);
			if (startX < endX) {
				bi.fillRect(startX, startY, width, 1);
			} else {
				bi.fillRect(endX, startY, width, 1);
			}
			return;
		}

		//if (startX < endX) {
		slope = (0.0 + endY - startY) / (endX - startX);
		/*} else {
			slope = (0.0 + startY - endY) / (startX - endX);
		}*/
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


	public static TriangleRenderer createTriangleRenderFor(Screen wrapper, Vector2D p1, Vector2D p2, Vector2D p3, Material material, Color color) {
		TriangleRenderer tr = new TriangleRenderer(p1, p2, p3, color);
		return tr;
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

	public static void drawLine(BufferedImage bi, Graphics2D g, World world, int x, int y, Line line) {
		double cosy = Math.cos(world.camera.getYawRadians());
		double siny = Math.sin(world.camera.getYawRadians());

		double zdifA = (line.getStart().getZ() - world.camera.getLocation().getZ());
		double ydifA = (line.getStart().getY() - world.camera.getLocation().getY());
		double xdifA = (line.getStart().getX() - world.camera.getLocation().getX());

		double zdifB = (line.getEnd().getZ() - world.camera.getLocation().getZ());
		double ydifB = (line.getEnd().getY() - world.camera.getLocation().getY());
		double xdifB = (line.getEnd().getX() - world.camera.getLocation().getX());


		double zxdistance1 = (cosy * zdifA) - (siny * xdifA); // Col 3
		double xzdistance1 = (cosy * xdifA) + (siny * zdifA); //Col 2
		double zxdistance2 = (cosy * zdifB) - (siny * xdifB);
		double xzdistance2 = (cosy * xdifB) + (siny * zdifB);


		/*double x1 = (((xdifA) / (zdifA)));
		double x2 = (((xdifB) / (zdifB)));

		double y1 = ((((-ydifA / (zdifA)))));
		double y2 = ((((-ydifB / (zdifB)))));*/
		float x1 = (float) ((xzdistance1) / (zxdistance1));
		float x2 = (float) ((xzdistance2) / (zxdistance2));
		float y1 = (float) (-ydifA / (zxdistance1));
		float y2 = (float) (-ydifB / (zxdistance2));
		Vector2D point1 = new Vector2D(x1, y1);
		Vector2D point2 = new Vector2D(x2, y2);
		point1.multiply(bi.getHeight() / 2);
		point2.multiply(bi.getHeight() / 2);

		System.out.println(point1.getX() + "    " + point1.getY() + "    " + point2.getX() + "    " + point2.getY());

		if (Double.isInfinite(x1))
			return;
		if (Double.isInfinite(y1))
			return;
		if (Double.isInfinite(x2))
			return;
		if (Double.isInfinite(y2))
			return;

		drawLine(g, (int) point1.getX() + x, (int) point1.getY() + y, (int) point2.getX() + x, (int) point2.getY() + y);

	}



	public static ObjectChain<TriangleRenderer> drawTriangle(Screen wrapper, ObjectChain<TriangleRenderer> cur, World world, Triangle triangle, boolean drawRelativeToCamra) {
		Color color = triangle.getColor();
		if (triangle.shouldCalculateLight() && triangle.getColor() != null)
			color = (LightManager.getColorFromLightsources(triangle, world, false));

		double zdifA;
		double ydifA;
		double xdifA;

		double zdifB;
		double ydifB;
		double xdifB;

		double zdifC;
		double ydifC;
		double xdifC;

		zdifA = (triangle.getPoints()[0].getZ() - world.camera.getLocation().getZ());
		ydifA = (triangle.getPoints()[0].getY() - world.camera.getLocation().getY());
		xdifA = (triangle.getPoints()[0].getX() - world.camera.getLocation().getX());

		zdifB = (triangle.getPoints()[1].getZ() - world.camera.getLocation().getZ());
		ydifB = (triangle.getPoints()[1].getY() - world.camera.getLocation().getY());
		xdifB = (triangle.getPoints()[1].getX() - world.camera.getLocation().getX());


		zdifC = (triangle.getPoints()[2].getZ() - world.camera.getLocation().getZ());
		ydifC = (triangle.getPoints()[2].getY() - world.camera.getLocation().getY());
		xdifC = (triangle.getPoints()[2].getX() - world.camera.getLocation().getX());


		float x1 = (float) ((xdifA) / (zdifA));
		float x2 = (float) ((xdifB) / (zdifB));
		float x3 = (float) ((xdifC) / (zdifC));

		float y1 = (float) (ydifA / (zdifA));
		float y2 = (float) (ydifB / (zdifB));
		float y3 = (float) (ydifC / (zdifC));

		if (zdifA < 0) {
			y1 = -wrapper.getHeight();
			if (xdifA < 0) {
				x1 = -wrapper.getWidth();
			} else {
				x1 = wrapper.getWidth();
			}
		}
		if (zdifB < 0) {
			y2 = -wrapper.getHeight();
			if (xdifB < 0) {
				x2 = -wrapper.getWidth();
			} else {
				x2 = wrapper.getWidth();
			}
		}
		if (zdifC < 0) {
			y3 = -wrapper.getHeight();
			if (xdifC < 0) {
				x2 = -wrapper.getWidth();
			} else {
				x2 = wrapper.getWidth();
			}
		}

		//TODO: Check if this is working
		/*if (zdifA < 0) {
			//y1=y1;
			if (ydifA < 0) {
				y1 -= wrapper.getHeight();
			} else {
				y1 += wrapper.getHeight();
			}
			if (xdifA < 0) {
				x1 += wrapper.getWidth();
			} else {
				x1 += wrapper.getWidth();
			}
		}
		if (zdifB < 0) {
			//	y2=y2;
			if (ydifB < 0) {
				y2 -= wrapper.getHeight();
			} else {
				y2 += wrapper.getHeight();
			}
			if (xdifB < 0) {
				x2 += wrapper.getWidth();
			} else {
				x2 += wrapper.getWidth();
			}
			//x2+=bi.getWidth();
			//x2=-x2;
		}
		if (zdifC < 0) {
			//y3=y3;
			if (ydifC < 0) {
				y3 -= wrapper.getHeight();
			} else {
				y3 += wrapper.getHeight();
			}
			if (xdifC < 0) {
				x3 += wrapper.getWidth();
			} else {
				x3 += wrapper.getWidth();
			}
			//	x3+=bi.getWidth();
			//x3=-x3;
		}*/

		Vector2D point1 = new Vector2D(x1, y1);
		Vector2D point2 = new Vector2D(x2, y2);
		Vector2D point3 = new Vector2D(x3, y3);

		point1.multiply(wrapper.getWidth());
		point2.multiply(wrapper.getWidth());
		point3.multiply(wrapper.getWidth());

		TriangleRenderer tr = createTriangleRenderFor(wrapper, point1, point2, point3, triangle.getMaterial(), color);
		ObjectChain objectChain = new ObjectChain(tr, cur);
		return objectChain;

		/*if (drawNormal) {
			Vector3D normal = triangle.getNormal(false);
			normal.normalize();
			Line line = new Line(triangle.getRelativeLocation()[0], new Vector3D(triangle.getRelativeLocation()[0].getX() - normal.getX(), triangle.getRelativeLocation()[0].getY() - normal.getY(), triangle.getRelativeLocation()[0].getZ() - normal.getZ()));
			//g.setColor(new Color(255, 0, 255));
			//drawLine(bi, g, world, x, y, line);
			drawTriangle(wrapper, world, x, y, new Triangle(line.getStart(), line.getEnd(), new Vector3D(line.getEnd().getX() + 0.01f, line.getEnd().getY(), line.getEnd().getZ() + 0.1f), new Color(255, 0, 160)), false, true);
		}*/
		//if (color == null)
	/*	g.setColor(new Color(255, 0, 0));
		drawLine(g, (int) (point1.getX() + x), (int) (point1.getY() + y), (int) (point2.getX() + x), (int) (point2.getY() + y));
		//if (color == null)
		g.setColor(new Color(0, 255, 0));
		drawLine(g, (int) (point1.getX() + x), (int) (point1.getY() + y), (int) (point3.getX() + x), (int) (point3.getY() + y));
		//if (color == null)
		g.setColor(new Color(0, 0, 255));
		drawLine(g, (int) (point3.getX() + x), (int) (point3.getY() + y), (int) (point2.getX() + x), (int) (point2.getY() + y));*/
	}

}

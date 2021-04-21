package me.zombie_striker.jretrowave3d.data;


import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenWrapper {

	int[][] data;

	PolygonWrapper[] polygons = new PolygonWrapper[4096*2];
	private int index = 0;

	public ScreenWrapper(int width, int height) {
		this.data = new int[width][height];
	}

	public double getPixel(int x, int y) {
		return data[x][y];
	}

	public void setPixel(int x, int y, int rgb) {
		this.data[x][y] = rgb;
	}

	public int[][] getPixels() {
		return data;
	}


	public void drawPolygons(boolean drawWithCPU) {
		BufferedImage bufferedImage = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g  = bufferedImage.createGraphics();
		for(int i = 0; i < index;i++){
			g.setColor(new Color(polygons[i].getColor()));
			g.fillPolygon(polygons[i].getPolygon());
		}
		g.dispose();
		for(int x = 0; x < getWidth(); x++){
			for(int y = 0; y < getHeight(); y++){
				data[x][y] = bufferedImage.getRGB(x,y);
			}
		}
	}
	public void addPolygon(Polygon polygon, Color color){
		this.polygons[index]=new PolygonWrapper(polygon,color.getRGB());
		index++;
	}

	public void clearPolygons() {
		this.polygons = new PolygonWrapper[4096*2];
		this.index = 0;
	}

	public int getWidth() {
		return data.length;
	}

	public int getHeight() {
		return data[0].length;
	}
}

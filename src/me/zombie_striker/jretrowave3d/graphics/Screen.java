package me.zombie_striker.jretrowave3d.graphics;

import me.zombie_striker.jretrowave3d.JRetroWave3D;
import me.zombie_striker.jretrowave3d.data.ObjectChain;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;

import java.awt.*;

public class Screen {

	private int height;
	private int width;

	private ObjectChain<TriangleRenderer> triangleRenderers = new ObjectChain<>(null,null);

	public Screen(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public void render() {
		ObjectChain<TriangleRenderer> start = triangleRenderers;
		while (start.hasNext()) {
			if (start.getObject() != null) {
				start.getObject().render();
			}
			start=start.getNextChain();
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getPixelAt(int i) {
		return getPixelAt(i % width, i / width);
	}

	public int getPixelAt(int x, int y) {
		return new Color(255, 255, 255).getRGB();
	}

	public void setRenderChain(ObjectChain<TriangleRenderer> chain) {
		this.triangleRenderers = chain;
	}

}

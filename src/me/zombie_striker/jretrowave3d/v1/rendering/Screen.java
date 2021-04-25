package me.zombie_striker.jretrowave3d.v1.rendering;

import me.zombie_striker.jretrowave3d.v1.data.ObjectChain;

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

	public void setRenderChain(ObjectChain<TriangleRenderer> chain) {
		this.triangleRenderers = chain;
	}

}

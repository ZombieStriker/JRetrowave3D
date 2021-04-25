package me.zombie_striker.jretrowave3d.v1.rendering;

import me.zombie_striker.jretrowave3d.v1.data.Vector3D;

import java.awt.*;
import java.util.HashMap;

public class TriangleRenderer {

	private static HashMap<Color, VertexArray> rendersForColor = new HashMap<>();

	private VertexArray triangle;
	private float[] vertices;
	private ColorTexture texture;
	private Color tempColor;

	/*public TriangleRenderer(Vector2D p1, Vector2D p2, Vector2D p3, Color color) {
		this.tempColor = color;

		int height = JRetroWave3D.getWindow().getWidth();
		int width = JRetroWave3D.getWindow().getWidth();
		vertices = new float[]{
				(p1.getX() / width), (p1.getY() / height), 0,
				(p2.getX() / width), (p2.getY() / height), 0,
				(p3.getX() / width), (p3.getY() / height), 0,
		};
	}*/

	public TriangleRenderer(Vector3D p1, Vector3D p2, Vector3D p3, Color color) {
		this.tempColor = color;

		vertices = new float[]{
				(p1.getX() ), (p1.getY() ), 0,
				(p2.getX() ), (p2.getY() ), 0,
				(p3.getX() ), (p3.getY() ), 0,
		};
	}
	public void render() {
		byte[] indices = new byte[]{
				0, 1, 2,
		};
		float[] tcs = new float[]{
				0, 0,
				0, 0,
				0, 0,
				0, 0
		};

		if (texture == null)
			this.texture = ColorTexture.getColorTexture(tempColor);
		if(rendersForColor.containsKey(this.tempColor)){
			triangle = rendersForColor.get(this.tempColor);
			triangle.updateVertexes(vertices);
		}else{
			triangle = new VertexArray(vertices, indices, tcs, tempColor);
			rendersForColor.put(tempColor, triangle);
		}
		texture.bind();
		Shader.SCREEN.enable();
		triangle.render();
		Shader.SCREEN.disable();
		texture.unbind();
	}
}

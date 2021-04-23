package me.zombie_striker.jretrowave3d.graphics;

import me.zombie_striker.jretrowave3d.utils.BufferUtils;

import java.awt.*;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;

public class ScreenTexture {


	private int width, height;

	private int texture = -1;

	public ScreenTexture(Screen screen) {
		load(screen);
	}

	public int load(Screen screen) {
		int[] pixels = null;
		try {
			width = screen.getWidth();
			height = screen.getHeight();
			pixels = new int[width * height];
			for(int i = 0; i < pixels.length; i++){
				pixels[i]=
						Math.random()<0.5?Color.BLUE.getRGB():
								Math.random()<0.5?Color.GREEN.getRGB():
										Math.random()<0.5?Color.RED.getRGB():
												Math.random()<0.5?Color.GRAY.getRGB():
														Math.random()<0.5?Color.DARK_GRAY.getRGB():
																Color.YELLOW.getRGB()
				//pixels[i] = screen.getPixelAt(i);
				;
			}
		} catch (Exception e34) {
			e34.printStackTrace();
		}

		int[] data = new int[width * height];
		for (int i = 0; i < data.length; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}

		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
		glBindTexture(GL_TEXTURE_2D, 0);
		return texture;
	}

	public void updateScreen(Screen screen) {
		load(screen);
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, texture);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}

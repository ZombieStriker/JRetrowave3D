package me.zombie_striker.jretrowave3d.graphics;

import me.zombie_striker.jretrowave3d.utils.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;

public class ColorTexture {

	public static HashMap<Color, ColorTexture> textures = new HashMap<Color, ColorTexture>();

	public int texture;

	public static ColorTexture getColorTexture(Color color){
		if(textures.containsKey(color)){
			return textures.get(color);
		}
		ColorTexture colorTexture = new ColorTexture(color);
		textures.put(color,colorTexture);
		return colorTexture;
	}

	private ColorTexture(Color color){
		this.texture = load(color);
	}



	public int load(Color color){
		int[] pixels = null;
		int width = 5;
		int height = 100;
		try{
			pixels = new int[width*height];
			Arrays.fill(pixels,color.getRGB());
		}catch (Exception e34){
			e34.printStackTrace();
		}

		int[] data = new int[width*height];
		for(int i = 0; i < data.length; i++){
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);

			data[i] = a << 24 | b << 16 | g << 8 | r;
		}

		int result = glGenTextures();
		glBindTexture(GL_TEXTURE_2D,result);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));

		glBindTexture(GL_TEXTURE_2D,0);
		return result;
	}

	public void bind(){
		glBindTexture(GL_TEXTURE_2D,texture);
	}
	public void unbind(){
		glBindTexture(GL_TEXTURE_2D,0);
	}
}

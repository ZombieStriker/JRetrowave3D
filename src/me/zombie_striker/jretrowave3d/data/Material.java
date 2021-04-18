package me.zombie_striker.jretrowave3d.data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Material {

	private BufferedImage image;// = new BufferedImage(8,8,BufferedImage.TYPE_INT_ARGB);

	public Material(String s) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BufferedImage getMaterial(){
		return image;
	}
}

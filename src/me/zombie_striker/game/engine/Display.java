package me.zombie_striker.game.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Display extends JPanel {

	public BufferedImage image;

	public Display() {
		image =  new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}

	public void setDisplay(BufferedImage bi){
		image = bi;
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

}

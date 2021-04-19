package me.zombie_striker.jretrowave3d;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface GameEngine {

	void init();
	void tick();
	void render(BufferedImage bi);
	World getWorld();
}

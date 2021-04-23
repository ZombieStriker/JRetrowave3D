package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.ObjectChain;
import me.zombie_striker.jretrowave3d.graphics.Screen;
import me.zombie_striker.jretrowave3d.graphics.TriangleRenderer;

public interface GameEngine {

	void init();
	void tick();
	ObjectChain<TriangleRenderer> render(Screen screen, ObjectChain<TriangleRenderer> startOfChain, ObjectChain<TriangleRenderer> currentChain);
	World getWorld();
}

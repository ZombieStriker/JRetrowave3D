package me.zombie_striker.jretrowave3d.v1;

import me.zombie_striker.jretrowave3d.v1.data.ObjectChain;
import me.zombie_striker.jretrowave3d.v1.rendering.Screen;
import me.zombie_striker.jretrowave3d.v1.rendering.TriangleRenderer;

public interface GameEngine {

	void init();
	void tick();
	ObjectChain<TriangleRenderer> render(Screen screen, ObjectChain<TriangleRenderer> startOfChain, ObjectChain<TriangleRenderer> currentChain);
	World getWorld();
}

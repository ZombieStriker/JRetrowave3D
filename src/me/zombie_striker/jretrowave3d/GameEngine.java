package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.ScreenWrapper;

public interface GameEngine {

	void init();
	void tick();
	void render(ScreenWrapper bi);
	World getWorld();
}

package me.zombie_striker.jretrowave3d;

public interface GameEngine {

	void init();
	void tick();
	World getWorld();
}

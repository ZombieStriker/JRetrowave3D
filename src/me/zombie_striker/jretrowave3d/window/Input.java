package me.zombie_striker.jretrowave3d.window;

import me.zombie_striker.jretrowave3d.TickManager;
import me.zombie_striker.jretrowave3d.data.TickableObject;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback {

	public static boolean[] keys = new boolean[10000];
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = (action != GLFW.GLFW_RELEASE);
	}
}

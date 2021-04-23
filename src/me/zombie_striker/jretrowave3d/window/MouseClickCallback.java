package me.zombie_striker.jretrowave3d.window;

import me.zombie_striker.jretrowave3d.JRetroWave3D;
import me.zombie_striker.jretrowave3d.TickManager;
import me.zombie_striker.jretrowave3d.data.TickableObject;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public class MouseClickCallback implements GLFWMouseButtonCallbackI {

	private boolean[] pressed = new boolean[10];

	@Override
	public void invoke(long window, int button, int action, int mods) {

		TickManager.runNextTick(new TickableObject() {
			@Override
			public void tick() {
				if (pressed[button] != (action != GLFW.GLFW_RELEASE)) {
					JRetroWave3D.callMouseClick(button, (action != GLFW.GLFW_RELEASE));
					pressed[button] = (action != GLFW.GLFW_RELEASE);
				}
			}
		});
	}
}

package me.zombie_striker.jretrowave3d.window;

import me.zombie_striker.jretrowave3d.JRetroWave3D;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class WindowResizeCallback extends GLFWWindowSizeCallback {
	@Override
	public void invoke(long window, int w, int h) {
		JRetroWave3D.getWindow().setSize(w,h);
	}
}

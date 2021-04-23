package me.zombie_striker.jretrowave3d.window;

import me.zombie_striker.jretrowave3d.JRetroWave3D;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorMoveCallback extends GLFWCursorPosCallback {
	private double previousX;
	private double previousY;
	@Override
	public void invoke(long window, double x, double y) {
		if(x!=previousX || y !=previousY){
			previousX=x;
			previousY=y;
			JRetroWave3D.callMouseMove(x,y);
		}
	}
}

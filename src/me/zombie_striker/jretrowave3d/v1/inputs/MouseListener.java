package me.zombie_striker.jretrowave3d.v1.inputs;

public abstract class MouseListener {

	public abstract void onMove(double xcoord, double ycoord);

	public abstract void onLeftClick(boolean pressed);

	public abstract void onRightClick(boolean pressed);

	public abstract void onButtonPressed(int button, boolean pressed);
}

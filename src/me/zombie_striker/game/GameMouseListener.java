package me.zombie_striker.game;

import me.zombie_striker.jretrowave3d.JRetroWave3D;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameMouseListener implements MouseListener, MouseMotionListener {
	double mouseSensitivity = 0.4;
	double lastLocationx = -1;
	double lastLocationy = -1;
	private boolean justMovedIntoScreen = false;

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		justMovedIntoScreen = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	public void tick() {
		justMovedIntoScreen = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (justMovedIntoScreen) {
			justMovedIntoScreen=false;
			lastLocationx = e.getX();
			lastLocationy = e.getY();
			return;
		}
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException awtException) {
			awtException.printStackTrace();
		}
		if (!((Game)JRetroWave3D.getGame()).isEscaped()) {
			if (lastLocationx == -1 && lastLocationy == -1) {
				lastLocationx = e.getX();
				lastLocationy = e.getY();
			}

			double deltaX = ((lastLocationx - e.getX()));
			double deltaY = ((lastLocationy - e.getY()));

			lastLocationx = e.getX();
			lastLocationy = e.getY();

			JRetroWave3D.getGame().getWorld().camera.setYaw((float) (JRetroWave3D.getGame().getWorld().camera.getYaw() + deltaX * mouseSensitivity));
			for (RenderableObject renderableObject : JRetroWave3D.getGame().getWorld().getRenderableObjects()) {
				renderableObject.updateTriangles();
			}

			JRetroWave3D.getGame().getWorld().camera.setPitch((float) (JRetroWave3D.getGame().getWorld().camera.getPitch() - deltaY * mouseSensitivity));
			for (RenderableObject renderableObject : JRetroWave3D.getGame().getWorld().getRenderableObjects()) {
				renderableObject.updateTriangles();
			}
			justMovedIntoScreen = true;
			robot.mouseMove((int) JRetroWave3D.getWindow().getSize().getWidth()/2, (int) JRetroWave3D.getWindow().getSize().getHeight()/2);
		}
	}
}

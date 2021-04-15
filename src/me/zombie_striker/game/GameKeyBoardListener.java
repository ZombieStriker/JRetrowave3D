package me.zombie_striker.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyBoardListener implements KeyListener {
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		double cos = Math.cos(Math.toRadians(Main.game.getWorld().camera.getYaw()));
		double sin = Math.sin(Math.toRadians(Main.game.getWorld().camera.getYaw()));

		float k = 1;// Move distance

		float xzLength =  (k);
		float dx = (float) (xzLength * sin);
		float dz = (float) (xzLength * cos);

		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			Main.game.getWorld().camera.getApertureLocation().setX(Main.game.getWorld().camera.getApertureLocation().getX()-dz);
			Main.game.getWorld().camera.getPersonLocation().setX(Main.game.getWorld().camera.getPersonLocation().getX()-dz);
			Main.game.getWorld().camera.getApertureLocation().setZ(Main.game.getWorld().camera.getApertureLocation().getZ()-dx);
			Main.game.getWorld().camera.getPersonLocation().setZ(Main.game.getWorld().camera.getPersonLocation().getZ()-dx);
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			Main.game.getWorld().camera.getApertureLocation().setX(Main.game.getWorld().camera.getApertureLocation().getX()+dz);
			Main.game.getWorld().camera.getPersonLocation().setX(Main.game.getWorld().camera.getPersonLocation().getX()+dz);
			Main.game.getWorld().camera.getApertureLocation().setZ(Main.game.getWorld().camera.getApertureLocation().getZ()+dx);
			Main.game.getWorld().camera.getPersonLocation().setZ(Main.game.getWorld().camera.getPersonLocation().getZ()+dx);
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			Main.game.getWorld().camera.getApertureLocation().setZ(Main.game.getWorld().camera.getApertureLocation().getZ()+dz);
			Main.game.getWorld().camera.getPersonLocation().setZ(Main.game.getWorld().camera.getPersonLocation().getZ()+dz);
			Main.game.getWorld().camera.getApertureLocation().setX(Main.game.getWorld().camera.getApertureLocation().getX()-dx);
			Main.game.getWorld().camera.getPersonLocation().setX(Main.game.getWorld().camera.getPersonLocation().getX()-dx);
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			Main.game.getWorld().camera.getApertureLocation().setZ(Main.game.getWorld().camera.getApertureLocation().getZ()-dz);
			Main.game.getWorld().camera.getPersonLocation().setZ(Main.game.getWorld().camera.getPersonLocation().getZ()-dz);
			Main.game.getWorld().camera.getApertureLocation().setX(Main.game.getWorld().camera.getApertureLocation().getX()+dx);
			Main.game.getWorld().camera.getPersonLocation().setX(Main.game.getWorld().camera.getPersonLocation().getX()+dx);
		}
		if(e.getKeyCode()==KeyEvent.VK_A){
			Main.game.getWorld().camera.setYaw((int)Main.game.getWorld().camera.getYaw()+5);
		}
		if(e.getKeyCode()==KeyEvent.VK_D){
			Main.game.getWorld().camera.setYaw((int)Main.game.getWorld().camera.getYaw()-5);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}

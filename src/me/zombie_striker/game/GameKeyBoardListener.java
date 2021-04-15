package me.zombie_striker.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyBoardListener implements KeyListener {
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			Main.game.getWorld().camera.getApertureLocation().setX(Main.game.getWorld().camera.getApertureLocation().getX()-1);
			Main.game.getWorld().camera.getPersonLocation().setX(Main.game.getWorld().camera.getPersonLocation().getX()-1);
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			Main.game.getWorld().camera.getApertureLocation().setX(Main.game.getWorld().camera.getApertureLocation().getX()+1);
			Main.game.getWorld().camera.getPersonLocation().setX(Main.game.getWorld().camera.getPersonLocation().getX()+1);
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			Main.game.getWorld().camera.getApertureLocation().setZ(Main.game.getWorld().camera.getApertureLocation().getZ()+1);
			Main.game.getWorld().camera.getPersonLocation().setZ(Main.game.getWorld().camera.getPersonLocation().getZ()+1);
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			Main.game.getWorld().camera.getApertureLocation().setZ(Main.game.getWorld().camera.getApertureLocation().getZ()-1);
			Main.game.getWorld().camera.getPersonLocation().setZ(Main.game.getWorld().camera.getPersonLocation().getZ()-1);
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

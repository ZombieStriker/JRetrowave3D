package me.zombie_striker.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyBoardListener implements KeyListener {
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean active = true;
		if(e.getKeyCode()==KeyEvent.VK_W)
			Main.game.forward = active;
		if(e.getKeyCode()==KeyEvent.VK_S)
			Main.game.backwards = active;
		if(e.getKeyCode()==KeyEvent.VK_A)
			Main.game.left = active;
		if(e.getKeyCode()==KeyEvent.VK_D)
			Main.game.right = active;
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			Main.game.turn_left = active;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			Main.game.turn_right = active;
		if(e.getKeyCode()==KeyEvent.VK_UP)
			Main.game.turn_up = active;
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
			Main.game.turn_down = active;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		boolean active = false;
		if(e.getKeyCode()==KeyEvent.VK_W)
			Main.game.forward = active;
		if(e.getKeyCode()==KeyEvent.VK_S)
			Main.game.backwards = active;
		if(e.getKeyCode()==KeyEvent.VK_A)
			Main.game.left = active;
		if(e.getKeyCode()==KeyEvent.VK_D)
			Main.game.right = active;
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			Main.game.turn_left = active;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			Main.game.turn_right = active;
		if(e.getKeyCode()==KeyEvent.VK_UP)
			Main.game.turn_up = active;
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
			Main.game.turn_down = active;
	}
}

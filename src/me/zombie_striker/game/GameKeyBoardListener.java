package me.zombie_striker.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static me.zombie_striker.game.Main.setMouse;

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
		if(e.getKeyCode()==KeyEvent.VK_J)
			Main.game.j = active;
		if(e.getKeyCode()==KeyEvent.VK_L)
			Main.game.l = active;
		if(e.getKeyCode()==KeyEvent.VK_I)
			Main.game.i = active;
		if(e.getKeyCode()==KeyEvent.VK_K)
			Main.game.k = active;
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
			Main.game.jump = active;
		if(e.getKeyCode()==KeyEvent.VK_SHIFT)
			Main.game.crouch = active;
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			Main.game.turn_left = active;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			Main.game.turn_right = active;
		if(e.getKeyCode()==KeyEvent.VK_UP)
			Main.game.turn_up = active;
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
			Main.game.turn_down = active;
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			Main.game.escaped = !Main.game.escaped;
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			setMouse(Main.game.isEscaped()?null:toolkit.getImage("textures/transparent.png"),0,0);
		}

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
		if(e.getKeyCode()==KeyEvent.VK_J)
			Main.game.j = active;
		if(e.getKeyCode()==KeyEvent.VK_L)
			Main.game.l = active;
		if(e.getKeyCode()==KeyEvent.VK_I)
			Main.game.i = active;
		if(e.getKeyCode()==KeyEvent.VK_K)
			Main.game.k = active;
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
			Main.game.jump = active;
		if(e.getKeyCode()==KeyEvent.VK_SHIFT)
			Main.game.crouch = active;
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

package me.zombie_striker.game;

import me.zombie_striker.jretrowave3d.JRetroWave3D;

import java.awt.*;
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
			((Game)JRetroWave3D.getGame()).forward = active;
		if(e.getKeyCode()==KeyEvent.VK_S)
			((Game)JRetroWave3D.getGame()).backwards = active;
		if(e.getKeyCode()==KeyEvent.VK_A)
			((Game)JRetroWave3D.getGame()).left = active;
		if(e.getKeyCode()==KeyEvent.VK_D)
			((Game)JRetroWave3D.getGame()).right = active;
		if(e.getKeyCode()==KeyEvent.VK_J)
			((Game)JRetroWave3D.getGame()).j = active;
		if(e.getKeyCode()==KeyEvent.VK_L)
			((Game)JRetroWave3D.getGame()).l = active;
		if(e.getKeyCode()==KeyEvent.VK_I)
			((Game)JRetroWave3D.getGame()).i = active;
		if(e.getKeyCode()==KeyEvent.VK_K)
			((Game)JRetroWave3D.getGame()).k = active;
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
			((Game)JRetroWave3D.getGame()).jump = active;
		if(e.getKeyCode()==KeyEvent.VK_SHIFT)
			((Game)JRetroWave3D.getGame()).crouch = active;
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			((Game)JRetroWave3D.getGame()).turn_left = active;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			((Game)JRetroWave3D.getGame()).turn_right = active;
		if(e.getKeyCode()==KeyEvent.VK_UP)
			((Game)JRetroWave3D.getGame()).turn_up = active;
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
			((Game)JRetroWave3D.getGame()).turn_down = active;
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			((Game)JRetroWave3D.getGame()).escaped = !((Game)JRetroWave3D.getGame()).escaped;
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			JRetroWave3D.setMouse(((Game)JRetroWave3D.getGame()).isEscaped()?null:toolkit.getImage("textures/transparent.png"),0,0);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		boolean active = false;
		if(e.getKeyCode()==KeyEvent.VK_W)
			((Game)JRetroWave3D.getGame()).forward = active;
		if(e.getKeyCode()==KeyEvent.VK_S)
			((Game)JRetroWave3D.getGame()).backwards = active;
		if(e.getKeyCode()==KeyEvent.VK_A)
			((Game)JRetroWave3D.getGame()).left = active;
		if(e.getKeyCode()==KeyEvent.VK_D)
			((Game)JRetroWave3D.getGame()).right = active;
		if(e.getKeyCode()==KeyEvent.VK_J)
			((Game)JRetroWave3D.getGame()).j = active;
		if(e.getKeyCode()==KeyEvent.VK_L)
			((Game)JRetroWave3D.getGame()).l = active;
		if(e.getKeyCode()==KeyEvent.VK_I)
			((Game)JRetroWave3D.getGame()).i = active;
		if(e.getKeyCode()==KeyEvent.VK_K)
			((Game)JRetroWave3D.getGame()).k = active;
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
			((Game)JRetroWave3D.getGame()).jump = active;
		if(e.getKeyCode()==KeyEvent.VK_SHIFT)
			((Game)JRetroWave3D.getGame()).crouch = active;
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			((Game)JRetroWave3D.getGame()).turn_left = active;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			((Game)JRetroWave3D.getGame()).turn_right = active;
		if(e.getKeyCode()==KeyEvent.VK_UP)
			((Game)JRetroWave3D.getGame()).turn_up = active;
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
			((Game)JRetroWave3D.getGame()).turn_down = active;
	}
}

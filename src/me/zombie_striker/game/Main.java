package me.zombie_striker.game;

import me.zombie_striker.jretrowave3d.JRetroWave3D;
import me.zombie_striker.jretrowave3d.TickManager;
import me.zombie_striker.jretrowave3d.Window;
import me.zombie_striker.jretrowave3d.data.TickableObject;

import java.awt.*;

public class Main {

	public static void main(String... args) {
		JRetroWave3D.init();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		JRetroWave3D.setMouse(toolkit.getImage("textures/transparent.png"),0,0);
		JRetroWave3D.addKeyListener(new GameKeyBoardListener());
		GameMouseListener mouseListener = new GameMouseListener();
		JRetroWave3D.addMouseListener(mouseListener);
		JRetroWave3D.addMouseMotionListener(mouseListener);

		TickManager.registerTickableObject(new TickableObject() {
			@Override
			public void tick() {
				mouseListener.tick();
			}
		});

		JRetroWave3D.start();
	}

}

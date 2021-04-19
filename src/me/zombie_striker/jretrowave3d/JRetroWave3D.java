package me.zombie_striker.jretrowave3d;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class JRetroWave3D {

	private static GameEngine game;
	private static Window window;

	public static GameEngine getGame(){
		return game;
	}

	public static void init(){

		window = new Window();
		window.init();
	}

	public static void start(){

		while (true) {
			long start = System.currentTimeMillis();
			game.tick();


			BufferedImage screen = new BufferedImage(getWindow().getWidth(), getWindow().getHeight(), BufferedImage.TYPE_INT_ARGB);
			game.getWorld().render(screen);
			game.render(screen);
			getWindow().getDisplay().setDisplay(screen);

			try {
				long wait = (10 - (System.currentTimeMillis() - start));
				if (wait> 0)
					Thread.sleep(wait);
			} catch (InterruptedException e) {
			}

			long end = System.currentTimeMillis();

			long elapsed = end - start;

			int fps = (int) (1000.0 / (elapsed));

			window.getDisplay().image.getGraphics().drawString("FPS: " + fps + " : " + (end % 100), 10, 10);
			window.getDisplay().image.getGraphics().drawString("YAW: " + game.getWorld().camera.getYaw() + " : PITCH " + game.getWorld().camera.getPitchRadians(), 10, 20);

			window.repaint();
		}
	}

	public static Window getWindow() {
		return window;
	}


	public static void setMouse(Image cursor,int x, int y){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		if(cursor==null) {
			getWindow().setCursor(Cursor.getDefaultCursor());
		}else{
			Cursor c = toolkit.createCustomCursor(cursor , new Point(x,
					y), "img");
			getWindow().setCursor (c);
		}
	}

	public static void addKeyListener(KeyListener gameKeyBoardListener) {
		getWindow().addKeyListener(gameKeyBoardListener);
	}

	public static void addMouseListener(MouseListener mouseListener) {
		getWindow().addMouseListener(mouseListener);
	}

	public static void addMouseMotionListener(MouseMotionListener mouseListener) {
		getWindow().addMouseMotionListener(mouseListener);
	}

	public static void setGameEngine(GameEngine game) {
		JRetroWave3D.game = game;
	}
}

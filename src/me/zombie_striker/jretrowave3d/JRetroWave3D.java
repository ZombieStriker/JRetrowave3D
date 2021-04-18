package me.zombie_striker.jretrowave3d;

import me.zombie_striker.game.Game;
import me.zombie_striker.game.GameKeyBoardListener;
import me.zombie_striker.game.GameMouseListener;
import me.zombie_striker.game.Main;

import java.awt.*;
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


		game = new Game();
		game.init();

	}

	public static void start(){

		while (true) {
			long start = System.currentTimeMillis();
			game.tick();


			BufferedImage screen = new BufferedImage(getWindow().getWidth(), getWindow().getHeight(), BufferedImage.TYPE_INT_ARGB);
			game.getWorld().render(screen);
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

	public static void addKeyListener(GameKeyBoardListener gameKeyBoardListener) {
		getWindow().addKeyListener(gameKeyBoardListener);
	}

	public static void addMouseListener(GameMouseListener mouseListener) {
		getWindow().addMouseListener(mouseListener);
	}

	public static void addMouseMotionListener(GameMouseListener mouseListener) {
		getWindow().addMouseMotionListener(mouseListener);
	}
}

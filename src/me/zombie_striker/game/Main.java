package me.zombie_striker.game;

import me.zombie_striker.jretrowave3d.Window;

import java.awt.*;

public class Main {

	public static Game game;
	private static Window window;

	public static void setMouse(Image cursor,int x, int y){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		if(cursor==null) {
			Main.getWindow().setCursor(Cursor.getDefaultCursor());
		}else{
			Cursor c = toolkit.createCustomCursor(cursor , new Point(x,
					y), "img");
			Main.getWindow().setCursor (c);
		}
	}

	public static void main(String... args) {
		window = new Window();
		window.init();

		window.addKeyListener(new GameKeyBoardListener());
		GameMouseListener mouseListener = new GameMouseListener();
		window.addMouseListener(mouseListener);
		window.addMouseMotionListener(mouseListener);

		game = new Game();
		game.init();

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setMouse(toolkit.getImage("textures/transparent.png"),0,0);

		while (true) {


			long start = System.currentTimeMillis();
			mouseListener.tick();
			game.tick();
			game.render();
			try {
				long wait = (20 - (System.currentTimeMillis() - start));
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

}

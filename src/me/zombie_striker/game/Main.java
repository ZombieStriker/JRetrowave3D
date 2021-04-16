package me.zombie_striker.game;

import me.zombie_striker.game.engine.Window;

public class Main {

	public static Game game;
	private static Window window;

	public static void main(String... args) {
		window = new Window();
		window.init();

		window.addKeyListener(new GameKeyBoardListener());

		game = new Game();
		game.init();

		while (true) {


			long start = System.currentTimeMillis();

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

			window.repaint();
		}
	}

	public static Window getWindow() {
		return window;
	}

}

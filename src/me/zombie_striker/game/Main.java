package me.zombie_striker.game;

import me.zombie_striker.game.engine.Window;

public class Main {

	private static Window window;
	public static Game game;


	public static void main(String... args){
		window = new Window();
		window.init();

		window.addKeyListener(new GameKeyBoardListener());

		game = new Game();
		game.init();

		while(true){


			long start = System.currentTimeMillis();

			game.tick();
			game.render();

			long end = System.currentTimeMillis();

			long elapsed = end-start;

			int fps = (int) (1000.0/(elapsed));

			window.getDisplay().image.getGraphics().drawString("FPS: "+fps,10,10);

			window.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static Window getWindow(){
		return window;
	}

}

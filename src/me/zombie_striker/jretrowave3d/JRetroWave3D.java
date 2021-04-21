package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.ScreenWrapper;
import me.zombie_striker.jretrowave3d.utils.Draw;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
		ScreenWrapper screenwrapper = new ScreenWrapper(getWindow().getWidth(),getWindow().getHeight());
		while (true) {
			long start = System.currentTimeMillis();
			game.tick();


			//BufferedImage screen = new BufferedImage(getWindow().getWidth(), getWindow().getHeight(), BufferedImage.TYPE_INT_ARGB);
			if(screenwrapper.getWidth() != getWindow().getWidth() || screenwrapper.getHeight() != getWindow().getHeight()){
				screenwrapper = new ScreenWrapper(getWindow().getWidth(),getWindow().getHeight());
			}
			game.getWorld().render(screenwrapper);
			screenwrapper.drawPolygons(true);
			screenwrapper.clearPolygons();
			game.render(screenwrapper);
			getWindow().getDisplay().setDisplay(Draw.render(screenwrapper.getPixels()));

			try {
				long wait = (10 - (System.currentTimeMillis() - start));
				if (wait> 0)
					Thread.sleep(wait);
			} catch (InterruptedException e) {
			}
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

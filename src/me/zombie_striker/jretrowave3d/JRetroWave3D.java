package me.zombie_striker.jretrowave3d;

import me.zombie_striker.jretrowave3d.data.ObjectChain;
import me.zombie_striker.jretrowave3d.graphics.TriangleRenderer;
import me.zombie_striker.jretrowave3d.inputs.Key;
import me.zombie_striker.jretrowave3d.inputs.KeyListener;
import me.zombie_striker.jretrowave3d.inputs.MouseListener;
import me.zombie_striker.jretrowave3d.window.Window;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JRetroWave3D {

	public static int tick = 0;
	private static GameEngine game;
	private static Window_OLD window_old;
	private static Window window;

	private static List<KeyListener> keylisteners = new ArrayList<>();
	private static List<MouseListener> mouseListeners = new ArrayList<>();

	public static GameEngine getGame() {
		return game;
	}

	public static void init() {

		//window_old = new Window_OLD();
		//window_old.init();

		window = new Window();
		window.start();

	}

	public static void start() {

		while (window.isRunning()) {
			long start = System.currentTimeMillis();
			game.tick();
			if (getWindow().getScreen() != null) {
				ObjectChain<TriangleRenderer> renderChain = new ObjectChain<>(null,null);
				ObjectChain<TriangleRenderer> currentChain = renderChain;
				currentChain = game.getWorld().render(getWindow().getScreen(),renderChain, currentChain);
				currentChain = game.render(getWindow().getScreen(),renderChain, currentChain);
				getWindow().getScreen().setRenderChain(renderChain);
			}
			tick = getWindow().getTick();
			try {
				long wait = (10 - (System.currentTimeMillis() - start));
				if (wait > 0)
					Thread.sleep(wait);
			} catch (InterruptedException e) {
			}
		}
	}

	public static Window_OLD getWindow_old() {
		return window_old;
	}


	//TODO: Handle Inputs and mouse

	public static void setMouse(Image cursor, int x, int y) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		if (cursor == null) {
			//getWindow_old().setCursor(Cursor.getDefaultCursor());
		} else {
			Cursor c = toolkit.createCustomCursor(cursor, new Point(x,
					y), "img");
			//getWindow_old().setCursor (c);
		}
	}

	public static void callKey(Key key, boolean pressed) {
		for (KeyListener listeners : keylisteners) {
			if (pressed) {
				listeners.keyPressed(key);
			} else {
				listeners.keyReleased(key);
			}
		}
	}

	public static void callMouseMove(double x, double y) {
		for (MouseListener listeners : mouseListeners) {
			listeners.onMove(x, y);
		}
	}

	public static void callMouseClick(int button, boolean pressed) {
		for (MouseListener listeners : mouseListeners) {
			if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
				listeners.onLeftClick(pressed);
			} else if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
				listeners.onRightClick(pressed);
			} else {
				listeners.onButtonPressed(button, pressed);
			}
		}
	}

	public static void addKeyListener(KeyListener gameKeyBoardListener) {
		//getWindow_old().addKeyListener(gameKeyBoardListener);
		keylisteners.add(gameKeyBoardListener);
	}

	public static void addMouseListener(MouseListener mouseListener) {
		//getWindow_old().addMouseListener(mouseListener);
		mouseListeners.add(mouseListener);
	}


	public static void setGameEngine(GameEngine game) {
		JRetroWave3D.game = game;
	}

	public static Window getWindow() {
		return window;
	}
}

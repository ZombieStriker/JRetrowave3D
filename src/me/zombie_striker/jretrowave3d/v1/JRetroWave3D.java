package me.zombie_striker.jretrowave3d.v1;

import me.zombie_striker.jretrowave3d.v1.data.ObjectChain;
import me.zombie_striker.jretrowave3d.v1.rendering.TriangleRenderer;
import me.zombie_striker.jretrowave3d.v1.inputs.Key;
import me.zombie_striker.jretrowave3d.v1.inputs.KeyListener;
import me.zombie_striker.jretrowave3d.v1.inputs.MouseListener;
import me.zombie_striker.jretrowave3d.v1.rendering.Input;
import me.zombie_striker.jretrowave3d.v1.rendering.Window;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JRetroWave3D {

    public static int tick = 0;
    private static GameEngine game;
    private static me.zombie_striker.jretrowave3d.v1.rendering.Window window;

    private static List<KeyListener> keylisteners = new ArrayList<>();
    private static List<MouseListener> mouseListeners = new ArrayList<>();

    public static GameEngine getGame() {
        return game;
    }

    public static void init() {

        //window_old = new Window_OLD();
        //window_old.init();

        window = new me.zombie_striker.jretrowave3d.v1.rendering.Window();
        window.start();

    }

    private static boolean[] lastCheckedInput = new boolean[1000];

    public static void start() {

        while (window.isRunning()) {
            long start = System.currentTimeMillis();


            for (Key key : Key.values()) {
                if (lastCheckedInput[key.getKeyCode()] != Input.keys[key.getKeyCode()]) {
                    if (Input.keys[key.getKeyCode()]) {
                        JRetroWave3D.callKey(key, true);
                    } else {
                        JRetroWave3D.callKey(key, false);
                    }
                    lastCheckedInput[key.getKeyCode()] = Input.keys[key.getKeyCode()];
                }
            }

            long time1 = System.currentTimeMillis();
            game.tick();
            if (getWindow().getScreen() != null) {
                ObjectChain<TriangleRenderer> renderChain = new ObjectChain<>(null, null);
                ObjectChain<TriangleRenderer> currentChain = renderChain;
                long time2 = System.currentTimeMillis();
                currentChain = game.getWorld().render(getWindow().getScreen(), renderChain, currentChain);
                long time3 = System.currentTimeMillis();
                currentChain = game.render(getWindow().getScreen(), renderChain, currentChain);
                System.out.println(((time2-time1)+" "+(time3 - time2)+"  "+(System.currentTimeMillis()-time3)));
                getWindow().getScreen().setRenderChain(renderChain);
            }
            try {
                long wait = (10 - (System.currentTimeMillis() - start));
                if (wait > 0)
                    Thread.sleep(wait);
            } catch (InterruptedException e) {
            }
        }
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

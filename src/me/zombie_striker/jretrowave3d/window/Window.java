package me.zombie_striker.jretrowave3d.window;

import me.zombie_striker.jretrowave3d.JRetroWave3D;
import me.zombie_striker.jretrowave3d.TickManager;
import me.zombie_striker.jretrowave3d.data.TickableObject;
import me.zombie_striker.jretrowave3d.graphics.Screen;
import me.zombie_striker.jretrowave3d.graphics.Shader;
import me.zombie_striker.jretrowave3d.inputs.Key;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements Runnable {


	private int width = 1900;
	private int height = 1200 - 50;
	private long window;
	private Thread windowThread = new Thread(this);
	private boolean running = true;

	private int tick = 0;

	private Screen screen;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void start() {
		windowThread.start();
	}

	@Override
	public void run() {
		init();
		while (running) {
			update();
			render();
			tick();
			if (glfwWindowShouldClose(window)) {
				running = false;
			}
		}

	}

	public int getTick(){
		return tick;
	}
	public void tick(){
		tick++;
	}

	public void init() {
		if (!glfwInit()) {
			System.out.println("Failed to init!");
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "Test Window", NULL, NULL);

		if (window == NULL) {
			return;
		}
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);

		GL.createCapabilities();


		glfwSetKeyCallback(window, new Input());
		glfwSetWindowSizeCallback(window, new WindowResizeCallback());
		glfwSetCursorPosCallback(window,new CursorMoveCallback());
		glfwSetMouseButtonCallback(window,new MouseClickCallback());

		glClearColor(1.0f, 0.9f, 0.9f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		//glActiveTexture(GL_TEXTURE1);

		Shader.loadAll();

		Shader.SCREEN.setUniform1i("tex", 1);

		//TODO: ORthigraphic
		screen = new Screen(width, height);

	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		screen.render();
		int i = glGetError();
		if (i != GL_NO_ERROR) {
			System.out.println("Error = " + i);
		}
		glfwSwapBuffers(window);
	}

	private void update() {
		glfwPollEvents();
	}

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		this.screen = new Screen(w, h);
	}

	public Screen getScreen() {
		return screen;
	}

	public boolean isRunning() {
		return running;
	}
}

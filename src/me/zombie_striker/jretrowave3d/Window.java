package me.zombie_striker.jretrowave3d;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

	private Display display = new Display();


	public Window() {
		super("Test");
	}


	public Display getDisplay(){
		return display;
	}
	public void init() {
		this.add(display);
		this.setPreferredSize(new Dimension(1000, 1000));
		this.setSize(1000, 1000);
		this.pack();
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

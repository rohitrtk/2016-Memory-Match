package com.rtk.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import com.rtk.gui.GUI;

// This class handles the main draw loop of the program, draw loop being
// the tick and render methods
public class Memory extends JComponent implements Runnable, MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 600;								// Width of the screen
	public static final int HEIGHT = 600;								// Height of the screen
	
	private GUI gui;													// New GUI object
	
	private final int FPS = 60;											// Frames Per Second
	private final int TARGETTIME = 1000 / FPS;							// Target Time
	
	private boolean isRunning = false;									// Is the program running
	private Thread thread;												// New Thread object
	
	public Memory() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));					// Sets the size of the window
	
		addMouseListener(this);											// New mouse listener for the JFrame
		
		init();
	}
	
	private void init() {												// Inits my objects
		isRunning = true;
		gui = new GUI();
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		long start;														// When did the program start
		long elapsed;													// How long has the program been running
		long wait;														// Pause the thread to keep 20 FPS
		
		while(isRunning) {
			start = System.nanoTime();
			
			tick();
			repaint();
			
			elapsed = System.nanoTime() - start;
			wait = TARGETTIME - elapsed / 1000000;
			
			if(wait < 1) {
				wait = 3;
			}
			
			try {
				Thread.sleep(wait);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick() {
		gui.tick();
	}
	
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		gui.render(g);
	}

	public void mouseClicked(MouseEvent e) {
		gui.mouseClicked(e);
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}	
}

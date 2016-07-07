package com.rtk.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class MyButton extends Rectangle {
	
	private static final long serialVersionUID = 1L;

	private String title;
	
	public MyButton(String title, int x, int y) {
		setBounds(x, y, 100, 50);
		
		this.title = title;
		
		init();
	}
	
	private void init() {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawRect(x, y, width, height);
		g.drawString(title, x + 25, y + 25);
	}
	
	public static boolean collide(Point p, MyButton b) {
		return b.contains(p);
	}
}

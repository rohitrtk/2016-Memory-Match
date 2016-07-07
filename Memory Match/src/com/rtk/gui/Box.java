package com.rtk.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

// This class handles each induvidual box on the grid, the grid will be made up of a 2D
// array of the boxes which will represent one card to be flipped
@SuppressWarnings("unused")
public class Box extends Rectangle {

	private static final long serialVersionUID = 1L;

	public static final int size = 100;
	
	private int id;
	
	private Color[] colours;
	private Color currentColour;
	
	public boolean selected = false;
	private boolean remove = false;
	
	public Box(int x, int y, int id) {
		setBounds(x, y, size, size);
		setId(id);
		
		colours = new Color[8];
		for(int i = 0;i < colours.length;i++) {
			switch(i) {
			case 0:
				colours[i] = Color.GREEN;
				break;
			case 1:
				colours[i] = Color.BLUE;
				break;
			case 2:
				colours[i] = Color.RED;
				break;
			case 3:
				colours[i] = Color.CYAN;
				break;
			case 4:
				colours[i] = Color.MAGENTA;
				break;
			case 5:
				colours[i] = Color.ORANGE;
				break;
			case 6: 
				colours[i] = Color.YELLOW;
				break;
			case 7:
				colours[i] = Color.PINK;
				break;
			}
		}
		
		if(id == 0) {
			currentColour = colours[0];
		} else {
			currentColour = colours[id / 2];
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(!remove) {
			if(!selected) {
				g.setColor(Color.BLACK);
				g.drawRect(x, y, size, size);			
			} else if(selected) {
				g.setColor(currentColour);
				g.fillRect(x, y, size, size);			
			}
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, size, size);	
		}
		
		//g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		//g.drawString("" + id, x + (size / 2) - 10, y + (size / 2));
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static boolean collide(Point p, Box b) {
		return b.contains(p);
	}
}

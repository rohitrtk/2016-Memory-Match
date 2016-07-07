package com.rtk.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

// This class handles all the GUI for the program
public class GUI {

	private ArrayList<Integer> cards;												// New list which will hold the 'cards'

	private Random random;															// New random object
	
	private Box[][] grid;															// 2D array of boxes
	private int numBoxes = 4;														// How many boxes in the horizontal and vertical
	
	private int counter = -1;														// Counter variable for mouse clicked
	private int[] numbersSelected;													// Which box id's are currently selected
	
	private int[] g1;																// Stores the first grid id
	private int[] g2;																// Stores the second grid id
	
	private boolean keep = false;													// Will the boxes go blank again
	
	private MyButton[] myButtons;
	
	private int turn = 0;															// Shows the current turn
	private int currentWin = 0;														// Current win number
	private final int WINNUM = 8;													// Number required to win game
	
	public GUI() {
		init();
	}
	
	private void init() {
		
		myButtons = new MyButton[2];
		for(int i = 0;i < myButtons.length;i++) {
			String string;
			
			if(i == 0) string = "Instructions";
			else string = "Reset";
			
			myButtons[i] = new MyButton(string, 100 + (i * 300), 0);
		}
		
		g1 = new int[2];
		g2 = new int[2];
		
		numbersSelected = new int[2];
		for(int i = 0;i < numbersSelected.length;i++) {
			numbersSelected[i] = -1;
		}
		
		cards = new ArrayList<Integer>();
		for(int i = 0;i < Math.pow(numBoxes, 2);i++) {								// Makes 2 of each number in the array
			if(i == 0 || i % 2 == 0) {												// list starting at 0 and going up
				cards.add(i);														// by 2 each time
			} else {																// (0,0,2,2,4,4,6,6,8,8)
				cards.add(i-1);
			}
		}
		
		random = new Random();
		int id;
		int randomNumber;
		
		grid = new Box[numBoxes][numBoxes];											// Inits the 2D array
		for(int i = 0;i < numBoxes;i++) {											// Populates the 2D array
			for(int j = 0;j < grid[i].length;j++) {
				randomNumber = random.nextInt(cards.size());
				id = cards.get(randomNumber);
				grid[j][i] = new Box((j * Box.size) + Box.size, (i * Box.size) + Box.size, id);
				cards.remove(randomNumber);
			}
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		for(int i = 0;i < myButtons.length;i++) {
			myButtons[i].render(g);
		}
		
		for(int i = 0;i < numBoxes;i++) {
			for(int j = 0;j < grid[i].length;j++) {
				grid[j][i].render(g);												// Renders the boxes to the screen
			}
		}
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.setColor(Color.BLACK);
		g.drawString("Current Turn: " + (int) Math.floor(turn) / 2, 245, 25);
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();															// xpos of mouse
		int y = e.getY();															// ypos of mouse
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(x > Box.size && x < (Box.size * numBoxes) + Box.size &&
					y > Box.size && y < (Box.size * numBoxes) + Box.size) {
				for(int i = 0;i < numBoxes;i++) {
					for(int j = 0;j < grid[i].length;j++) {
						if(Box.collide(new Point(x, y), grid[j][i])) {
							if(grid[j][i].selected) {
								continue;
							}
							//System.out.println(grid[j][i].getId());
							counter++;
							
							if(counter == 0 || counter == 1) {
								turn++;
								numbersSelected[counter] = grid[j][i].getId();
								grid[j][i].selected = true;		
								
								if(counter == 0) {
									g1[0] = j;
									g1[1] = i;
								} else if(counter == 1) {
									g2[0] = j;
									g2[1] = i;
									
									if(numbersSelected[0] == numbersSelected[1]) {
										currentWin++;
										//System.out.println("WORKS");
										
										grid[g1[0]][g1[1]].selected = true;
										grid[g2[0]][g2[1]].selected = true;
										keep = true;
									} else {
										keep = false;
									}
								}
							} else if(counter == 2) {
								if(!keep) {
									//System.out.println(keep);
									grid[g1[0]][g1[1]].selected = false;
									grid[g2[0]][g2[1]].selected = false;
								}
								counter = -1;
								for(int k = 0;k < numbersSelected.length;k++) {
									numbersSelected[k] = -1;
								}
							}
							
							if(currentWin == WINNUM) {
								JOptionPane.showMessageDialog(null, "VICTORY!\n"
										+ "Congratulations, you won!\n"
										+ "Press RESET to play again!", "VICTORY!",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}		
					}
				}				
			} else if(x > myButtons[0].x && x < myButtons[0].x + myButtons[0].width &&
					y > myButtons[0].y && y < myButtons[0].y + myButtons[0].height) {
				instructions();
			} else if(x > myButtons[1].x && x < myButtons[1].x + myButtons[1].width && 
					y > myButtons[1].y && y < myButtons[1].y + myButtons[1].height) {
				reset();
			} else {
				//System.out.println("You are outside the box!");
			}
		}
	}
	
	private void instructions() {
		JOptionPane.showMessageDialog(null, "There are 16 squares in total and 2 of each colour.\n"
				+ "Click on squares to reveal what colour they are.\n"
				+ "Then click on another square and try to match the two colours!\n"
				+ "GLHF!",
				"Instructions!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void reset() {
		for(int i = 0;i < Math.pow(numBoxes, 2);i++) {								// Makes 2 of each number in the array
			if(i == 0 || i % 2 == 0) {												// list starting at 0 and going up
				cards.add(i);														// by 2 each time
			} else {																// (0,0,2,2,4,4,6,6,8,8)
				cards.add(i-1);
			}
		}
		
		int id;
		int randomNumber;
		
		for(int i = 0;i < numBoxes;i++) {											// Populates the 2D array
			for(int j = 0;j < grid[i].length;j++) {
				randomNumber = random.nextInt(cards.size());
				id = cards.get(randomNumber);
				grid[j][i] = new Box((j * Box.size) + Box.size, (i * Box.size) + Box.size, id);
				cards.remove(randomNumber);
			}
		}
		
		counter = -1;
		turn = 0;
	}
}

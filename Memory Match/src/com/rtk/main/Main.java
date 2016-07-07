package com.rtk.main;

import javax.swing.JFrame;

// This class is the main entry point to the program
public class Main {

	/*
	 * Created by Rohit Terry Kisto
	 * Date Created 3/6/2016
	 * Purpose: Have a game board created using 2D graphics that will allow the user to
	 * play a simple memory match game.
	 */
	
	public Main() {
		JFrame f = new JFrame("Memory Match!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(new Memory());
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.pack();
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}

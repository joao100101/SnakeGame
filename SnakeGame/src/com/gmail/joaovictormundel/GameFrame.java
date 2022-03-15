package com.gmail.joaovictormundel;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameFrame extends JFrame {

	GameFrame() {
		this.add(new GamePanel());

		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}
}

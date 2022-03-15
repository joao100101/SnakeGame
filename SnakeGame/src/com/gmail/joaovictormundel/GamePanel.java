package com.gmail.joaovictormundel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -840950680973680431L;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 125;// 75
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 3;
	int applesEaten;
	int appleX;
	int appleY;
	Direction direction = Direction.RIGHT;
	boolean running = false;
	Timer timer;
	Random random;
	JLabel pontos = new JLabel();
	JLabel gameover = new JLabel();

	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		pontos.setText("Pontuaçao: " + applesEaten);
		pontos.setAlignmentX(25);
		pontos.setAlignmentY(25);
		pontos.setForeground(Color.yellow);

		gameover.setText("");
		gameover.setForeground(Color.red);
		this.add(gameover);
		this.add(pontos);
		startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void restartGame() {
		gameover.setText("");
		bodyParts = 3;
		applesEaten = 0;
		pontos.setText("Pontuaçao: " + applesEaten);
		Arrays.fill(x, 0);
		x[0] = 25;
		y[0] = 25;
		x[1] = 25;
		y[1] = 24;
		x[2] = 25;
		y[2] = 23;
		direction = Direction.RIGHT;
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
			g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
		}
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

		for (int i = 0; i < bodyParts; i++) {
			if (i == 0) {
				g.setColor(Color.orange);
				if (direction != Direction.DOWN) {
					g.drawString("joao100101", x[i] - 25, y[i] - 25);
				}
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			} else {
				g.setColor(new Color(45, 180, 0));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

			}
		}
	}

	public void newApple() {
		appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	public void move() {
		checkApple();
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		switch (direction) {
		case RIGHT:
			if (direction != Direction.LEFT) {
				x[0] = x[0] + UNIT_SIZE;
			}
			break;
		case LEFT:
			if (direction != Direction.RIGHT) {
				x[0] = x[0] - UNIT_SIZE;
			}
			break;
		case UP:
			if (direction != Direction.DOWN) {
				y[0] = y[0] - UNIT_SIZE;
			}
			break;
		case DOWN:
			if (direction != Direction.UP) {
				y[0] = y[0] + UNIT_SIZE;
			}
			break;
		}

	}

	public void checkApple() {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			Utils.playSound(Sons.COIN);
			applesEaten++;
			bodyParts++;
			newApple();
			pontos.setText("Pontuaçao: " + applesEaten);
		}
	}

	public void checkCollisions() {
		// Checando se cobra colidiu com o corpo
		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
				Utils.playSound(Sons.GAME_OVER);
				gameover.setText("GAME OVER");
			}
		}
		// checando se colidiu com a borda do mapa
		if (x[0] < 0 || x[0] >= SCREEN_WIDTH) {
			running = false;
			Utils.playSound(Sons.GAME_OVER);
			gameover.setText("GAME OVER");
		}
		if (y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
			running = false;
			Utils.playSound(Sons.GAME_OVER);
			gameover.setText("GAME OVER");
		}
		if (!running) {
			timer.stop();
			Utils.playSound(Sons.GAME_OVER);
			gameover.setText("GAME OVER");
		}
	}

	public void gameOver(Graphics g) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_R:
				if (!running) {
					restartGame();
				}
				break;
			case KeyEvent.VK_W:
				if (direction != Direction.DOWN) {
					direction = Direction.UP;
				}
				break;
			case KeyEvent.VK_S:
				if (direction != Direction.UP) {
					direction = Direction.DOWN;
			
				}
				break;
			case KeyEvent.VK_D:
				if (direction != Direction.LEFT) {
					direction = Direction.RIGHT;
				}
				break;
			case KeyEvent.VK_A:
				if (direction != Direction.RIGHT) {
					direction = Direction.LEFT;
				}
				break;
			}
		}

	}
}

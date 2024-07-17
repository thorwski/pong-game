package Game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1024;
	public static final int HEIGHT = WIDTH * 9 / 16;
	public static final String TITLE = "Pong";

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

	private Thread thread;
	private boolean running = false;

	private Ball ball;
	private Paddle userPaddle;
	private Paddle computerPaddle;
	private Score userScore;
	private Score computerScore;

	private boolean upPressed = false;
	private boolean downPressed = false;

	public Game() {
		ball = new Ball(Ball.BALL_SIZE, Ball.CENTER_X, Ball.CENTER_Y, Ball.BALL_SPEED, Ball.BALL_SPEED, Color.WHITE);
		userPaddle = new Paddle(Paddle.USER_POSITION, Paddle.CENTER_Y, Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT, Color.ORANGE);
		computerPaddle = new Paddle(Paddle.COMPUTER_POSITION, Paddle.CENTER_Y, Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT, Color.RED);
		userScore = new Score("Player: ", Score.USER_POSITION_X, Score.POSITION_Y, Color.WHITE);
		computerScore = new Score("Computer: ", Score.COMPUTER_POSITION_X, Score.POSITION_Y, Color.WHITE);

		addKeyListener(this);
		setFocusable(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (running) {
			update();
			render();
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		ball.move();
		ball.checkCollision();
		userPaddle.checkCollion(ball);
		computerPaddle.checkCollion(ball);

		if (upPressed) {
			userPaddle.moveUp();
		}

		if (downPressed) {
			userPaddle.moveDown();
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		ball.draw(g);
		userPaddle.draw(g);
		computerPaddle.draw(g);
		userScore.draw(g);
		computerScore.draw(g);

		g.dispose();

		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		JFrame f = new JFrame(Game.TITLE);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(game);
		f.pack();
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);

		game.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (key == KeyEvent.VK_S) {
			downPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (key == KeyEvent.VK_S) {
			downPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}

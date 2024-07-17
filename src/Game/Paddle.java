package Game;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Paddle {

	private int x, y;
	private int width, height;
	private Color color;

	public static final int PADDLE_WIDTH = 20;
	public static final int PADDLE_HEIGHT = 125;
	public static final int PADDLE_SPEED = 6;

	private Clip hitPaddleSound;
	
	public static final int USER_POSITION = 30;
	public static final int COMPUTER_POSITION = Game.WIDTH - PADDLE_WIDTH - 30;
	public static final int CENTER_Y = (Game.HEIGHT - PADDLE_HEIGHT) / 2;

	public Paddle(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;

		loadSounds();
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

	public void checkCollion(Ball b) {
		if (b.getX() < x + width && b.getX() + b.getSize() > x) {
			if (b.getY() < y + height && b.getY() + b.getSize() > y) {
				b.reverseX();
				playHitPaddleSound();
				if (b.getX() < x) {
					b.setX(x - b.getSize());
				} else {
					b.setX(x + width);
				}
			}
		}
	}

	public void moveUp() {
		if (y - PADDLE_SPEED >= 0) {
			y -= PADDLE_SPEED;
		}
	}

	public void moveDown() {
		if (y + height + PADDLE_SPEED <= Game.HEIGHT) {
			y += PADDLE_SPEED;
		}
	}

	private void loadSounds() {
		try {
			File hitPaddleFile = new File("src\\sounds\\hit_paddle.wav");
			AudioInputStream paddleStream = AudioSystem.getAudioInputStream(hitPaddleFile);
			hitPaddleSound = AudioSystem.getClip();
			hitPaddleSound.open(paddleStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void playHitPaddleSound() {
		hitPaddleSound.setFramePosition(0);
		hitPaddleSound.start();
	}
}

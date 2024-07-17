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

public class Ball {

	private int size;
	private int x, y;
	private int sx, sy; // SPEED X and SPEED Y
	private Color color;

	public static final int BALL_SIZE = 30;
	public static final int BALL_SPEED = 10;

	private Clip hitWallSound;
	
	public static final int CENTER_X = (Game.WIDTH - BALL_SIZE) / 2;
	public static final int CENTER_Y = (Game.HEIGHT - BALL_SIZE) / 2;

	public Ball(int size, int x, int y, int sx, int sy, Color color) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.sx = sx;
		this.sy = sy;
		this.color = color;
		
		loadSounds();
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, size, size);
	}

	public void move() {
		x += sx;
		y += sy;
	}

	public void checkCollision() {
		if (x < 0 || x > Game.WIDTH - size) {
			reverseX();
			playHitWallSound();

		}

		if (y < 0 || y > Game.HEIGHT - size) {
			reverseY();
			playHitWallSound();
		}
	}

	public void reverseX() {
		sx = -sx;
	}

	public void reverseY() {
		sy = -sy;
	}

	private void loadSounds() {
		try {
			File hitWallFile = new File("src\\sounds\\hit_wall.wav");
			AudioInputStream wallStream = AudioSystem.getAudioInputStream(hitWallFile);
			hitWallSound = AudioSystem.getClip();
			hitWallSound.open(wallStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void playHitWallSound() {
		hitWallSound.setFramePosition(0);
		hitWallSound.start();
	}

	public int getSize() {
		return size;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSx() {
		return sx;
	}

	public int getSy() {
		return sy;
	}

	public Color getColor() {
		return color;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSx(int sx) {
		this.sx = sx;
	}

	public void setSy(int sy) {
		this.sy = sy;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}

package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Score {

	private String player;
	private int x, y;
	private int userScore;
	private int computerScore;
	private Color color;
	private Font font;
	
	public static final int USER_POSITION_X = 30;
	public static final int COMPUTER_POSITION_X = 850;
	public static final int POSITION_Y = 50;

	public Score(String player, int x, int y, Color color) {
		this.player = player;
		this.x = x;
		this.y = y;
		this.userScore = 0;
		this.computerScore = 0;
		this.color = color;
		this.font = new Font("Arial", Font.BOLD, 25);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.setFont(font);
		g.drawString(player + userScore, x, y);
		//g.drawString("Computer: " + computerScore, x, y);
	}
	
	public void increaseUserScore() {
		userScore++;
	}

	public void increaseComputerScore() {
		computerScore++;
	}
	
	public int getUserScore() {
        return userScore;
    }

    public int getComputerScore() {
        return computerScore;
    }
}

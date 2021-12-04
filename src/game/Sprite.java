package game;

import javafx.scene.image.Image;

public class Sprite extends GameElement {
	protected final static int SPRITE_MOVING_DISTANCE = 10;

	protected int dX, dY;
	private int lowestXPos = GameArea.LOWER_X_BOUND;
	private int highestXPos = GameArea.UPPER_X_BOUND - width;
	private int lowestYPos = GameArea.LOWER_Y_BOUND;
	private int highestYPos = GameArea.UPPER_Y_BOUND - height;

	public Sprite(int xPos, int yPos, Image image) {
		super(xPos, yPos, image);
	}

	void moveUp() {
		dY = -1 * SPRITE_MOVING_DISTANCE;
	}

	void moveDown() {
		dY = SPRITE_MOVING_DISTANCE;
	}

	void moveLeft() {
		dX = -1 * SPRITE_MOVING_DISTANCE;
	}

	void moveRight() {
		dX = SPRITE_MOVING_DISTANCE;
	}

	void stopMovingHorizontally() {
		dX = 0;
	}

	void stopMovingVertically() {
		dY = 0;
	}

	void updatePosition() {
		xPos += dX;
		if (xPos < lowestXPos) xPos = lowestXPos;
		else if (xPos > highestXPos) xPos = highestXPos;

		yPos += dY;
		if (yPos < lowestYPos) yPos = lowestYPos;
		else if (yPos > highestYPos) yPos = highestYPos;
	}
}

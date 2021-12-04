package game;

import javafx.scene.image.Image;

public class Sprite extends GameElement {

	private int dX, dY;
	private int movementSpeed;

	public Sprite(int xPos, int yPos, Image image) {
		super(xPos, yPos, image);
	}

	protected void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	void moveUp() {
		dY = -1 * movementSpeed;
	}

	void moveDown() {
		dY = movementSpeed;
	}

	void moveLeft() {
		dX = -1 * movementSpeed;
	}

	void moveRight() {
		dX = movementSpeed;
	}

	void stopMovingHorizontally() {
		dX = 0;
	}

	void stopMovingVertically() {
		dY = 0;
	}

	void updatePosition() {
		int lowestXPos = GameArea.LOWER_X_BOUND;
		int highestXPos = GameArea.UPPER_X_BOUND - width;
		int lowestYPos = GameArea.LOWER_Y_BOUND;
		int highestYPos = GameArea.UPPER_Y_BOUND - height;

		xPos += dX;
		if (xPos < lowestXPos) xPos = lowestXPos;
		else if (xPos > highestXPos) xPos = highestXPos;

		yPos += dY;
		if (yPos < lowestYPos) yPos = lowestYPos;
		else if (yPos > highestYPos) yPos = highestYPos;
	}
}

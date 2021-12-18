package game;

import javafx.scene.image.Image;

import views.View;

abstract class MovableSprite extends Sprite {

	private int dX, dY, movementSpeed;

	public MovableSprite(int xPos, int yPos, Image image) {
		super(xPos, yPos, image);
	}

	void moveUp() {
		dY = -1 * movementSpeed;
	}

	void moveDown() {
		dY = movementSpeed;
	}

	void moveLeft() {
		dX = -1 * movementSpeed;
		faceLeft();
	}

	void moveRight() {
		dX = movementSpeed;
		faceRight();
	}

	void stopMovingHorizontally() {
		dX = 0;
	}

	void stopMovingVertically() {
		dY = 0;
	}

	void updatePosition() {
		int highestXPos = View.WINDOW_WIDTH - this.width;
		int highestYPos = View.WINDOW_HEIGHT - this.height;

		xPos += dX;
		if (xPos < 0) xPos = 0;
		else if (xPos > highestXPos) xPos = highestXPos;

		yPos += dY;
		if (yPos < 0) yPos = 0;
		else if (yPos > highestYPos) yPos = highestYPos;
	}

	protected void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
}

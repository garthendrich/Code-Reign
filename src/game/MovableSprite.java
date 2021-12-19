package game;

import javafx.scene.image.Image;

import views.View;

abstract class MovableSprite extends Sprite {

	private int dX;
	private int dY;
	private int movementSpeed;

	MovableSprite(int xPos, int yPos, Image image) {
		super(xPos, yPos, image);
	}

	void moveLeft() {
		dX = -movementSpeed;
		faceLeft();
	}

	void moveRight() {
		dX = movementSpeed;
		faceRight();
	}

	void moveUp() {
		dY = -movementSpeed;
	}

	void moveDown() {
		dY = movementSpeed;
	}

	void stopMovingHorizontally() {
		dX = 0;
	}

	void stopMovingVertically() {
		dY = 0;
	}

	/**
	 * Updates the sprite's position based on its dx and dy. The updated position is bounded by
	 * the window's edges.
	 */
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
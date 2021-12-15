package game;

import main.Main;

abstract class Sprite extends GameElement {

	private int dX, dY;
	private int movementSpeed;

	public Sprite(int xPos, int yPos) {
		super(xPos, yPos);
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
		int highestXPos = Main.WINDOW_WIDTH - this.width;
		int highestYPos = Main.WINDOW_HEIGHT - this.height;

		xPos += dX;
		if (xPos < 0) xPos = 0;
		else if (xPos > highestXPos) xPos = highestXPos;

		yPos += dY;
		if (yPos < 0) yPos = 0;
		else if (yPos > highestYPos) yPos = highestYPos;
	}
}

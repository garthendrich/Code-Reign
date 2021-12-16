package game;

import javafx.scene.image.Image;

import main.Main;

abstract class MovableSprite extends Sprite {

	private int dX, dY;

	public MovableSprite(int xPos, int yPos, Image image) {
		super(xPos, yPos, image);
	}

	void moveUp(int distance) {
		dY = -1 * distance;
	}

	void moveDown(int distance) {
		dY = distance;
	}

	void moveLeft(int distance) {
		dX = -1 * distance;
	}

	void moveRight(int distance) {
		dX = distance;
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

package game;

import javafx.scene.image.Image;
import views.View;

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
		faceLeft();
	}

	void moveRight(int distance) {
		dX = distance;
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
}

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
		int highestXPos = GameStage.CANVAS_WIDTH - this.width;
		int highestYPos = GameStage.CANVAS_HEIGHT - this.height;

		xPos += dX;
		if (xPos < 0) xPos = 0;
		else if (xPos > highestXPos) xPos = highestXPos;

		yPos += dY;
		if (yPos < 0) yPos = 0;
		else if (yPos > highestYPos) yPos = highestYPos;
	}
}

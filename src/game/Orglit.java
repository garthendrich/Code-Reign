package game;

import java.util.Random;
import javafx.scene.image.Image;

import views.View;

class Orglit extends MovableSprite {

	public final static int WIDTH = 24;
	public final static int HEIGHT = 28;
	public final static Image IMAGE = new Image("assets/images/orglit.png", WIDTH, HEIGHT, false, false);
	public final static int MIN_MOVEMENT_SPEED = 1;
	public final static int MAX_MOVEMENT_SPEED = 5;

	private int movementSpeed;
	protected int damage = 30;

	public Orglit(int xPos, int yPos) {
		this(xPos, yPos, IMAGE);
	}

	public Orglit(int xPos, int yPos, Image image) {
		super(xPos, yPos, image);

		Random randomizer = new Random();

		movementSpeed = MIN_MOVEMENT_SPEED + randomizer.nextInt(MAX_MOVEMENT_SPEED - MIN_MOVEMENT_SPEED + 1);

		switch(randomizer.nextInt(2)) {
			case 0: moveLeft(movementSpeed); break;
			case 1: moveRight(movementSpeed); break;
			default:
		}
	}

	public int getDamage() {
		return damage;
	}

	@Override
	void updatePosition() {
		super.updatePosition();
		updateMovement();
	}

	void updateMovement() {
		boolean isOrglitAtLeftmostEdge = (xPos == 0);
		boolean isOrglitAtRightmostEdge = (xPos == View.WINDOW_WIDTH - this.width);

		if (isOrglitAtLeftmostEdge) moveRight(movementSpeed);
		else if (isOrglitAtRightmostEdge) moveLeft(movementSpeed);
	}
}

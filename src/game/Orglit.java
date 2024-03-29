package game;

import java.util.Random;
import javafx.scene.image.Image;

import views.View;

class Orglit extends MovableSprite {

	public final static int WIDTH = 24;
	public final static int HEIGHT = 28;
	public final static Image IMAGE = new Image("assets/images/orglit.png", WIDTH, HEIGHT, false, false);
	public final static int DAMAGE = 30;
	public final static int MIN_MOVEMENT_SPEED = 1;
	public final static int MAX_MOVEMENT_SPEED = 5;

	private int damage;

	Orglit(int xPos, int yPos) {
		this(xPos, yPos, IMAGE, DAMAGE);
	}

	Orglit(int xPos, int yPos, Image image, int damage) {
		super(xPos, yPos, image);

		this.damage = damage;

		setupRandomInitialMovement();
	}

	/**
	 * Randomly sets the orglit's movement speed between the determined values and randomly chooses
	 * an initial movement of either left or right.
	 */
	private void setupRandomInitialMovement() {
		Random randomizer = new Random();

		int randomMovementSpeed = MIN_MOVEMENT_SPEED + randomizer.nextInt(MAX_MOVEMENT_SPEED - MIN_MOVEMENT_SPEED + 1);
		setMovementSpeed(randomMovementSpeed);

		switch(randomizer.nextInt(2)) {
			case 0: moveLeft(); break;
			case 1: moveRight(); break;
			default:
		}
	}

	@Override
	void updatePosition() {
		super.updatePosition();
		updateMovement();
	}

	/**
	 * Updates whether the orglit will now move either to the left or right.
	 *
	 * When the orglit bumps into the leftmost edge of the window, it moves to the right direction;
	 * and when it bumps into the rightmost edge of the window, it moves to the left direction.
	 */
	private void updateMovement() {
		boolean isOrglitAtLeftmostEdge = (xPos == 0);
		boolean isOrglitAtRightmostEdge = (xPos == View.WINDOW_WIDTH - this.width);

		if (isOrglitAtLeftmostEdge) moveRight();
		else if (isOrglitAtRightmostEdge) moveLeft();
	}

	int getDamage() {
		return damage;
	}
}
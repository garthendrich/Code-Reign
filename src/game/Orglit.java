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

	public Orglit(int xPos, int yPos) {
		this(xPos, yPos, IMAGE, DAMAGE);
	}

	public Orglit(int xPos, int yPos, Image image, int damage) {
		super(xPos, yPos, image);

		this.damage = damage;

		setupRandomInitialMovement();
	}

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

		if (isOrglitAtLeftmostEdge) moveRight();
		else if (isOrglitAtRightmostEdge) moveLeft();
	}
}

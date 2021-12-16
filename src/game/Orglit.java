package game;

import java.util.Random;
import javafx.scene.image.Image;

import main.Main;

class Orglit extends MovableSprite {

	public final static int WIDTH = 24;
	public final static int HEIGHT = 28;
	public final static Image IMAGE = new Image("assets/images/orglit.png", WIDTH, HEIGHT, false, false);
	private final static int MIN_MOVEMENT_SPEED = 1;
	private final static int MAX_MOVEMENT_SPEED = 5;

	private boolean isAlive = true;
	protected int damage = 30;

	public Orglit(int x, int y) {
		super(x,y);

		setImage(IMAGE);
		setRandomMovementSpeed();
		setRandomInitialMovement();
	}

	private void setRandomMovementSpeed() {
		Random randomizer = new Random();
		int movementSpeed = MIN_MOVEMENT_SPEED + randomizer.nextInt(MAX_MOVEMENT_SPEED - MIN_MOVEMENT_SPEED + 1);
		setMovementSpeed(movementSpeed);
	}

	private void setRandomInitialMovement() {
		Random randomizer = new Random();
		int randomDirectionIndex = randomizer.nextInt(2);
		switch(randomDirectionIndex) {
			case 0: moveLeft(); break;
			case 1: moveRight(); break;
			default:
		}
	}

	public int getDamage() {
		return damage;
	}

	void die() {
		isAlive = false;
	}

	boolean isAlive() {
		return isAlive;
	}

	@Override
	void updatePosition() {
		super.updatePosition();
		updateMovement();
	}

	void updateMovement() {
		boolean isOrglitAtLeftmostEdge = (xPos == 0);
		boolean isOrglitAtRightmostEdge = (xPos == Main.WINDOW_WIDTH - this.width);

		if (isOrglitAtLeftmostEdge) moveRight();
		else if (isOrglitAtRightmostEdge) moveLeft();
	}
}

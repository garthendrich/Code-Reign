package game;

import javafx.scene.image.Image;
import java.util.Random;

public class Orglit extends Sprite{

	private final static int MIN_MOVEMENT_SPEED = 1;
	private final static int MAX_MOVEMENT_SPEED = 5;
	public final static Image ORGLIT_IMAGE = new Image("images/fish.png", Orglit.WIDTH, Orglit.HEIGHT, false, false);
	public final static int WIDTH = 50;
	public final static int HEIGHT = 50;

	private boolean isAlive = true;
	protected int damage = 30;

	public Orglit(int x, int y) {
		super(x,y, ORGLIT_IMAGE);

		setRandomInitialMovement();
		Random randomizer = new Random();
		int movementSpeed = MIN_MOVEMENT_SPEED + randomizer.nextInt(MAX_MOVEMENT_SPEED - MIN_MOVEMENT_SPEED + 1);
		setMovementSpeed(movementSpeed);
	}

	private void setRandomInitialMovement() {
		Random randomizer = new Random();
		switch(randomizer.nextInt(2)) {
			case 0: moveLeft();
			case 1: moveRight();
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

	void updateMovement() {
		if (xPos == 0) {
			moveRight();
		} else if (xPos == GameStage.CANVAS_WIDTH - this.width) {
			moveLeft();
		}
	}
}

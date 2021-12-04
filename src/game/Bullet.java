package game;

import javafx.scene.image.Image;

public class Bullet extends Sprite{

	public final static Image BULLET_IMAGE = new Image("images/bullet.png", 25, 25, false, false);
	private final static int MOVEMENT_SPEED = 20;

	private int damage;
	private boolean hasCollided = false;

	public Bullet(int x, int y, int damage){
		super(x,y, BULLET_IMAGE);

		this.damage = damage;
		setMovementSpeed(MOVEMENT_SPEED);
		moveRight();
	}

	int getDamage() {
		return damage;
	}

	void collide() {
		hasCollided = true;
	}

	boolean hasCollided() {
		return hasCollided;
	}
}
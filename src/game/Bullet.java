package game;

import javafx.scene.image.Image;

import main.Main;;

class Bullet extends Sprite {

	public final static int WIDTH = 20;
	public final static int HEIGHT = 20;
	public final static Image IMAGE = new Image("assets/images/bullet.png", WIDTH, HEIGHT, false, false);
	private final static int MOVEMENT_SPEED = 20;

	private int damage;
	private boolean hasCollided = false;

	public Bullet(int x, int y, int damage){
		super(x,y);

		setImage(IMAGE);

		this.damage = damage;
		setMovementSpeed(MOVEMENT_SPEED);
		moveRight();
	}

	@Override
	void updatePosition() {
		super.updatePosition();

		boolean isBulletAtRightmostEdge = (xPos == GameStage.WINDOW_WIDTH - this.width);
		if (isBulletAtRightmostEdge) collide();
	}

	void collide() {
		hasCollided = true;
	}

	int getDamage() {
		return damage;
	}

	boolean hasCollided() {
		return hasCollided;
	}
}
package game;

import javafx.scene.image.Image;

class Bullet extends Sprite {

	public final static int WIDTH = 25;
	public final static int HEIGHT = 25;
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

	int getDamage() {
		return damage;
	}

	void collide() {
		hasCollided = true;
	}

	boolean hasCollided() {
		return hasCollided;
	}

	@Override
	void updatePosition() {
		super.updatePosition();

		boolean isBulletAtRightmostEdge = (xPos == GameStage.CANVAS_WIDTH - this.width);
		if (isBulletAtRightmostEdge) collide();
	}
}
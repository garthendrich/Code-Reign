package game;

import javafx.scene.image.Image;

import main.Main;;

class Bullet extends MovableSprite {

	public final static int WIDTH = 20;
	public final static int HEIGHT = 20;
	public final static Image IMAGE = new Image("assets/images/bullet.png", WIDTH, HEIGHT, false, false);
	public final static int MOVEMENT_SPEED = 8;

	private int damage;
	private boolean hasCollided = false;

	public Bullet(int xPos, int yPos, int damage) {
		this(xPos, yPos, damage, IMAGE);
	}

	public Bullet(int xPos, int yPos, int damage, Image image) {
		super(xPos, yPos, image);

		this.damage = damage;
	}

	@Override
	void updatePosition() {
		super.updatePosition();

		boolean isBulletAtRightmostEdge = (xPos == Main.WINDOW_WIDTH - this.width);
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
package game;

import javafx.scene.image.Image;

import views.View;

class Bullet extends MovableSprite {

	public final static int WIDTH = 20;
	public final static int HEIGHT = 20;
	public final static Image IMAGE = new Image("assets/images/bullet.png", WIDTH, HEIGHT, false, false);
	public final static int MOVEMENT_SPEED = 8;

	private int damage;

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
		manageCollisionToEdge();
	}

	private void manageCollisionToEdge() {
		boolean isBulletAtLeftmostEdge = (xPos == 0);
		boolean isBulletAtRightmostEdge = (xPos == View.WINDOW_WIDTH - this.width);
		boolean isBulletAtTopmostEdge = (yPos == 0);
		boolean isBulletAtBottommostEdge = (yPos == View.WINDOW_HEIGHT - this.height);

		if (isBulletAtLeftmostEdge || isBulletAtRightmostEdge || isBulletAtTopmostEdge || isBulletAtBottommostEdge) {
			this.vanish();
		}
	}

	int getDamage() {
		return damage;
	}
}
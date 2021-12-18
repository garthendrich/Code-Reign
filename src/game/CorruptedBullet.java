package game;

import javafx.scene.image.Image;

class CorruptedBullet extends Bullet {

	public final static int WIDTH = 24;
	public final static int HEIGHT = 24;
	public final static Image IMAGE = new Image("assets/images/corruptedBullet.png", WIDTH, HEIGHT, false, false);
	public final static int DAMAGE = 30;
	public final static int MOVEMENT_SPEED = 2;

	CorruptedBullet(int xPos, int yPos) {
		super(xPos, yPos, DAMAGE, IMAGE);

		setMovementSpeed(MOVEMENT_SPEED);
	}
}

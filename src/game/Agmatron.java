package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

class Agmatron extends Orglit {

	public final static int WIDTH = 88;
	public final static int HEIGHT = 96;
	public final static Image IMAGE = new Image("assets/images/agmatron.png", WIDTH, HEIGHT, false, false);
	public final static int MAX_HEALTH = 3000;
	public final static int DAMAGE = 50;

	private int health;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public Agmatron(int xPos, int yPos) {
		super(xPos, yPos, IMAGE, DAMAGE);

		health = MAX_HEALTH;
	}

	void shoot() {
		int bulletXPos = this.xPos - Bullet.WIDTH;
		int bulletYPos = this.yPos - Bullet.HEIGHT + (this.height / 2);
		Bullet bullet = new CorruptedBullet(bulletXPos, bulletYPos);
		bullet.moveLeft();

		Random randomizer = new Random();
		switch(randomizer.nextInt(2)) {
			case 0: bullet.moveUp(); break;
			case 1: bullet.moveDown(); break;
			default:
		}

		bullets.add(bullet);
    }

	void reduceHealthBy(int amount) {
		health -= amount;

		if (health <= 0) {
			this.vanish();
		}
	}

	int getHealth() {
		return health;
	}

	ArrayList<Bullet> getBullets() {
		return bullets;
	}
}

package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

class Agmatron extends Orglit {

	public final static int WIDTH = 88;
	public final static int HEIGHT = 96;
	public final static Image IMAGE = new Image("assets/images/agmatron.png", WIDTH, HEIGHT, false, false);
	public final static int SMASH_DAMAGE = 50;

	private int health = 3000;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public Agmatron(int xPos, int yPos) {
		super(xPos, yPos, IMAGE);

		damage = SMASH_DAMAGE;
	}

	void shoot() {
		Random randomizer = new Random();

		int bulletXPos = this.xPos - Bullet.WIDTH;
		int bulletYPos = this.yPos - Bullet.HEIGHT + randomizer.nextInt(this.height);
		Bullet bullet = new CorruptedBullet(bulletXPos, bulletYPos);
		bullet.moveLeft(CorruptedBullet.MOVEMENT_SPEED);

		switch(randomizer.nextInt(2)) {
			case 0: bullet.moveUp(CorruptedBullet.MOVEMENT_SPEED); break;
			case 1: bullet.moveDown(CorruptedBullet.MOVEMENT_SPEED); break;
			default:
		}

		bullets.add(bullet);
    }

	void reduceHealthBy(int amount) {
		health -= amount;

		if (health <= 0) {
			die();
		}
	}

	ArrayList<Bullet> getBullets() {
		return bullets;
	}
}

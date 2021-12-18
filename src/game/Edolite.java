package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

class Edolite extends MovableSprite {

	public final static int WIDTH = 32;
	public final static int HEIGHT = 44;
	public final static Image IMAGE = new Image("assets/images/edolite.png", WIDTH, HEIGHT, false, false);
	public final static int MIN_INITIAL_STRENGTH = 100;
	public final static int MAX_INITIAL_STRENGTH = 150;
	public final static int MOVEMENT_SPEED = 3;
	public final static int GUN_ELEVATION = 30;
	public final static int MULTIPLE_BULLETS_GAP = 16;

	private int strength;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<StatusEffect> statusEffects = new ArrayList<StatusEffect>();

	Edolite(int xPos, int yPos) {
		super(xPos, yPos, IMAGE);

		setMovementSpeed(MOVEMENT_SPEED);

		Random randomizer = new Random();
		strength = MIN_INITIAL_STRENGTH + randomizer.nextInt(MAX_INITIAL_STRENGTH - MIN_INITIAL_STRENGTH + 1);
	}

	/**
	 * Creates a bullet and adds it to the edolite's arraylist of bullets. It spawns at the right
	 * of the edolite and moves to the right. Its damage is based on the edolite's strength.
	 *
	 * If edolite has a status effect of warrior's fury, the spawned bullets will be of count 3 and
	 * are spawned side by side.
	 */
	void shoot() {
		faceRight();

		int bulletXPos = this.xPos + this.width;
		int bulletYPos = this.yPos + this.height - GUN_ELEVATION - (Bullet.HEIGHT / 2);
		Bullet bullet = new Bullet(bulletXPos, bulletYPos, strength);
		bullet.moveRight();

		bullets.add(bullet);

		if (hasStatusEffect(StatusEffect.WARRIORS_FURY)) {
			int upperBulletYPos = this.yPos + this.height - GUN_ELEVATION - Bullet.HEIGHT - MULTIPLE_BULLETS_GAP;
			Bullet upperBullet = new Bullet(bulletXPos, upperBulletYPos, strength);
			upperBullet.moveRight();

			int lowerBulletYPos = this.yPos + this.height - GUN_ELEVATION + MULTIPLE_BULLETS_GAP;
			Bullet lowerBullet = new Bullet(bulletXPos, lowerBulletYPos, strength);
			lowerBullet.moveRight();

			bullets.add(upperBullet);
			bullets.add(lowerBullet);
		}
    }

	/**
	 * Increases edolite's strength by a specified amount.
	 *
	 * @param amount The amount of strength to increase.
	 */
	void gainStrength(int amount){
		strength += amount;
	}


	/**
	 * Reduces edolite's strength by a specified amount.
	 *
	 * @param amount The amount of strength to reduce.
	 */
	void reduceStrengthBy(int damage) {
		strength -= damage;
	}

	/**
	 * Adds the given status effect to the edolite's list of status effects and starts the timer of
	 * the status effect.
	 *
	 * @param statusEffect The status effect to be applied.
	 */
	void obtainStatusEffect(StatusEffect statusEffect) {
		statusEffects.add(statusEffect);
		statusEffect.start();
	}

	/**
	 * Removes the specified status effect from the edolite's list of status effects.
	 *
	 * @param statusEffect The status effect to be removed.
	 */
	void removeStatusEffect(StatusEffect statusEffect) {
		statusEffects.remove(statusEffect);
	}

	/**
	 * Checks whether the edolite has a status effect of a specified type.
	 *
	 * @param type The type of status effect to be searched from the edolite.
	 * @return true if edolite has the specified status effect type, false otherwise.
	 */
	boolean hasStatusEffect(String type) {
		for (StatusEffect appliedStatusEffect : statusEffects) {
			if (appliedStatusEffect.isOfType(type)) {
				return true;
			}
		}
		return false;
	}

	int getStrength() {
		return strength;
	}

	ArrayList<Bullet> getBullets() {
		return bullets;
	}

	/**
	 * Checks whether the edolite is alive based on its remaining strength.
	 *
	 * @return true if edolite has strength greater than 0, false otherwise.
	 */
	boolean isAlive() {
		if (strength > 0) {
			return true;
		}
		return false;
	}
}

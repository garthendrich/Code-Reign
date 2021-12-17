package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

class Edolite extends MovableSprite {

	public final static int WIDTH = 32;
	public final static int HEIGHT = 44;
	public final static Image IMAGE = new Image("assets/images/edolite.png", WIDTH, HEIGHT, false, false);
	public final static int MIN_STRENGTH = 100;
	public final static int MAX_STRENGTH = 150;
	public final static int MOVEMENT_SPEED = 3;
	public final static int GUN_ELEVATION = 30;
	public final static int MULTIPLE_BULLETS_GAP = 16;

	private int strength;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<StatusEffect> statusEffects = new ArrayList<StatusEffect>();

	public Edolite(int xPos, int yPos) {
		super(xPos, yPos, IMAGE);

		Random randomizer = new Random();
		strength = MIN_STRENGTH + randomizer.nextInt(MAX_STRENGTH - MIN_STRENGTH + 1);
	}

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}

	//method called if spacebar is pressed
	void shoot(){
		int bulletXPos = this.xPos + this.width;
		int bulletYPos = this.yPos + this.height - GUN_ELEVATION - (Bullet.HEIGHT / 2);
		Bullet bullet = new Bullet(bulletXPos, bulletYPos, strength);
		bullet.moveRight(Bullet.MOVEMENT_SPEED);

		bullets.add(bullet);

		if (hasStatusEffect(StatusEffect.WARRIORS_FURY)) {
			int upperBulletYPos = this.yPos + this.height - GUN_ELEVATION - Bullet.HEIGHT - MULTIPLE_BULLETS_GAP;
			Bullet upperBullet = new Bullet(bulletXPos, upperBulletYPos, strength);

			int lowerBulletYPos = this.yPos + this.height - GUN_ELEVATION + MULTIPLE_BULLETS_GAP;
			Bullet lowerBullet = new Bullet(bulletXPos, lowerBulletYPos, strength);

			lowerBullet.moveRight(Bullet.MOVEMENT_SPEED);
			upperBullet.moveRight(Bullet.MOVEMENT_SPEED);

			bullets.add(upperBullet);
			bullets.add(lowerBullet);
		}
    }

	void receiveDamage(int damage) {
		if (!hasStatusEffect(StatusEffect.INVULNERABILITY)) {
			strength -= damage;
		}
	}

	boolean isAlive() {
		if (strength > 0){
			return true;
		}else{
			return false;
		}
	}

	public void gainStrength(int amount){
		strength += amount;
	}

	void obtainStatusEffect(StatusEffect statusEffect) {
		statusEffects.add(statusEffect);
		statusEffect.start();
	}

	void removeStatusEffect(StatusEffect statusEffect) {
		statusEffects.remove(statusEffect);
	}

	private boolean hasStatusEffect(String statusEffectType) {
		for (StatusEffect currentStatusEffect : statusEffects) {
			if (currentStatusEffect.isOfType(statusEffectType)) {
				return true;
			}
		}
		return false;
	}

	int getStrength() {
		return strength;
	}
}

package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

class Edolite extends Sprite {

	private final static int WIDTH = 32;
	private final static int HEIGHT = 44;
	public final static Image IMAGE = new Image("assets/images/edolite.png", WIDTH, HEIGHT, false, false);
	private final static int MIN_STRENGTH = 100;
	private final static int MAX_STRENGTH = 150;
	private final static int MOVEMENT_SPEED = 3;
	private final static int GUN_ELEVATION_ON_SHOOT = 30;

	private int strength;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private StatusEffect statusEffect;

	public Edolite(int x, int y) {
		super(x,y);

		setImage(IMAGE);

		Random randomizer = new Random();
		strength = MIN_STRENGTH + randomizer.nextInt(MAX_STRENGTH - MIN_STRENGTH + 1);

		setMovementSpeed(MOVEMENT_SPEED);
	}

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}

	//method called if spacebar is pressed
	public void shoot(){
		//compute for the x and y initial position of the bullet
		int bulletXPos = this.xPos + this.width;
		int bulletYPos = this.yPos + this.height - GUN_ELEVATION_ON_SHOOT - (Bullet.HEIGHT / 2);

		Bullet bullet = new Bullet(bulletXPos, bulletYPos, strength);
		bullets.add(bullet);
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
		this.statusEffect = statusEffect;
		statusEffect.start();
	}

	void removeStatusEffect() {
		statusEffect = null;
	}

	private boolean hasStatusEffect(String statusEffectType) {
		if (statusEffect == null) return false;

		String currentStatusEffectType = this.statusEffect.getType();
		if (currentStatusEffectType == statusEffectType) {
			return true;
		}
		return false;
	}

	public int getStrength(){
		return strength;
	}
}

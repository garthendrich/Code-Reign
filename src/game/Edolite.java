package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

public class Edolite extends Sprite {

	private final static int WIDTH = 25;
	private final static int HEIGHT = 25;
	public final static Image IMAGE = new Image("images/ship.png", WIDTH, HEIGHT, false, false);

	private final static int MIN_STRENGTH = 100;
	private final static int MAX_STRENGTH = 150;
	private final static int MOVEMENT_SPEED = 3;

	private int strength;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

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
		int bulletXPos = this.xPos + (this.width + 16);
		int bulletYPos = this.yPos + (this.height / 2) - (Bullet.WIDTH / 2);

		Bullet bullet = new Bullet(bulletXPos, bulletYPos, strength);
		bullets.add(bullet);
    }

	void reduceStrengthBy(int damage){
		strength -= damage;
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

	void obtainEffect(){

	}

	void removeEffect(){

	}
}

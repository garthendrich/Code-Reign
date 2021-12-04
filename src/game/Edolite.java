package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

public class Edolite extends Sprite{

	public final static Image EDOLITE_IMAGE = new Image("images/ship.png", 25, 25, false, false);
	private final static int MIN_STRENGTH = 100;
	private final static int MAX_STRENGTH = 150;
	private final static int MOVEMENT_SPEED = 3;

	private int strength;
	private ArrayList<Bullet> bullets;

	public Edolite(int x, int y) {
		super(x,y, EDOLITE_IMAGE);

		setMovementSpeed(MOVEMENT_SPEED);
	}

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}

	//method called if spacebar is pressed
	public void shoot(){
		//compute for the x and y initial position of the bullet
		int bulletXPos =+ this.xPos + this.width + 16;
		int bulletYPos =+ this.yPos + (this.height / 2);

		Bullet bullet = new Bullet(bulletXPos, bulletYPos, strength);
		bullets.add(bullet);
    }

	void reduceStrengthBy(int damage){
		strength -= damage;
	}

	boolean isAlive() {
		if (strength >= 0) return true;
		return false;
	}

	public void gainStrength(int amount){
		strength += amount;
	}

	void obtainEffect(){

	}

	void removeEffect(){

	}
}

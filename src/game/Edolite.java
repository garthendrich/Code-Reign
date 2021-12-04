package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

public class Edolite extends Sprite{

	public final static Image EDOLITE_IMAGE = new Image("images/ship.png", 25, 25, false, false);
	private final static int MIN_STRENGTH = 100;
	private final static int MAX_STRENGTH = 150;

	private int strength;
	private ArrayList<Bullet> bullets;

	public Edolite(int x, int y) {
		super(x,y, EDOLITE_IMAGE);
		Random r = new Random();
		this.strength = r.nextInt(MAX_STRENGTH - MIN_STRENGTH) + 101;
		this.bullets = new ArrayList<Bullet>();
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

	void obtainEffect(){

	}

	void removeEffect(){

	}
}

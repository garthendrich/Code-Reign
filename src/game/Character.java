package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

public class Character extends Sprite{
	private int strength;
	private ArrayList<Bullet> bullets;
	public final static Image CHARACTER_IMAGE = new Image("images/ship.png", 25, 25, false, false);
	private final static Image BULLET_IMAGE = new Image("images/bullet.png", 25, 25, false, false);
	private final static int MIN_STRENGTH = 100;
	private final static int MAX_STRENGTH = 150;

	public Character(String name, int x, int y, Image image){
		super(x,y, image);
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
		int x =+ width + 20;
		int y =+ height / 2;
		/*
		 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
		 */
		bullets.add(new Bullet(x + (width / 2), y, BULLET_IMAGE));
    }

	void reduceStrength(int amount){

	}

	void obtainEffect(){

	}

	void removeEffect(){

	}
}

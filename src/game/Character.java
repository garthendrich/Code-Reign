package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

public class Character extends GameElement{
	private String name;
	private int strength;
	private boolean alive;

	private ArrayList<Bullet> bullets;
	public final static Image CHARACTER_IMAGE = new Image("images/ship.png", Character.CHARACTER_WIDTH, Character.CHARACTER_WIDTH, false, false);
	private final static int CHARACTER_WIDTH = 50;

	public Character(String name, int x, int y){
		super(x,y);
		Random r = new Random();
		strength = r.nextInt(151)+100;
		alive = true;
		bullets = new ArrayList<Bullet>();
		loadImage(Character.CHARACTER_IMAGE);
	}

	public boolean isAlive(){
		if(alive) return true;
		return false;
	}
	public String getName(){
		return name;
	}

	public void die(){
    	alive = false;
    }

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}

	//method called if spacebar is pressed
	public void shoot(){
		//compute for the x and y initial position of the bullet
		int x =+ (int) ( width + 20);
		int y =+ (int) ( height / 2);
		/*
		 * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
		 */
		this.bullets.add(new Bullet((int)(x + (width / 2)), y));
    }

	//method called if up/down/left/right arrow key is pressed.
	public void move() {
		/*
		 *TODO: 		Only change the x and y position of the ship if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */
		if(x + dx >= 0 && x + dx <= GameStage.WINDOW_WIDTH - width){
			x += dx;
		}
		if(y + dy >= 0 && y + dy <= GameStage.WINDOW_HEIGHT - height){
			y += dy;
		}
	}

	public int getStrength() {
		return strength;
	}

	public void addStrength(){
		strength += 50;
	}

}

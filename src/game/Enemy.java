package game;

import javafx.scene.image.Image;
import java.util.Random;

public class Enemy extends Sprite{
	public int movementSpeed;
	public static final int MAX_ENEMY_SPEED = 5;
	public final static Image ENEMY_IMAGE = new Image("images/fish.png", Enemy.ENEMY_WIDTH, Enemy.ENEMY_WIDTH, false, false);
	public final static int ENEMY_WIDTH = 50;
	public final static int ENEMY_HEIGHT = 50;
	public final static int ENEMY_DAMAGE = 10;

	public Enemy(int x, int y, Image image){
		super(x,y, image);
		Random r = new Random();
		this.movementSpeed = r.nextInt(MAX_ENEMY_SPEED) + 1;
	}

	public int getDamage(){
		return ENEMY_DAMAGE;
	}
}

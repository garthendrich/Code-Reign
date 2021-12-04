package game;

import javafx.scene.image.Image;
import java.util.Random;

public class Orglit extends Sprite{
	public int movementSpeed;
	public static final int ORGLIT = 5;
	public final static Image ENEMY_IMAGE = new Image("images/fish.png", Orglit.WIDTH, Orglit.HEIGHT, false, false);
	public final static int WIDTH = 50;
	public final static int HEIGHT = 50;
	public final static int DAMAGE = 10;

	public Orglit(int x, int y, Image image){
		super(x,y, image);
		Random r = new Random();
		this.movementSpeed = r.nextInt(MAX_ORGLIT_SPEED) + 1;
	}

	public int getDamage(){
		return DAMAGE;
	}
}
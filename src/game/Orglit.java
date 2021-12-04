package game;

import javafx.scene.image.Image;
import java.util.Random;

public class Orglit extends Sprite{
	public static final int MAX_ORGLIT_SPEED = 5;
	public static final int DEFAULT_DAMAGE = 30;
	public final static Image ORGLIT_IMAGE = new Image("images/fish.png", Orglit.WIDTH, Orglit.HEIGHT, false, false);
	public final static int WIDTH = 50;
	public final static int HEIGHT = 50;

	public int movementSpeed;
	protected int damage = DEFAULT_DAMAGE;

	public Orglit(int x, int y) {
		super(x,y, ORGLIT_IMAGE);
		Random r = new Random();
		this.movementSpeed = r.nextInt(MAX_ORGLIT_SPEED) + 1;
	}

	public int getDamage() {
		return damage;
	}
}

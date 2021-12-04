package game;

import javafx.scene.image.Image;

public class Bullet extends Sprite{

	public final static Image BULLET_IMAGE = new Image("images/bullet.png", 25, 25, false, false);

	private int damage;

	public Bullet(int x, int y, int damage){
		super(x,y, BULLET_IMAGE);

		this.damage = damage;
	}

	int getDamage() {
		return damage;
	}
}
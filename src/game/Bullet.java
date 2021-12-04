package game;

import javafx.scene.image.Image;

public class Bullet extends Sprite{
	public final static int BULLET_DAMAGE = 10;
	public final static Image BULLET_IMAGE = new Image("images/bullet.png", 25, 25, false, false);

	public Bullet(int x, int y, Image image){
		super(x,y, image);
		this.image = BULLET_IMAGE;
	}

}
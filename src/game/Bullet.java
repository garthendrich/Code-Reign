package game;

import javafx.scene.image.Image;

public class Bullet extends GameElement {
	private final static int BULLET_DAMAGE = 10;

	public Bullet(int x, int y, Image image){
		super(x,y, image);
	}


}
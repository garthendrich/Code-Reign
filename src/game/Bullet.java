package game;

import javafx.scene.image.Image;

public class Bullet extends GameElement {
	private final static int BULLET_DAMAGE = 10;
	private final static Image BULLET_IMAGE = new Image("images/bullet.png",Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);
	private final static int BULLET_WIDTH = 20;

	public Bullet(int x, int y, Image image){
		super(x,y, image);
	}


}
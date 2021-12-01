package game;

import javafx.scene.image.Image;

public class Bullet extends Sprite {
	private final static int BULLET_SPEED = 20;
	private final static Image BULLET_IMAGE = new Image("images/bullet.png",Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);
	private final static int BULLET_WIDTH = 20;

	public Bullet(int x, int y){
		super(x,y);
		loadImage(Bullet.BULLET_IMAGE);
	}


	//method that will move/change the x position of the bullet
	public void move(){
		/*
		 * TODO: Change the x position of the bullet depending on the bullet speed.
		 * 					If the x position has reached the right boundary of the screen,
		 * 						set the bullet's visibility to false.
		 */
		x -= Bullet.BULLET_SPEED;
		if(x <= 0){
			isVisible();
		}
	}
}
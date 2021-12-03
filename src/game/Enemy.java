package game;

import javafx.scene.image.Image;
import java.util.Random;

public class Enemy extends GameElement {
	public static final int MAX_ENEMY_SPEED = 5;
	public final static Image ENEMY_IMAGE = new Image("images/fish.png", Enemy.ENEMY_WIDTH, Enemy.ENEMY_WIDTH, false, false);
	public final static int ENEMY_WIDTH = 50;
	private boolean alive;
	//attribute that will determine if a fish will initially move to the right
	private boolean moveRight;
	private int speed;


	Enemy(int x, int y, Image img){
		super(x,y, img);
		alive = true;
		loadImage(Enemy.ENEMY_IMAGE);
		/*
		 *TODO: Randomize speed of fish and moveRight's initial value
		 */
		Random r = new Random();
		speed = r.nextInt(MAX_ENEMY_SPEED);
		moveRight = r.nextBoolean();
	}

	//method that changes the x position of the fish
	void move(){
		/*
		 * TODO: 				If moveRight is true and if the fish hasn't reached the right boundary yet,
		 *    						move the fish to the right by changing the x position of the fish depending on its speed
		 *    					else if it has reached the boundary, change the moveRight value / move to the left
		 * 					 Else, if moveRight is false and if the fish hasn't reached the left boundary yet,
		 * 	 						move the fish to the left by changing the x position of the fish depending on its speed.
		 * 						else if it has reached the boundary, change the moveRight value / move to the right
		 */
		if(x < GameStage.WINDOW_WIDTH && moveRight == false){
			x -= speed;
		}else if(x == 0){
			moveRight = true;
			x += speed;
		}else if(x < GameStage.WINDOW_WIDTH && moveRight == true){
			x += speed;
		}else if(x == GameStage.WINDOW_WIDTH){
			moveRight = false;
			x -= speed;
		}
	}

	//getter
	public boolean isAlive() {
		return alive;
	}
}

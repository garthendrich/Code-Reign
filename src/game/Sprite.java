package game;

import javafx.scene.image.Image;

public class Sprite extends GameElement{
	protected final static int SPRITE_MOVING_DISTANCE = 10;
	protected int dX, dY;

	public Sprite(int xPos, int yPos, Image image) {
		super(xPos, yPos, image);
	}

	void moveUp(){

	}

	void moveDown(){

	}

	void moveLeft(){

	}

	void moveRight(){

	}

	void stopMovingHorizontally(){

	}

	void stopMovingVertically(){

	}

	void setDX(int distance){
		this.dX = distance;
	}

	void setDY(int distance){
		this.dY = distance;
	}

	void updatePosition(){

	}
}

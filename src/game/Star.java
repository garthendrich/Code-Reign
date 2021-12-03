package game;

import javafx.scene.image.Image;

class Star extends PowerUp{
	private final static Image STAR_IMAGE = new Image();

	Star(int xPos, int yPos){
		super(xPos, yPos);
		loadImage(Star.STAR_IMAGE);
	}

	@Override
	void checkCollision(Character character) {

	}
}

package game;

import javafx.scene.image.Image;

class Pearl extends PowerUp{
	private final static Image PEARL_IMAGE = new Image();

	public Pearl(int xPos, int yPos) {
		super(xPos, yPos, Pearl.PEARL_IMAGE);
	}
}

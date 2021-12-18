package game;

import javafx.scene.image.Image;

abstract class PowerUp extends Sprite {

	public final static int SIZE = 24;

	PowerUp(int xPos, int yPos, Image image) {
		super(xPos, yPos, image);
	}

	abstract void applyTo(Edolite edolite);
}

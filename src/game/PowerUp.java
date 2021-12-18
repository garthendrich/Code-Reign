package game;

import javafx.scene.image.Image;

abstract class PowerUp extends Sprite {

	public final static int SIZE = 24;

	PowerUp(int xPos, int yPos, Image image) {
		super(xPos, yPos, image);
	}

	/**
	 * Applies a particular buff to the given edolite.
	 *
	 * @param edolite The edolite that will obtain the buff.
	 */
	abstract void applyTo(Edolite edolite);
}

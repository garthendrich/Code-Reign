package game;

import javafx.scene.image.Image;

abstract class PowerUp extends GameElement {

	public final static int SIZE = 25;

	public PowerUp(int x, int y) {
		super(x, y);
	}

	abstract void applyTo(Edolite edolite);
}

package game;

import javafx.scene.image.Image;

abstract class PowerUp extends GameElement{

	public PowerUp(int x, int y, Image image) {
		super(x, y, image);
	}

	abstract void applyTo(Edolite edolite);
}

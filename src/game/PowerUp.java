package game;

import javafx.scene.image.Image;

abstract class PowerUp extends GameElement{

	public PowerUp(int xPos, int yPos, Image img) {
		super(xPos, yPos, img);
	}

	abstract void checkCollision(Character character);
}

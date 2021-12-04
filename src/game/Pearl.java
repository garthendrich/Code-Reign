package game;

import javafx.scene.image.Image;

class Pearl extends PowerUp{
	private final static Image PEARL_IMAGE = new Image();

	Pearl(int xPos, int yPos) {
		super(xPos, yPos);
		loadImage(Pearl.PEARL_IMAGE);
	}

	@Override
	void checkCollision(Edolite edolite) {
		if(collidesWith(edolite)){
			setVisible(false);
			edolite.addStrength();
		}
	}
}

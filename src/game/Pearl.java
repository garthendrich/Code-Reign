package game;

import javafx.scene.image.Image;

class Pearl extends PowerUp{
	private final static Image PEARL_IMAGE = new Image("images/biscuit.png");

	Pearl(int x, int y){
		super(x, y, PEARL_IMAGE);
	}

	@Override
	void applyTo(Edolite edolite) {
		// TODO Auto-generated method stub

	}
}

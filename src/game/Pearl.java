package game;

import javafx.scene.image.Image;

class Pearl extends PowerUp{
	private final static Image PEARL_IMAGE = new Image("images/biscuit.png");

	Pearl(int x, int y, Image image){
		super(x, y, image);
		this.image = PEARL_IMAGE;
	}

	@Override
	void applyTo(Character character) {
		// TODO Auto-generated method stub

	}
}

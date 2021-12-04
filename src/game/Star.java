package game;

import javafx.scene.image.Image;

class Star extends PowerUp{
	public final static Image STAR_IMAGE = new Image("images/biscuit.png", PowerUp.SIZE, PowerUp.SIZE, false, false);

	Star(int x, int y){
		super(x, y, STAR_IMAGE);
	}

	@Override
	void applyTo(Edolite edolite) {
		// TODO Auto-generated method stub

	}
}

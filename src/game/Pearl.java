package game;

import javafx.scene.image.Image;

class Pearl extends PowerUp{
	public final static Image PEARL_IMAGE = new Image("images/biscuit.png", PowerUp.SIZE, PowerUp.SIZE, false, false);
	public final static int PEARL_STRENGTH = 50;

	Pearl(int x, int y){
		super(x, y, PEARL_IMAGE);
	}

	@Override
	void applyTo(Edolite edolite) {
		edolite.gainStrength(PEARL_STRENGTH);
	}
}

package game;

import javafx.scene.image.Image;

	public final static int PEARL_STRENGTH = 50;
class Pearl extends PowerUp {

	public final static Image IMAGE = new Image("images/biscuit.png", PowerUp.SIZE, PowerUp.SIZE, false, false);

	Pearl(int x, int y){
		super(x, y);

		setImage(IMAGE);
	}

	@Override
	void applyTo(Edolite edolite) {
		edolite.gainStrength(PEARL_STRENGTH);
	}
}

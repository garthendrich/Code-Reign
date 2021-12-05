package game;

import javafx.scene.image.Image;

class Pearl extends PowerUp {

	public final static Image IMAGE = new Image("images/biscuit.png", PowerUp.SIZE, PowerUp.SIZE, false, false);
	public final static int STRENGTH_BONUS = 50;

	Pearl(int x, int y){
		super(x, y);

		setImage(IMAGE);
	}

	@Override
	void applyTo(Edolite edolite) {
		edolite.gainStrength(STRENGTH_BONUS);
	}
}

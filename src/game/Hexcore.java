package game;

import javafx.scene.image.Image;

class Hexcore extends PowerUp {

	public final static Image IMAGE = new Image("assets/images/hexcore.png", PowerUp.SIZE, PowerUp.SIZE, false, false);
	public final static int STRENGTH_BONUS = 50;

	Hexcore(int xPos, int yPos){
		super(xPos, yPos, IMAGE);
	}

	@Override
	void applyTo(Edolite edolite) {
		edolite.gainStrength(STRENGTH_BONUS);
	}
}

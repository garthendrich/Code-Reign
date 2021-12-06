package game;

import javafx.scene.image.Image;

class Gemstone extends PowerUp {

	public final static Image IMAGE = new Image("assets/images/gemstone.png", PowerUp.SIZE, PowerUp.SIZE, false, false);

	Gemstone(int x, int y){
		super(x, y);

		setImage(IMAGE);
	}

	@Override
	void applyTo(Edolite edolite) {
		StatusEffect invulnerability = new StatusEffect(StatusEffect.INVULNERABILITY, 3, edolite);
		edolite.obtainStatusEffect(invulnerability);
	}
}
package game;

import javafx.scene.image.Image;

public class Gemstone extends PowerUp {

	public final static Image IMAGE = new Image("assets/images/gemstone.png", PowerUp.SIZE, PowerUp.SIZE, false, false);

	Gemstone(int xPos, int yPos){
		super(xPos, yPos, IMAGE);
	}

	@Override
	void applyTo(Edolite edolite) {
		StatusEffect warriorsFury = new StatusEffect(StatusEffect.WARRIORS_FURY, 5, edolite);
		edolite.obtainStatusEffect(warriorsFury);
	}

}
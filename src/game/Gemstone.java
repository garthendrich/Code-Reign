package game;

import javafx.scene.image.Image;

public class Gemstone extends PowerUp {

	public final static Image IMAGE = new Image("assets/images/gemstone.png", PowerUp.SIZE, PowerUp.SIZE, false, false);
	public final static int STATUS_EFFECT_DURATION = 5;

	Gemstone(int xPos, int yPos){
		super(xPos, yPos, IMAGE);
	}

	/**
	 * Creates a status effect of warrior's fury (which increases the edolites bullet per shot)
	 * that will last for a short duration and applies it to the given edolite.
	 *
	 * @param edolite The edolite that will be having warrior's fury.
	 */
	@Override
	void applyTo(Edolite edolite) {
		StatusEffect warriorsFury = new StatusEffect(StatusEffect.WARRIORS_FURY, STATUS_EFFECT_DURATION, edolite);
		edolite.obtainStatusEffect(warriorsFury);
	}
}
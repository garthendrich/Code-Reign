package game;

import javafx.scene.image.Image;

public class Hexcore extends PowerUp {

	public final static Image IMAGE = new Image("assets/images/hexcore.png", PowerUp.SIZE, PowerUp.SIZE, false, false);
	public final static int STRENGTH_BONUS = 50;

	Hexcore(int xPos, int yPos) {
		super(xPos, yPos, IMAGE);
	}

	/**
	 * Increases the given edolite's strength by a certain amount.
	 *
	 * @param edolite The edolite that will gain strength.
	 */
	@Override
	void applyTo(Edolite edolite) {
		edolite.gainStrength(STRENGTH_BONUS);
	}
}
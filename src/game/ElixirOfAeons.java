package game;

import javafx.scene.image.Image;

public class ElixirOfAeons extends PowerUp {

	public final static Image IMAGE = new Image("assets/images/elixir_of_aeons.png", PowerUp.SIZE, PowerUp.SIZE, false, false);
	public final static int STATUS_EFFECT_DURATION = 3;

	ElixirOfAeons(int xPos, int yPos) {
		super(xPos, yPos, IMAGE);
	}

	/**
	 * Creates a status effect of invulnerability that will last for a short duration and applies
	 * it to the given edolite.
	 *
	 * @param edolite The edolite that will be having invulnerability.
	 */
	@Override
	void applyTo(Edolite edolite) {
		StatusEffect invulnerability = new StatusEffect(StatusEffect.INVULNERABILITY, STATUS_EFFECT_DURATION, edolite);
		edolite.obtainStatusEffect(invulnerability);
	}
}

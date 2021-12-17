package game;

import javafx.scene.image.Image;

class ElixirOfAeons extends PowerUp {

	public final static Image IMAGE = new Image("assets/images/elixir_of_aeons.png", PowerUp.SIZE, PowerUp.SIZE, false, false);

	ElixirOfAeons(int xPos, int yPos){
		super(xPos, yPos, IMAGE);
	}

	@Override
	void applyTo(Edolite edolite) {
		StatusEffect invulnerability = new StatusEffect(StatusEffect.INVULNERABILITY, 3, edolite);
		edolite.obtainStatusEffect(invulnerability);
	}
}

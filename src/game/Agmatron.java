package game;

import javafx.scene.image.Image;

public class Agmatron extends Orglit {

	public final static int AGMATRON_DAMAGE = 50;

	private int health;

	public Agmatron(int x, int y) {
		super(x, y);

		damage = AGMATRON_DAMAGE;
	}

	void reduceHealthBy(int amount) {
		health -= amount;
		if (health < 0) die();
	}
}

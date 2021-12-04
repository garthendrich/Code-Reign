package game;

import javafx.scene.image.Image;

public class Agmatron extends Orglit {

	public final static int AGMATRON_DAMAGE = 50;
	private final static int AGMATRON_HEALTH = 3000;
	protected int health = AGMATRON_HEALTH;

	public Agmatron(int x, int y) {
		super(x, y);

		damage = AGMATRON_DAMAGE;
	}

	void reduceHealthBy(int amount) {
		health -= amount;
		if (health <= 0){
			die();
		}
	}
}

package game;

import javafx.scene.image.Image;

class Agmatron extends Orglit {

	public final static int WIDTH = 88;
	public final static int HEIGHT = 96;
	public final static Image IMAGE = new Image("assets/images/agmatron.png", WIDTH, HEIGHT, false, false);
	public final static int DAMAGE = 50;

	private int health = 3000;

	public Agmatron(int xPos, int yPos) {
		super(xPos, yPos, IMAGE);

		damage = DAMAGE;
	}

	void reduceHealthBy(int amount) {
		health -= amount;

		if (health <= 0) {
			die();
		}
	}
}

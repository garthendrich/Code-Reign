package game;

import javafx.scene.image.Image;

public class Agmatron extends Orglit {
	private final static Image AGMATRON_IMAGE = new Image("images/poro.png");
	private int health;

	public Agmatron(int x, int y) {
		super(x, y, AGMATRON_IMAGE);
	}

	void reduceHealthBy(int amount) {
		health -= amount;
	}

	boolean isAlive() {
		if (health >= 0){
			return true;
		}else{
			return false;
		}
	}
}

package game;

import javafx.scene.image.Image;

public class Agmatron extends Orglit {

	private int health;

	public Agmatron(int x, int y, Image image) {
		super(x, y, image);
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

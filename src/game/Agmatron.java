package game;

import javafx.scene.image.Image;

public class Agmatron extends Orglit {

	public final static int AGMATRON_DAMAGE = 50;

	private int health;

	public Agmatron(int x, int y) {
		super(x, y);
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

	public static int getAgmatronDamage(){
		return AGMATRON_DAMAGE;

	}
}

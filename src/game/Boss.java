package game;

import javafx.scene.image.Image;

public class Boss extends Enemy{
	private final static int BOSS_HEALTH = 30;

	public Boss(int x, int y, Image image) {
		super(x, y, image);

	}

	int reduceHealth(int damage){
		return BOSS_HEALTH - Bullet.BULLET_DAMAGE;
	}

	boolean isAlive(){
		return true;
	}
}

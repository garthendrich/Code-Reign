package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameTimer extends AnimationTimer{

	public static final int SPRITE_MOVING_DISTANCE = 10;
	public static final int ENEMY_SPAWN_COUNT = 3;

	private GraphicsContext graphicsContext;
	private Character character = new Character("Going merry", 150, 250);
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	GameTimer(GraphicsContext graphicsContext){
		this.graphicsContext = graphicsContext;
	}

	@Override
	public void handle(long currentTimeInNanoseconds) {
		updateSpritePositions();
		manageSpriteCollisions();
	}

	private void updateSpritePositions() {
		ArrayList<Bullet> characterBullets = character.getBullets();

		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(character);
		sprites.addAll(characterBullets);
		sprites.addAll(enemies);

		for (Sprite sprite : sprites) {
			sprite.updatePosition();
		}
	}

	private void manageSpriteCollisions() {
		for (Enemy enemy: enemies) {
			manageCollisionOf(character, enemy);

			ArrayList<Bullet> characterBullets = character.getBullets();
			for (Bullet characterBullet : characterBullets) manageCollisionOf(enemy, characterBullet);
		}
	}

	private void manageCollisionOf(Character character, Enemy enemy) {
		if (character.collidesWith(enemy)) {
			int enemyDamage = enemy.getDamage();
			character.reduceStrengthBy(enemyDamage);

			if (enemy instanceof Boss == false) enemies.remove(enemy);
		}
	}

	private void manageCollisionOf(Enemy enemy, Bullet characterBullet) {
		if (characterBullet.collidesWith(enemy)) {
			if (enemy instanceof Boss) {
				Boss enemyBoss = (Boss) enemy;

				int characterBulletDamage = characterBullet.getDamage();
				enemyBoss.reduceHealthBy(characterBulletDamage);

				if (enemyBoss.isAlive() == false)  enemies.remove(enemyBoss);

			} else {
				enemies.remove(enemy);
			}
		}
	}
}

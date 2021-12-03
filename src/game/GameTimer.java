package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
		reRenderSprites();
	}

	private void spawnEnemies(int spawnCount) {
		for (int spawned = 0; spawned < spawnCount; spawned++) {
			Enemy enemy = createEnemyAtRandomUnoccupiedPosition();
			enemies.add(enemy);
		}
	}

	private Enemy createEnemyAtRandomUnoccupiedPosition() {
		Enemy enemy = null;

		for (int generatePositionAttempts = 0; generatePositionAttempts < 10; generatePositionAttempts++) {
			enemy = createEnemyAtRandomPosition();

			if (isEnemyCollidingAnotherSprite(enemy) == false) break;
		}

		return enemy;
	}

	private Enemy createEnemyAtRandomPosition() {
		int lowestXPos = GameArea.LOWER_X_BOUND;
		int highestXPos = GameArea.UPPER_X_BOUND - Enemy.WIDTH;
		int randomXPos = generateRandomNumber(lowestXPos, highestXPos);

		int lowestYPos = GameArea.LOWER_Y_BOUND;
		int highestYPos = GameArea.UPPER_Y_BOUND - Enemy.HEIGHT;
		int randomYPos = generateRandomNumber(lowestYPos, highestYPos);

		return new Enemy(randomXPos, randomYPos);
	}

	private boolean isEnemyCollidingAnotherSprite(Enemy enemy) {
		ArrayList<Sprite> sprites = getAllSprites();
		for (Sprite sprite : sprites) if (enemy.collidesWith(sprite)) return true;
		return false;
	}

	private void updateSpritePositions() {
		ArrayList<Sprite> sprites = getAllSprites();
		for (Sprite sprite : sprites) sprite.updatePosition();
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

				if (enemyBoss.isAlive() == false) enemies.remove(enemyBoss);

			} else {
				enemies.remove(enemy);
			}
		}
	}

	private void reRenderSprites() {
		clearCanvas();

		ArrayList<Sprite> sprites = getAllSprites();
		for (Sprite sprite : sprites) render(sprite);
	}

	private void clearCanvas() {
		Canvas canvas = graphicsContext.getCanvas();
		double canvasWidth = canvas.getWidth();
		double canvasHeight = canvas.getHeight();
		graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
	}

	private void render(GameElement gameElement) {
		Image gameElementImage = gameElement.getImage();
		int gameElementXPos = gameElement.getXPos();
		int gameElementYPos = gameElement.getYPos();
		graphicsContext.drawImage(gameElementImage, gameElementXPos, gameElementYPos);
	}

	private ArrayList<Sprite> getAllSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(character);
		ArrayList<Bullet> characterBullets = character.getBullets();
		sprites.addAll(characterBullets);
		sprites.addAll(enemies);
		return sprites;
	}

	private int generateRandomNumber(int min, int max) {
		Random randomizer = new Random();
		return min + randomizer.nextInt(max - min + 1);
	}
}

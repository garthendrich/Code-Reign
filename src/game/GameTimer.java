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

	public static final int ENEMY_INITIAL_SPAWN_COUNT = 7;
	public static final int ENEMY_SPAWN_COUNT = 3;
	public static final int ENEMY_SPAWN_INTERVAL_SECONDS = 5;

	public static final int POWER_UP_SPAWN_INTERVAL_SECONDS = 10;
	public static final int POWER_UP_OCCURENCE_SECONDS = 5;

	private GraphicsContext graphicsContext;
	private Edolite edolite = new Edolite("Burcham", 150, 250);
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private PowerUp powerUp;
	private long gameStartTimeInNanos = -1;
	private double gameTimeDuringPreviousEnemySpawn = 0;
	private double gameTimeDuringPreviousPowerUpSpawn = 0;
	private ArrayList<KeyCode> keysPressed = new ArrayList<KeyCode>();

	GameTimer(GraphicsContext graphicsContext){
		this.graphicsContext = graphicsContext;

		spawnEnemies(ENEMY_INITIAL_SPAWN_COUNT);
	}

	@Override
	public void handle(long currentTimeInNanos) {
		double gameTime = computeGameTime(currentTimeInNanos);

		double elapsedSecondsSincePreviousEnemySpawn = gameTime - gameTimeDuringPreviousEnemySpawn;
		if (elapsedSecondsSincePreviousEnemySpawn > ENEMY_SPAWN_INTERVAL_SECONDS) {
			spawnEnemies(ENEMY_SPAWN_COUNT);
			gameTimeDuringPreviousEnemySpawn = gameTime;
		}

		double elapsedSecondsSincePreviousPowerUpSpawn = gameTime - gameTimeDuringPreviousPowerUpSpawn;
		if (elapsedSecondsSincePreviousPowerUpSpawn > POWER_UP_SPAWN_INTERVAL_SECONDS) {
			spawnPowerUp();
			gameTimeDuringPreviousPowerUpSpawn = gameTime;
		}
		if (elapsedSecondsSincePreviousPowerUpSpawn > POWER_UP_OCCURENCE_SECONDS) deSpawnPowerUp();

		updateSpritePositions();
		manageGameElementCollisions();
		reRenderGameElements();
	}

	private double computeGameTime(long currentTimeInNanos) {
		if (gameStartTimeInNanos == -1) gameStartTimeInNanos = currentTimeInNanos;
		return (currentTimeInNanos - gameStartTimeInNanos) / 1_000_000_000.0;
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

			if (isEnemyCollidingAnotherGameElement(enemy) == false) break;
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

	private boolean isEnemyCollidingAnotherGameElement(Enemy enemy) {
		ArrayList<GameElement> gameElements = getAllGameElements();
		for (GameElement gameElement : gameElements) if (enemy.collidesWith(gameElement)) return true;
		return false;
	}

	private void spawnPowerUp() {
		powerUp = createRandomPowerUp();
	}

	private void deSpawnPowerUp() {
		powerUp = null;
	}

	private PowerUp createRandomPowerUp() {
		Random randomizer = new Random();
		switch(randomizer.nextInt(2)) {
			case 0: return new Pearl();
			case 1: return new Star();
		}
	}

	private void updateSpritePositions() {
		ArrayList<Sprite> sprites = getAllSprites();
		for (Sprite sprite : sprites) sprite.updatePosition();
	}

	private void manageGameElementCollisions() {
		ArrayList<Bullet> edoliteBullets = edolite.getBullets();

		manageCollisionOf(edolite, powerUp);

		for (Enemy enemy: enemies) {
			manageCollisionOf(edolite, enemy);

			for (Bullet edoliteBullet : edoliteBullets) manageCollisionOf(enemy, edoliteBullet);
		}
	}

	private void manageCollisionOf(Edolite edolite, PowerUp powerUp) {
		if (edolite.collidesWith(powerUp)) {
			powerUp.applyTo(edolite);

			deSpawnPowerUp();
		}
	}

	private void manageCollisionOf(Edolite edolite, Enemy enemy) {
		if (edolite.collidesWith(enemy)) {
			int enemyDamage = enemy.getDamage();
			edolite.reduceStrengthBy(enemyDamage);

			if (enemy instanceof Boss == false) enemies.remove(enemy);
		}
	}

	private void manageCollisionOf(Enemy enemy, Bullet edoliteBullet) {
		if (edoliteBullet.collidesWith(enemy)) {
			if (enemy instanceof Boss) {
				Boss enemyBoss = (Boss) enemy;

				int edoliteBulletDamage = edoliteBullet.getDamage();
				enemyBoss.reduceHealthBy(edoliteBulletDamage);

				if (enemyBoss.isAlive() == false) enemies.remove(enemyBoss);

			} else {
				enemies.remove(enemy);
			}
		}
	}

	private void reRenderGameElements() {
		clearGameCanvas();

		ArrayList<GameElement> gameElements = getAllGameElements();
		for (GameElement gameElement : gameElements) render(gameElement);
	}

	private void clearGameCanvas() {
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

	private ArrayList<GameElement> getAllGameElements() {
		ArrayList<GameElement> gameElements = new ArrayList<GameElement>();
		ArrayList<Sprite> sprites = getAllSprites();
		gameElements.addAll(sprites);
		gameElements.add(powerUp);
		return gameElements;
	}

	private ArrayList<Sprite> getAllSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(edolite);
		ArrayList<Bullet> edoliteBullets = edolite.getBullets();
		sprites.addAll(edoliteBullets);
		sprites.addAll(enemies);
		return sprites;
	}

	private int generateRandomNumber(int min, int max) {
		Random randomizer = new Random();
		return min + randomizer.nextInt(max - min + 1);
	}

	public void handleKeyPress(KeyCode key) {
		if (keysPressed.contains(key)) return;

		keysPressed.add(key);
		updateEdoliteMovement();
	}

	public void handleKeyRelease(KeyCode key) {
		keysPressed.remove(key);
		updateEdoliteMovement();
	}

	private void updateEdoliteMovement() {
		if (isKeyPressed(KeyCode.UP) && isKeyPressed(KeyCode.DOWN)) edolite.stopMovingVertically();
		else if (isKeyPressed(KeyCode.UP)) edolite.moveUpward();
		else if (isKeyPressed(KeyCode.DOWN)) edolite.moveDownward();
		else edolite.stopMovingVertically();

		if (isKeyPressed(KeyCode.LEFT) && isKeyPressed(KeyCode.RIGHT)) edolite.stopMovingHorizontally();
		else if (isKeyPressed(KeyCode.LEFT)) edolite.moveLeftward();
		else if (isKeyPressed(KeyCode.RIGHT)) edolite.moveRightward();
		else edolite.stopMovingHorizontally();
	}

	private boolean isKeyPressed(KeyCode key) {
		return keysPressed.contains(key);
	}
}

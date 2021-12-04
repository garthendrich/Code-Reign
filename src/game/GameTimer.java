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

	public static final int ORGLIT_INITIAL_SPAWN_COUNT = 7;
	public static final int ORGLIT_SPAWN_COUNT = 3;
	public static final int ORGLIT_SPAWN_INTERVAL_SECONDS = 5;

	public static final int POWER_UP_SPAWN_INTERVAL_SECONDS = 10;
	public static final int POWER_UP_OCCURENCE_SECONDS = 5;

	private GraphicsContext graphicsContext;
	private Edolite edolite = new Edolite(150, 250);
	private ArrayList<Orglit> orglits = new ArrayList<Orglit>();
	private PowerUp powerUp;
	private long gameStartTimeInNanos = -1;
	private double gameTimeDuringPreviousOrglitSpawn = 0;
	private double gameTimeDuringPreviousPowerUpSpawn = 0;
	private ArrayList<KeyCode> keysPressed = new ArrayList<KeyCode>();

	GameTimer(GraphicsContext graphicsContext){
		this.graphicsContext = graphicsContext;

		spawnOrglits(ORGLIT_INITIAL_SPAWN_COUNT);
	}

	@Override
	public void handle(long currentTimeInNanos) {
		double gameTime = computeGameTime(currentTimeInNanos);

		double elapsedSecondsSincePreviousOrglitSpawn = gameTime - gameTimeDuringPreviousOrglitSpawn;
		if (elapsedSecondsSincePreviousOrglitSpawn > ORGLIT_SPAWN_INTERVAL_SECONDS) {
			spawnOrglits(ORGLIT_SPAWN_COUNT);
			gameTimeDuringPreviousOrglitSpawn = gameTime;
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

	private void spawnOrglits(int spawnCount) {
		for (int spawned = 0; spawned < spawnCount; spawned++) {
			Orglit orglit = createOrglitAtRandomUnoccupiedPosition();
			orglits.add(orglit);
		}
	}

	private Orglit createOrglitAtRandomUnoccupiedPosition() {
		Orglit orglit = null;

		for (int generatePositionAttempts = 0; generatePositionAttempts < 10; generatePositionAttempts++) {
			orglit = createOrglitAtRandomPosition();

			if (isOrglitCollidingAnotherGameElement(orglit) == false) break;
		}

		return orglit;
	}

	private Orglit createOrglitAtRandomPosition() {
		int lowestXPos = GameArea.LOWER_X_BOUND;
		int highestXPos = GameArea.UPPER_X_BOUND - Orglit.WIDTH;
		int randomXPos = generateRandomNumber(lowestXPos, highestXPos);

		int lowestYPos = GameArea.LOWER_Y_BOUND;
		int highestYPos = GameArea.UPPER_Y_BOUND - Orglit.HEIGHT;
		int randomYPos = generateRandomNumber(lowestYPos, highestYPos);

		return new Orglit(randomXPos, randomYPos);
	}

	private boolean isOrglitCollidingAnotherGameElement(Orglit orglit) {
		ArrayList<GameElement> gameElements = getAllGameElements();
		for (GameElement gameElement : gameElements) if (orglit.collidesWith(gameElement)) return true;
		return false;
	}

	private void spawnPowerUp() {
		int lowestXPos = GameArea.LOWER_X_BOUND;
		int highestXPos = GameArea.UPPER_X_BOUND - Orglit.WIDTH;
		int randomXPos = generateRandomNumber(lowestXPos, highestXPos);

		int lowestYPos = GameArea.LOWER_Y_BOUND;
		int highestYPos = GameArea.UPPER_Y_BOUND - Orglit.HEIGHT;
		int randomYPos = generateRandomNumber(lowestYPos, highestYPos);

		powerUp = createRandomPowerUpAt(randomXPos, randomYPos);
	}

	private PowerUp createRandomPowerUpAt(int xPos, int yPos) {
		Random randomizer = new Random();
		int randomPowerUpIndex = randomizer.nextInt(2);

		PowerUp powerUp = null;
		switch(randomPowerUpIndex) {
			case 0: powerUp = new Pearl(xPos, yPos);
			case 1: powerUp = new Star(xPos, yPos);
		}

		return powerUp;
	}

	private void deSpawnPowerUp() {
		powerUp = null;
	}

	private void updateSpritePositions() {
		ArrayList<Sprite> sprites = getAllSprites();
		for (Sprite sprite : sprites) sprite.updatePosition();
	}

	private void manageGameElementCollisions() {
		ArrayList<Bullet> edoliteBullets = edolite.getBullets();

		if (powerUp != null) manageCollisionOf(edolite, powerUp);

		for (Orglit orglit: orglits) {
			manageCollisionOf(edolite, orglit);

			for (Bullet edoliteBullet : edoliteBullets) manageCollisionOf(orglit, edoliteBullet);
		}
	}

	private void manageCollisionOf(Edolite edolite, PowerUp powerUp) {
		if (edolite.collidesWith(powerUp)) {
			powerUp.applyTo(edolite);

			deSpawnPowerUp();
		}
	}

	private void manageCollisionOf(Edolite edolite, Orglit orglit) {
		if (edolite.collidesWith(orglit)) {
			int orglitDamage = orglit.getDamage();
			edolite.reduceStrengthBy(orglitDamage);

			if (orglit instanceof Agmatron == false) orglits.remove(orglit);
		}
	}

	private void manageCollisionOf(Orglit orglit, Bullet edoliteBullet) {
		if (edoliteBullet.collidesWith(orglit)) {
			if (orglit instanceof Agmatron) {
				Agmatron agmatron = (Agmatron) orglit;

				int edoliteBulletDamage = edoliteBullet.getDamage();
				agmatron.reduceHealthBy(edoliteBulletDamage);

				if (agmatron.isAlive() == false) orglits.remove(agmatron);

			} else {
				orglits.remove(orglit);
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
		if (powerUp != null) gameElements.add(powerUp);
		return gameElements;
	}

	private ArrayList<Sprite> getAllSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(edolite);
		ArrayList<Bullet> edoliteBullets = edolite.getBullets();
		sprites.addAll(edoliteBullets);
		sprites.addAll(orglits);
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
		else if (isKeyPressed(KeyCode.UP)) edolite.moveUp();
		else if (isKeyPressed(KeyCode.DOWN)) edolite.moveDown();
		else edolite.stopMovingVertically();

		if (isKeyPressed(KeyCode.LEFT) && isKeyPressed(KeyCode.RIGHT)) edolite.stopMovingHorizontally();
		else if (isKeyPressed(KeyCode.LEFT)) edolite.moveLeft();
		else if (isKeyPressed(KeyCode.RIGHT)) edolite.moveRight();
		else edolite.stopMovingHorizontally();
	}

	private boolean isKeyPressed(KeyCode key) {
		return keysPressed.contains(key);
	}
}

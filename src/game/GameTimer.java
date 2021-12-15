package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import main.Main;

public class GameTimer extends AnimationTimer {

	public static final int MAX_GAME_TIME = 60;

	public static final int EDOLITE_INITIAL_X_POS = 150;
	public static final int EDOLITE_INITIAL_Y_POS = 250;

	public static final int ORGLIT_INITIAL_SPAWN_COUNT = 7;
	public static final int ORGLIT_SPAWN_COUNT = 3;
	public static final int ORGLIT_SPAWN_INTERVAL_SECONDS = 5;

	public static final int AGMATRON_SPAWN_GAME_TIME = 30;

	public static final int POWER_UP_SPAWN_INTERVAL_SECONDS = 10;
	public static final int POWER_UP_OCCURENCE_SECONDS = 5;

	private GraphicsContext graphicsContext;

	private Edolite edolite = new Edolite(EDOLITE_INITIAL_X_POS, EDOLITE_INITIAL_Y_POS);
	private ArrayList<Orglit> orglits = new ArrayList<Orglit>();
	private PowerUp powerUp;

	private long gameStartTimeInNanos = -1;
	private double gameTime;
	private double orglitSpawnGameTime = 0;
	private double powerUpSpawnGameTime = 0;
	private boolean isAgmatronSpawned = false;
	private int orglitsKilled = 0;

	private ArrayList<KeyCode> keysHeld = new ArrayList<KeyCode>();

	public GameTimer(GraphicsContext graphicsContext){
		this.graphicsContext = graphicsContext;
		graphicsContext.setFont(Font.font(Main.NOTALOT60, 20));
		graphicsContext.setTextBaseline(VPos.TOP);

		spawnOrglits(ORGLIT_INITIAL_SPAWN_COUNT);
	}

	@Override
	public void handle(long currentTimeInNanos) {
		updateGameTime(currentTimeInNanos);
		manageOrglitSpawns();
		managePowerUpSpawns();
		updateSpritePositions();
		manageGameElementCollisions();
		updateCanvas();
		checkGameEnd();
	}

	private void updateGameTime(long currentTimeInNanos) {
		if (gameStartTimeInNanos == -1) {
			gameStartTimeInNanos = currentTimeInNanos;
		}

		gameTime = (currentTimeInNanos - gameStartTimeInNanos) / 1_000_000_000.0;
	}

	private void manageOrglitSpawns() {
		double orglitSpawnElapsedSeconds = gameTime - orglitSpawnGameTime;

		if (orglitSpawnElapsedSeconds > ORGLIT_SPAWN_INTERVAL_SECONDS) {
			spawnOrglits(ORGLIT_SPAWN_COUNT);
			orglitSpawnGameTime = gameTime;
		}

		if (gameTime > AGMATRON_SPAWN_GAME_TIME && !isAgmatronSpawned) {
			spawnAgmatron();
			isAgmatronSpawned = true;
		}
	}

	private void spawnOrglits(int spawnCount) {
		for (int spawned = 0; spawned < spawnCount; spawned++) {
			int canvasMiddleX = Main.WINDOW_WIDTH / 2;
			int highestXPos = Main.WINDOW_WIDTH - Orglit.WIDTH;
			int randomXPos = generateRandomNumber(canvasMiddleX, highestXPos);

			int highestYPos = Main.WINDOW_HEIGHT - Orglit.HEIGHT;
			int randomYPos = generateRandomNumber(0, highestYPos);

			orglits.add(new Orglit(randomXPos, randomYPos));
		}
	}

	private void spawnAgmatron() {
		int canvasMiddleX = Main.WINDOW_WIDTH / 2;
		int highestXPos = Main.WINDOW_WIDTH - Agmatron.WIDTH;
		int randomXPos = generateRandomNumber(canvasMiddleX, highestXPos);

		int highestYPos = Main.WINDOW_HEIGHT - Agmatron.HEIGHT;
		int randomYPos = generateRandomNumber(0, highestYPos);

		orglits.add(new Agmatron(randomXPos, randomYPos));
	}

	private void managePowerUpSpawns() {
		double powerUpSpawnElapsedSeconds = gameTime - powerUpSpawnGameTime;

		if (powerUpSpawnElapsedSeconds > POWER_UP_OCCURENCE_SECONDS) {
			deSpawnPowerUp();
		}

		if (powerUpSpawnElapsedSeconds > POWER_UP_SPAWN_INTERVAL_SECONDS) {
			spawnPowerUp();
			powerUpSpawnGameTime = gameTime;
		}
	}

	private void spawnPowerUp() {
		int canvasMiddleX = Main.WINDOW_WIDTH / 2;
		int randomXPos = generateRandomNumber(0, canvasMiddleX);

		int highestYPos = Main.WINDOW_HEIGHT - PowerUp.SIZE;
		int randomYPos = generateRandomNumber(0, highestYPos);

		powerUp = createRandomPowerUpAt(randomXPos, randomYPos);
	}

	private PowerUp createRandomPowerUpAt(int xPos, int yPos) {
		PowerUp powerUp = null;

		Random randomizer = new Random();
		int randomPowerUpIndex = randomizer.nextInt(2);
		switch(randomPowerUpIndex) {
			case 0: powerUp = new Hexcore(xPos, yPos); break;
			case 1: powerUp = new Gemstone(xPos, yPos); break;
			default:
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

		for (Orglit orglit : orglits) manageCollisionOf(edolite, orglit);

		for (Orglit orglit : orglits) for (Bullet edoliteBullet : edoliteBullets) {
			if (orglit.isAlive() == false) break;
			manageCollisionOf(orglit, edoliteBullet);
		}

		deleteDeadOrglits();
		deleteCollidedBullets();
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
			edolite.receiveDamage(orglitDamage);

			if (orglit instanceof Agmatron == false) {
				orglit.die();
			}
		}
	}

	private void manageCollisionOf(Orglit orglit, Bullet edoliteBullet) {
		if (edoliteBullet.collidesWith(orglit)) {
			if (orglit instanceof Agmatron) {
				int edoliteBulletDamage = edoliteBullet.getDamage();

				Agmatron agmatron = (Agmatron) orglit;
				agmatron.reduceHealthBy(edoliteBulletDamage);
			} else {
				orglit.die();
			}

			edoliteBullet.collide();

			if (orglit.isAlive() == false) {
				orglitsKilled++;
			}
		}
	}

	private void deleteDeadOrglits() {
		ArrayList<Orglit> deadOrglits = new ArrayList<Orglit>();

		for (Orglit orglit : orglits) if (orglit.isAlive() == false) deadOrglits.add(orglit);

		for (Orglit deadOrglit : deadOrglits) orglits.remove(deadOrglit);
	}

	private void deleteCollidedBullets() {
		ArrayList<Bullet> collidedBullets = new ArrayList<Bullet>();

		ArrayList<Bullet> edoliteBullets = edolite.getBullets();

		for (Bullet edoliteBullet : edoliteBullets) {
			if (edoliteBullet.hasCollided()) collidedBullets.add(edoliteBullet);
		}

		for (Bullet collidedBullet : collidedBullets) {
			edoliteBullets.remove(collidedBullet);
		}
	}

	private void updateCanvas() {
		clearGameCanvas();

		ArrayList<GameElement> gameElements = getAllGameElements();
		for (GameElement gameElement : gameElements) render(gameElement);

		displayGameStats();
	}

	private void clearGameCanvas() {
		graphicsContext.clearRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
	}

	private void render(GameElement gameElement) {
		Image gameElementImage = gameElement.getImage();
		int gameElementXPos = gameElement.getXPos();
		int gameElementYPos = gameElement.getYPos();
		graphicsContext.drawImage(gameElementImage, gameElementXPos, gameElementYPos);
	}

	private void displayGameStats() {
		displayStrengthBar();
		displayOrglitsKilled();
		displayGameTimeLeft();
	}

	private void displayStrengthBar() {
		int edoliteStrength = edolite.getStrength();

		graphicsContext.setGlobalAlpha(0.75);
		graphicsContext.setFill(Color.valueOf("69CD2E"));
		graphicsContext.fillRect(16, 16, edoliteStrength, 32);
		graphicsContext.setGlobalAlpha(1);

		displayGameStatText(edoliteStrength + " strength", 24, 20);
	}

	private void displayOrglitsKilled() {
		displayGameStatText(orglitsKilled + " orglits killed", 24, 52);
	}

	private void displayGameTimeLeft() {
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		displayGameStatText("Time left", Main.WINDOW_WIDTH / 2, 16);
		displayGameStatText((MAX_GAME_TIME - (int) gameTime) + "", Main.WINDOW_WIDTH / 2, 40);
		graphicsContext.setTextAlign(TextAlignment.LEFT);
	}

	private void displayGameStatText(String text, double x, double y) {
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillText(text, x, y);
		graphicsContext.strokeText(text, x, y);
	}

	private void checkGameEnd() {
		if (edolite.isAlive() == false || gameTime >= MAX_GAME_TIME) {
			this.stop();
			// TODO Display game over
			displayGameOver();
		}
	}

	private void displayGameOver(){
		clearGameCanvas();
		graphicsContext.setFill(Color.valueOf("F6C27D"));
		graphicsContext.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
		if(edolite.isAlive() == true && gameTime >= MAX_GAME_TIME){
			graphicsContext.setFill(Color.GREEN);
			graphicsContext.setFont(Font.font(Main.NOTALOT60, 48));
			graphicsContext.fillText("CONGRATULATIONS! YOU WIN!", Main.WINDOW_WIDTH / 25, Main.WINDOW_HEIGHT / 4);
			displayGameOverStats(1);
		}else{
			graphicsContext.setFill(Color.RED);
			graphicsContext.setFont(Font.font(Main.NOTALOT60, 48));
			graphicsContext.fillText("GAME OVER! YOU LOSE!", Main.WINDOW_WIDTH / 6, Main.WINDOW_HEIGHT / 4);
			displayGameOverStats(0);
		}
	}

	private void displayGameOverStats(int state){
		switch(state){
		case 1:
			graphicsContext.setFill(Color.BLACK);
			graphicsContext.setFont(Font.font(Main.NOTALOT60, 32));
			graphicsContext.fillText("Game Time: " + (int) gameTime, Main.WINDOW_WIDTH / 3.1, Main.WINDOW_HEIGHT / 2.15);
			graphicsContext.fillText("Edolite Strength: " + edolite.getStrength(), Main.WINDOW_WIDTH / 3.1, Main.WINDOW_HEIGHT / 1.9);
			graphicsContext.fillText("Orglits Killed: " + orglitsKilled, Main.WINDOW_WIDTH / 3.1, Main.WINDOW_HEIGHT / 1.7);
			break;
		case 0:
			graphicsContext.setFill(Color.BLACK);
			graphicsContext.setFont(Font.font(Main.NOTALOT60, 32));
			graphicsContext.fillText("Game Time: " + (int) gameTime, Main.WINDOW_WIDTH / 2.8, Main.WINDOW_HEIGHT / 2.15);
			graphicsContext.fillText("Orglits Killed: " + orglitsKilled, Main.WINDOW_WIDTH / 2.8, Main.WINDOW_HEIGHT / 1.9);
		}
	}

	private int generateRandomNumber(int min, int max) {
		Random randomizer = new Random();
		return min + randomizer.nextInt(max - min + 1);
	}

	private ArrayList<GameElement> getAllGameElements() {
		ArrayList<GameElement> gameElements = new ArrayList<GameElement>();
		if (powerUp != null) gameElements.add(powerUp);
		ArrayList<Sprite> sprites = getAllSprites();
		gameElements.addAll(sprites);
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

	public void handleKeyPress(KeyCode key) {
		if ((key == KeyCode.SPACE) && !isKeyHeld(KeyCode.SPACE)) {
			edolite.shoot();
		}

		if (keysHeld.contains(key) == false) {
			keysHeld.add(key);
			updateEdoliteMovement();
		}
	}

	public void handleKeyRelease(KeyCode key) {
		keysHeld.remove(key);
		updateEdoliteMovement();
	}

	private void updateEdoliteMovement() {
		if (isKeyHeld(KeyCode.UP) && isKeyHeld(KeyCode.DOWN)) edolite.stopMovingVertically();
		else if (isKeyHeld(KeyCode.UP)) edolite.moveUp();
		else if (isKeyHeld(KeyCode.DOWN)) edolite.moveDown();
		else edolite.stopMovingVertically();

		if (isKeyHeld(KeyCode.LEFT) && isKeyHeld(KeyCode.RIGHT)) edolite.stopMovingHorizontally();
		else if (isKeyHeld(KeyCode.LEFT)) edolite.moveLeft();
		else if (isKeyHeld(KeyCode.RIGHT)) edolite.moveRight();
		else edolite.stopMovingHorizontally();
	}

	private boolean isKeyHeld(KeyCode key) {
		return keysHeld.contains(key);
	}
}

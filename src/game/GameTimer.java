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
import javafx.stage.Stage;

import main.Main;
import views.GameOverView;

public class GameTimer extends AnimationTimer {

	public static final int MAX_GAME_TIME = 60;

	public static final int EDOLITE_INITIAL_X_POS = 150;
	public static final int EDOLITE_INITIAL_Y_POS = 250;

	public static final int ORGLIT_INITIAL_SPAWN_COUNT = 7;
	public static final int ORGLIT_SPAWN_COUNT = 3;
	public static final int ORGLIT_SPAWN_INTERVAL_SECONDS = 5;

	public static final int AGMATRON_SPAWN_GAME_TIME = 30;
	public static final int AGMATRON_SMASH_DELAY_SECONDS = 1;
	public static final int AGMATRON_SHOOT_INTERVAL_SECONDS = 1;

	public static final int POWER_UP_SPAWN_INTERVAL_SECONDS = 10;
	public static final int POWER_UP_OCCURENCE_SECONDS = 5;

	private Stage stage;
	private GraphicsContext graphicsContext;

	private Edolite edolite = new Edolite(EDOLITE_INITIAL_X_POS, EDOLITE_INITIAL_Y_POS);
	private Agmatron agmatron = null;
	private ArrayList<Orglit> smallOrglits = new ArrayList<Orglit>();
	private PowerUp powerUp = null;

	private long gameStartTimeInNanos = -1;
	private double gameTime;
	private double orglitSpawnGameTime = 0;
	private double powerUpSpawnGameTime = 0;
	private double agmatronSmashGameTime = 0;
	private double agmatronShootGameTime = 0;
	private boolean isAgmatronSpawned = false;
	private int orglitsKilled = 0;

	private ArrayList<KeyCode> keysHeld = new ArrayList<KeyCode>();

	public GameTimer(GraphicsContext graphicsContext){
		this.graphicsContext = graphicsContext;

		graphicsContext.setFont(Font.font(Main.NOTALOT60, 20));
		graphicsContext.setTextBaseline(VPos.TOP);

		spawnOrglits(ORGLIT_INITIAL_SPAWN_COUNT);
	}

	public void receiveStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void handle(long currentTimeInNanos) {
		updateGameTime(currentTimeInNanos);

		manageOrglitSpawns();
		managePowerUpSpawns();
		manageAgmatronAttacks();

		updateMovableSpritePositions();
		manageSpriteCollisions();
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

			smallOrglits.add(new Orglit(randomXPos, randomYPos));
		}
	}

	private void spawnAgmatron() {
		int canvasMiddleX = Main.WINDOW_WIDTH / 2;
		int highestXPos = Main.WINDOW_WIDTH - Agmatron.WIDTH;
		int randomXPos = generateRandomNumber(canvasMiddleX, highestXPos);

		int highestYPos = Main.WINDOW_HEIGHT - Agmatron.HEIGHT;
		int randomYPos = generateRandomNumber(0, highestYPos);

		agmatron = new Agmatron(randomXPos, randomYPos);
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

	private void manageAgmatronAttacks() {
		if (agmatron == null) return;

		double agmatronShootElapsedSeconds = gameTime - agmatronShootGameTime;
		if (agmatronShootElapsedSeconds > AGMATRON_SHOOT_INTERVAL_SECONDS) {
			agmatron.shoot();

			agmatronShootGameTime = gameTime;
		}
	}

	private void updateMovableSpritePositions() {
		ArrayList<MovableSprite> movableSprites = getAllMovableSprites();
		for (MovableSprite movableSprite : movableSprites) movableSprite.updatePosition();
	}

	private void manageSpriteCollisions() {
		ArrayList<Bullet> edoliteBullets = edolite.getBullets();

		if (powerUp != null) manageCollisionOf(edolite, powerUp);

		for (Orglit smallOrglit : smallOrglits) {
			manageCollisionOf(edolite, smallOrglit);

			for (Bullet edoliteBullet : edoliteBullets) {
				if (smallOrglit.isAlive()) {
					manageCollisionOf(smallOrglit, edoliteBullet);
				}
			}
		}

		if (agmatron != null) {
			manageCollisionOf(edolite, agmatron);

			for (Bullet edoliteBullet : edoliteBullets) {
				manageCollisionOf(agmatron, edoliteBullet);
			}

			ArrayList<Bullet> agmatronBullets = agmatron.getBullets();
			for (Bullet agmatronBullet : agmatronBullets) {
				manageCollisionOf(edolite, agmatronBullet);
			}
		}

		deleteDeadOrglits();
		deleteCollidedBullets();
	}

	private void manageCollisionOf(Edolite edolite, PowerUp powerUp) {
		if (edolite.collidesWith(powerUp) == false) return;

		powerUp.applyTo(edolite);
		deSpawnPowerUp();
	}

	private void manageCollisionOf(Edolite edolite, Orglit orglit) {
		if (edolite.collidesWith(orglit) == false) return;

		if (orglit instanceof Agmatron) {
			double agmatronSmashElapsedSeconds = gameTime - agmatronSmashGameTime;
			if (agmatronSmashElapsedSeconds <= AGMATRON_SMASH_DELAY_SECONDS) return;

			agmatronSmashGameTime = gameTime;

		} else {
			orglit.die();
		}

		int orglitDamage = orglit.getDamage();
		edolite.receiveDamage(orglitDamage);
	}

	private void manageCollisionOf(Orglit orglit, Bullet bullet) {
		if (bullet.collidesWith(orglit) == false) return;

		if (orglit instanceof Agmatron) {
			int bulletDamage = bullet.getDamage();

			Agmatron agmatron = (Agmatron) orglit;
			agmatron.reduceHealthBy(bulletDamage);
		} else {
			orglit.die();
		}

		bullet.collide();

		if (orglit.isAlive() == false) {
			orglitsKilled++;
		}
	}

	private void manageCollisionOf(Edolite edolite, Bullet bullet) {
		if (edolite.collidesWith(bullet) == false) return;

		int bulletDamage = bullet.getDamage();
		edolite.receiveDamage(bulletDamage);

		bullet.collide();
	}

	private void deleteDeadOrglits() {
		if (agmatron != null && agmatron.isAlive() == false) agmatron = null;

		ArrayList<Orglit> deadOrglits = new ArrayList<Orglit>();

		for (Orglit smallOrglit : smallOrglits) if (smallOrglit.isAlive() == false) {
			deadOrglits.add(smallOrglit);
		}

		for (Orglit deadOrglit : deadOrglits) smallOrglits.remove(deadOrglit);
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

		if (agmatron != null) {
			ArrayList<Bullet> agmatronBullets = agmatron.getBullets();
			for (Bullet agmatronBullet : agmatronBullets) {
				if (agmatronBullet.hasCollided()) collidedBullets.add(agmatronBullet);
			}

			for (Bullet collidedBullet : collidedBullets) {
				agmatronBullets.remove(collidedBullet);
			}
		}
	}

	private void updateCanvas() {
		clearGameCanvas();

		ArrayList<Sprite> sprites = getAllSprites();
		for (Sprite sprite : sprites) sprite.render(graphicsContext);

		displayGameStats();
	}

	private void clearGameCanvas() {
		graphicsContext.clearRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
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

			int edoliteStrength = edolite.getStrength();
			GameOverView gameOverView = new GameOverView(edoliteStrength, orglitsKilled);
			gameOverView.loadTo(stage);
		}
	}

	private int generateRandomNumber(int min, int max) {
		Random randomizer = new Random();
		return min + randomizer.nextInt(max - min + 1);
	}

	private ArrayList<Sprite> getAllSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();

		if (powerUp != null) sprites.add(powerUp);

		ArrayList<MovableSprite> movableSprites = getAllMovableSprites();
		sprites.addAll(movableSprites);

		return sprites;
	}

	private ArrayList<MovableSprite> getAllMovableSprites() {
		ArrayList<MovableSprite> movableSprites = new ArrayList<MovableSprite>();

		ArrayList<Bullet> edoliteBullets = edolite.getBullets();
		movableSprites.add(edolite);
		movableSprites.addAll(edoliteBullets);

		movableSprites.addAll(smallOrglits);

		if (agmatron != null) {
			ArrayList<Bullet> agmatronBullets = agmatron.getBullets();
			movableSprites.add(agmatron);
			movableSprites.addAll(agmatronBullets);
		}

		return movableSprites;
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
		else if (isKeyHeld(KeyCode.UP)) edolite.moveUp(Edolite.MOVEMENT_SPEED);
		else if (isKeyHeld(KeyCode.DOWN)) edolite.moveDown(Edolite.MOVEMENT_SPEED);
		else edolite.stopMovingVertically();

		if (isKeyHeld(KeyCode.LEFT) && isKeyHeld(KeyCode.RIGHT)) edolite.stopMovingHorizontally();
		else if (isKeyHeld(KeyCode.LEFT)) edolite.moveLeft(Edolite.MOVEMENT_SPEED);
		else if (isKeyHeld(KeyCode.RIGHT)) edolite.moveRight(Edolite.MOVEMENT_SPEED);
		else edolite.stopMovingHorizontally();
	}

	private boolean isKeyHeld(KeyCode key) {
		return keysHeld.contains(key);
	}
}

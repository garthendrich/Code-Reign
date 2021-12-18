package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import views.GameOverView;
import views.View;

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

	public static final int STATUS_BAR_MAX_LENGTH = 240;
	public static final Color GREEN_COLOR = Color.valueOf("69CD2E");
	public static final Color RED_COLOR = Color.valueOf("CC2D2D");

	private Stage stage;
	private GraphicsContext graphicsContext;

	private Edolite edolite = new Edolite(EDOLITE_INITIAL_X_POS, EDOLITE_INITIAL_Y_POS);
	private Agmatron agmatron = null;
	private ArrayList<Orglit> smallOrglits = new ArrayList<Orglit>();
	private PowerUp powerUp = null;

	private long gameStartTimeInNanos = -1;
	private double gameTime;
	private double smallOrglitSpawnGameTime = 0;
	private double powerUpSpawnGameTime = 0;
	private double agmatronSmashGameTime = 0;
	private double agmatronShootGameTime = 0;
	private boolean isAgmatronSpawned = false;
	private int orglitsKilled = 0;

	private ArrayList<KeyCode> keysHeld = new ArrayList<KeyCode>();

	public GameTimer(GraphicsContext graphicsContext){
		this.graphicsContext = graphicsContext;

		graphicsContext.setFont(Font.font(View.NOTALOT60, 20));
		graphicsContext.setTextBaseline(VPos.TOP);

		spawnSmallOrglits(ORGLIT_INITIAL_SPAWN_COUNT);
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
		deleteHiddenSprites();
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
		double smallOrglitSpawnElapsedSeconds = gameTime - smallOrglitSpawnGameTime;
		if (smallOrglitSpawnElapsedSeconds > ORGLIT_SPAWN_INTERVAL_SECONDS) {
			spawnSmallOrglits(ORGLIT_SPAWN_COUNT);

			smallOrglitSpawnGameTime = gameTime;
		}

		if (gameTime > AGMATRON_SPAWN_GAME_TIME && !isAgmatronSpawned) {
			spawnAgmatron();

			isAgmatronSpawned = true;
		}
	}

	private void spawnSmallOrglits(int spawnCount) {
		for (int spawned = 0; spawned < spawnCount; spawned++) {
			int canvasMiddleX = View.WINDOW_WIDTH / 2;
			int highestXPos = View.WINDOW_WIDTH - Orglit.WIDTH;
			int randomXPos = generateRandomNumber(canvasMiddleX, highestXPos);

			int highestYPos = View.WINDOW_HEIGHT - Orglit.HEIGHT;
			int randomYPos = generateRandomNumber(0, highestYPos);

			smallOrglits.add(new Orglit(randomXPos, randomYPos));
		}
	}

	private void spawnAgmatron() {
		int canvasMiddleX = View.WINDOW_WIDTH / 2;
		int highestXPos = View.WINDOW_WIDTH - Agmatron.WIDTH;
		int randomXPos = generateRandomNumber(canvasMiddleX, highestXPos);

		int highestYPos = View.WINDOW_HEIGHT - Agmatron.HEIGHT;
		int randomYPos = generateRandomNumber(0, highestYPos);

		agmatron = new Agmatron(randomXPos, randomYPos);
	}

	private void managePowerUpSpawns() {
		double powerUpSpawnElapsedSeconds = gameTime - powerUpSpawnGameTime;

		if (powerUp != null && powerUpSpawnElapsedSeconds > POWER_UP_OCCURENCE_SECONDS) {
			powerUp.despawn();
		}

		if (powerUpSpawnElapsedSeconds > POWER_UP_SPAWN_INTERVAL_SECONDS) {
			powerUp = createRandomPowerUp();

			powerUpSpawnGameTime = gameTime;
		}
	}

	private PowerUp createRandomPowerUp() {
		int canvasMiddleX = View.WINDOW_WIDTH / 2;
		int randomXPos = generateRandomNumber(0, canvasMiddleX);

		int highestYPos = View.WINDOW_HEIGHT - PowerUp.SIZE;
		int randomYPos = generateRandomNumber(0, highestYPos);

		Random randomizer = new Random();
		switch(randomizer.nextInt(3)) {
			case 0: return new Hexcore(randomXPos, randomYPos);
			case 1: return new ElixirOfAeons(randomXPos, randomYPos);
			case 2: return new Gemstone(randomXPos, randomYPos);
			default:
		}
		return null;
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
			manageCollisionOf(smallOrglit, edolite);

			for (Bullet edoliteBullet : edoliteBullets) {
				if (!smallOrglit.isHidden()) {
					manageCollisionOf(edoliteBullet, smallOrglit);
				}
			}
		}

		if (agmatron != null) {
			manageCollisionOf(agmatron, edolite);

			for (Bullet edoliteBullet : edoliteBullets) {
				manageCollisionOf(edoliteBullet, agmatron);
			}

			ArrayList<Bullet> agmatronBullets = agmatron.getBullets();
			for (Bullet agmatronBullet : agmatronBullets) {
				manageCollisionOf(agmatronBullet, edolite);
			}
		}
	}

	private void manageCollisionOf(Edolite edolite, PowerUp powerUp) {
		if (edolite.collidesWith(powerUp) == false) return;

		powerUp.applyTo(edolite);
		powerUp.despawn();
	}

	private void manageCollisionOf(Orglit orglit, Edolite edolite) {
		if (orglit.collidesWith(edolite) == false) return;
		if (edolite.hasStatusEffect(StatusEffect.INVULNERABILITY)) return;

		if (orglit instanceof Agmatron) {
			double agmatronSmashElapsedSeconds = gameTime - agmatronSmashGameTime;
			if (agmatronSmashElapsedSeconds <= AGMATRON_SMASH_DELAY_SECONDS) return;

			agmatronSmashGameTime = gameTime;

		} else {
			orglit.vanish();
		}

		int orglitDamage = orglit.getDamage();
		edolite.reduceStrengthBy(orglitDamage);
	}

	private void manageCollisionOf(Bullet bullet, Orglit orglit) {
		if (bullet.collidesWith(orglit) == false) return;

		if (orglit instanceof Agmatron) {
			int bulletDamage = bullet.getDamage();

			Agmatron agmatron = (Agmatron) orglit;
			agmatron.reduceHealthBy(bulletDamage);
		} else {
			orglit.vanish();
		}

		bullet.vanish();

		if (orglit.isHidden()) {
			orglitsKilled++;
		}
	}

	private void manageCollisionOf(Bullet bullet, Edolite edolite) {
		if (bullet.collidesWith(edolite) == false) return;

		int bulletDamage = bullet.getDamage();
		edolite.reduceStrengthBy(bulletDamage);

		bullet.vanish();
	}

	private void deleteHiddenSprites() {
		deleteDeadOrglits();

		ArrayList<Bullet> edoliteBullets = edolite.getBullets();
		deleteCollidedBulletsFrom(edoliteBullets);

		if (agmatron != null) {
			ArrayList<Bullet> agmatronBullets = agmatron.getBullets();
			deleteCollidedBulletsFrom(agmatronBullets);
		}

		if (powerUp != null && powerUp.isHidden()) powerUp = null;
	}

	private void deleteDeadOrglits() {
		for (int index = 0; index < smallOrglits.size(); index++) {
			Orglit smallOrglit = smallOrglits.get(index);

			if (smallOrglit.isHidden()) {
				smallOrglits.remove(smallOrglit);
			}
		}

		if (agmatron != null && agmatron.isHidden()) agmatron = null;
	}

	private void deleteCollidedBulletsFrom(ArrayList<Bullet> bullets) {
		for (int index = 0; index < bullets.size(); index++) {
			Bullet bullet = bullets.get(index);

			if (bullet.isHidden()) {
				bullets.remove(bullet);
			}
		}
	}

	private void updateCanvas() {
		clearGameCanvas();

		ArrayList<Sprite> sprites = getAllSprites();
		for (Sprite sprite : sprites) sprite.render(graphicsContext);

		displayGameStatus();
	}

	private void clearGameCanvas() {
		graphicsContext.clearRect(0, 0, View.WINDOW_WIDTH, View.WINDOW_HEIGHT);
	}

	private void displayGameStatus() {
		displayAllStatusBars();
		displayOrglitsKilled();
		displayGameTimeLeft();
	}

	private void displayAllStatusBars() {
		int edoliteStrength = edolite.getStrength();
		displayStatusBar(16, 16, "strength", edoliteStrength, Edolite.MAX_INITIAL_STRENGTH + 100, GREEN_COLOR);

		if (agmatron != null) {
			int agmatronStatusBarXPos = View.WINDOW_WIDTH - STATUS_BAR_MAX_LENGTH - 16;

			displayGameStatusText("Agmatron:", agmatronStatusBarXPos + 4, 16);

			int agmatronHealth = agmatron.getHealth();
			displayStatusBar(agmatronStatusBarXPos, 44, "health", agmatronHealth, Agmatron.MAX_HEALTH, RED_COLOR);
		}
	}

	private void displayStatusBar(
			int xPos,
			int yPos,
			String statusLabel,
			int statusValue,
			int maxStatusValue,
			Color color) {

		double statusBarPercentage = statusValue / (double) maxStatusValue;
		int statusBarLength =  (int) (statusBarPercentage * STATUS_BAR_MAX_LENGTH);
		if (statusBarPercentage > 1) {
			statusBarLength = STATUS_BAR_MAX_LENGTH;
		}

		graphicsContext.setGlobalAlpha(0.75);
		graphicsContext.setFill(color);
		graphicsContext.fillRect(xPos, yPos, statusBarLength, 32);
		graphicsContext.setGlobalAlpha(1);

		displayGameStatusText(statusValue + " " + statusLabel, xPos + 8, yPos + 4);
	}

	private void displayOrglitsKilled() {
		displayGameStatusText(orglitsKilled + " orglits killed", 24, 56);
	}

	private void displayGameTimeLeft() {
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		displayGameStatusText("Time left", View.WINDOW_WIDTH / 2, 16);

		graphicsContext.setFont(Font.font(View.NOTALOT60, 28));
		displayGameStatusText("" + (MAX_GAME_TIME - (int) gameTime), View.WINDOW_WIDTH / 2, 40);

		graphicsContext.setFont(Font.font(View.NOTALOT60, 20));
		graphicsContext.setTextAlign(TextAlignment.LEFT);
	}

	private void displayGameStatusText(String text, int xPos, int yPos) {
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.setStroke(View.STROKE_COLOR);
		graphicsContext.fillText(text, xPos, yPos);
		graphicsContext.strokeText(text, xPos, yPos);
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

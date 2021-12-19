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

	public static final int STATUS_BAR_MAX_LENGTH = 240;
	public static final Color GREEN_COLOR = Color.valueOf("69CD2E");
	public static final Color RED_COLOR = Color.valueOf("CC2D2D");

	public static final int EDOLITE_INITIAL_X_POS = 150;
	public static final int EDOLITE_INITIAL_Y_POS = 250;

	public static final int POWER_UP_SPAWN_INTERVAL_SECONDS = 10;
	public static final int POWER_UP_OCCURENCE_SECONDS = 5;

	public static final int ORGLIT_INITIAL_SPAWN_COUNT = 7;
	public static final int ORGLIT_SPAWN_COUNT = 3;
	public static final int ORGLIT_SPAWN_INTERVAL_SECONDS = 5;

	public static final int AGMATRON_SPAWN_GAME_TIME = 30;
	public static final int AGMATRON_SMASH_DELAY_SECONDS = 1;
	public static final int AGMATRON_SHOOT_INTERVAL_SECONDS = 1;

	private Stage stage;
	private GraphicsContext graphicsContext;

	private ArrayList<KeyCode> keysHeld = new ArrayList<KeyCode>();

	private Edolite edolite = new Edolite(EDOLITE_INITIAL_X_POS, EDOLITE_INITIAL_Y_POS);
	private PowerUp powerUp = null;
	private ArrayList<Orglit> smallOrglits = new ArrayList<Orglit>();
	private Agmatron agmatron = null;

	private int orglitsKilled = 0;
	private boolean isAgmatronSpawned = false;

	private long gameStartTimeInNanos = -1;
	private double gameTime;
	private double powerUpSpawnGameTime = 0;
	private double smallOrglitSpawnGameTime = 0;
	private double agmatronSmashGameTime = 0;
	private double agmatronShootGameTime = 0;

	public GameTimer(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;

		graphicsContext.setFont(Font.font(View.NOTALOT60, 20));
		graphicsContext.setTextBaseline(VPos.TOP);

		spawnSmallOrglits(ORGLIT_INITIAL_SPAWN_COUNT);
	}

	public void receiveStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Handles a given pressed key.
	 *
	 * This controls the edolite's attack and movement.
	 *
	 * @param key The pressed key.
	 */
	public void handleKeyPress(KeyCode key) {
		if ((key == KeyCode.SPACE) && !isKeyHeld(KeyCode.SPACE)) {
			edolite.shoot();
		}

		if (keysHeld.contains(key) == false) {
			keysHeld.add(key);
			updateEdoliteMovement();
		}
	}

	/**
	 * Handles a given released key.
	 *
	 * This controls the edolite's movement.
	 *
	 * @param key The released key.
	 */
	public void handleKeyRelease(KeyCode key) {
		keysHeld.remove(key);
		updateEdoliteMovement();
	}

	/**
	 * Updates the edolite's movement based on the pressed keys.
	 */
	private void updateEdoliteMovement() {
		if (isKeyHeld(KeyCode.LEFT) && isKeyHeld(KeyCode.RIGHT)) edolite.stopMovingHorizontally();
		else if (isKeyHeld(KeyCode.LEFT)) edolite.moveLeft();
		else if (isKeyHeld(KeyCode.RIGHT)) edolite.moveRight();
		else edolite.stopMovingHorizontally();

		if (isKeyHeld(KeyCode.UP) && isKeyHeld(KeyCode.DOWN)) edolite.stopMovingVertically();
		else if (isKeyHeld(KeyCode.UP)) edolite.moveUp();
		else if (isKeyHeld(KeyCode.DOWN)) edolite.moveDown();
		else edolite.stopMovingVertically();
	}

	private boolean isKeyHeld(KeyCode key) {
		return keysHeld.contains(key);
	}

	@Override
	public void handle(long currentTimeInNanos) {
		updateGameTime(currentTimeInNanos);

		manageOrglitSpawns();
		managePowerUpSpawns();
		manageAgmatronProjectiles();

		updateMovableSpritePositions();
		manageSpriteCollisions();
		deleteHiddenSprites();
		updateCanvas();

		checkGameEnd();
	}

	/**
	 * Updates the game time given the current time in nanoseconds.
	 *
	 * The game time is based on the elapsed seconds since this AnimationTimer has started. Thus,
	 * the game start time is recorded once.
	 *
	 * @param currentTimeInNanos The current time in nanoseconds.
	 */
	private void updateGameTime(long currentTimeInNanos) {
		if (gameStartTimeInNanos == -1) {
			gameStartTimeInNanos = currentTimeInNanos;
		}

		gameTime = (currentTimeInNanos - gameStartTimeInNanos) / 1_000_000_000.0;
	}

	/**
	 * Responsible for spawning orglits at certain time periods.
	 *
	 * A number of small orglits are spawned every given time interval.
	 * The agmatron is spawned once at a certain game time.
	 */
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

	/**
	 * Spawns a number of small orglits.
	 *
	 * These orglits are spawned at a random location at the right half side of the game window.
	 *
	 * @param spawnCount The number of small orglits to be spawned.
	 */
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

	/**
	 * Spawns the agmatron.
	 *
	 * It is spawned at a random location at the right half side of the game window.
	 */
	private void spawnAgmatron() {
		int canvasMiddleX = View.WINDOW_WIDTH / 2;
		int highestXPos = View.WINDOW_WIDTH - Agmatron.WIDTH;
		int randomXPos = generateRandomNumber(canvasMiddleX, highestXPos);

		int highestYPos = View.WINDOW_HEIGHT - Agmatron.HEIGHT;
		int randomYPos = generateRandomNumber(0, highestYPos);

		agmatron = new Agmatron(randomXPos, randomYPos);
	}

	/**
	 * Responsible for spawning and despawning power-ups at certain time intervals.
	 */
	private void managePowerUpSpawns() {
		double powerUpSpawnElapsedSeconds = gameTime - powerUpSpawnGameTime;

		if (powerUp != null && powerUpSpawnElapsedSeconds > POWER_UP_OCCURENCE_SECONDS) {
			powerUp.vanish();
		}

		if (powerUpSpawnElapsedSeconds > POWER_UP_SPAWN_INTERVAL_SECONDS) {
			powerUp = createRandomPowerUp();

			powerUpSpawnGameTime = gameTime;
		}
	}

	/**
	 * Creates a random power-up at a random location at the left half side of the game window.
	 *
	 * @return The created power-up.
	 */
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

	/**
	 * Controls agmatron's attacks.
	 *
	 * The agmatron shoots projectiles at a certain time interval.
	 */
	private void manageAgmatronProjectiles() {
		if (agmatron == null) return;

		double agmatronShootElapsedSeconds = gameTime - agmatronShootGameTime;
		if (agmatronShootElapsedSeconds > AGMATRON_SHOOT_INTERVAL_SECONDS) {
			agmatron.shoot();

			agmatronShootGameTime = gameTime;
		}
	}

	/**
	 * Updates the positions of all movable sprites.
	 */
	private void updateMovableSpritePositions() {
		ArrayList<MovableSprite> movableSprites = getAllMovableSprites();
		for (MovableSprite movableSprite : movableSprites) movableSprite.updatePosition();
	}

	/**
	 * Manages the collisions of all sprites.
	 */
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

	/**
	 * Applies the power-up to a colliding edolite.
	 *
	 * @param edolite
	 * @param powerUp
	 */
	private void manageCollisionOf(Edolite edolite, PowerUp powerUp) {
		if (edolite.isCollidingWith(powerUp) == false) return;

		powerUp.applyTo(edolite);
		powerUp.vanish();
	}

	/**
	 * The orglit damages a colliding edolite.
	 *
	 * @param orglit
	 * @param edolite
	 */
	private void manageCollisionOf(Orglit orglit, Edolite edolite) {
		if (orglit.isCollidingWith(edolite) == false) return;
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

	/**
	 * The bullet damages a colliding orglit.
	 *
	 * @param bullet
	 * @param orglit
	 */
	private void manageCollisionOf(Bullet bullet, Orglit orglit) {
		if (bullet.isCollidingWith(orglit) == false) return;

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

	/**
	 * The bullet damages a colliding edolite.
	 *
	 * @param bullet
	 * @param edolite
	 */
	private void manageCollisionOf(Bullet bullet, Edolite edolite) {
		if (bullet.isCollidingWith(edolite) == false) return;

		int bulletDamage = bullet.getDamage();
		edolite.reduceStrengthBy(bulletDamage);

		bullet.vanish();
	}

	/**
	 * Deletes all sprites that were vanished.
	 */
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

	/**
	 * Deletes all vanished orglits.
	 */
	private void deleteDeadOrglits() {
		for (int index = 0; index < smallOrglits.size(); index++) {
			Orglit smallOrglit = smallOrglits.get(index);

			if (smallOrglit.isHidden()) {
				smallOrglits.remove(smallOrglit);
			}
		}

		if (agmatron != null && agmatron.isHidden()) agmatron = null;
	}

	/**
	 * Deletes the vanished bullets from a given list.
	 *
	 * @param bullets
	 */
	private void deleteCollidedBulletsFrom(ArrayList<Bullet> bullets) {
		for (int index = 0; index < bullets.size(); index++) {
			Bullet bullet = bullets.get(index);

			if (bullet.isHidden()) {
				bullets.remove(bullet);
			}
		}
	}

	/**
	 * Updates the content of the game canvas.
	 */
	private void updateCanvas() {
		clearGameCanvas();

		ArrayList<Sprite> sprites = getAllSprites();
		for (Sprite sprite : sprites) sprite.render(graphicsContext);

		displayGameStatus();
	}

	/**
	 * Clears the whole game canvas.
	 */
	private void clearGameCanvas() {
		graphicsContext.clearRect(0, 0, View.WINDOW_WIDTH, View.WINDOW_HEIGHT);
	}

	/**
	 * Displays all game statuses.
	 */
	private void displayGameStatus() {
		displayAllStatusBars();
		displayOrglitsKilled();
		displayGameTimeLeft();
	}

	/**
	 * Displays the status bars of edolite and agmatron.
	 */
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

	/**
	 * Renders the status bar to the game canvas given the information.
	 *
	 * @param xPos
	 * @param yPos
	 * @param statusLabel
	 * @param statusValue
	 * @param maxStatusValue
	 * @param color
	 */
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

	/**
	 * Displays the number of orglits killed by the edolite.
	 */
	private void displayOrglitsKilled() {
		displayGameStatusText(orglitsKilled + " orglits killed", 24, 56);
	}

	/**
	 * Renders the game time left to the game canvas.
	 */
	private void displayGameTimeLeft() {
		graphicsContext.setTextAlign(TextAlignment.CENTER);
		displayGameStatusText("Time left", View.WINDOW_WIDTH / 2, 16);

		graphicsContext.setFont(Font.font(View.NOTALOT60, 28));
		displayGameStatusText("" + (MAX_GAME_TIME - (int) gameTime), View.WINDOW_WIDTH / 2, 40);

		graphicsContext.setFont(Font.font(View.NOTALOT60, 20));
		graphicsContext.setTextAlign(TextAlignment.LEFT);
	}

	/**
	 * Renders a text to the game canvas given the information.
	 *
	 * @param text
	 * @param xPos
	 * @param yPos
	 */
	private void displayGameStatusText(String text, int xPos, int yPos) {
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.setStroke(View.PRIMARY_COLOR);
		graphicsContext.fillText(text, xPos, yPos);
		graphicsContext.strokeText(text, xPos, yPos);
	}

	/**
	 * Checks whether the game has ended.
	 *
	 * The game ends if the edolite dies or the game time reaches the max game time.
	 *
	 * Loads the game over screen to the stage if the game has ended.
	 */
	private void checkGameEnd() {
		if (edolite.isAlive() == false || gameTime >= MAX_GAME_TIME) {
			this.stop();

			int edoliteStrength = edolite.getStrength();
			GameOverView gameOverView = new GameOverView(edoliteStrength, orglitsKilled);
			gameOverView.loadTo(stage);
		}
	}

	/**
	 * Returns a random number between a minimum and a maximum value.
	 *
	 * @param min The minimum random number.
	 * @param max The maximum random number.
	 * @return The random number.
	 */
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

		movableSprites.add(edolite);
		ArrayList<Bullet> edoliteBullets = edolite.getBullets();
		movableSprites.addAll(edoliteBullets);

		movableSprites.addAll(smallOrglits);

		if (agmatron != null) {
			movableSprites.add(agmatron);
			ArrayList<Bullet> agmatronBullets = agmatron.getBullets();
			movableSprites.addAll(agmatronBullets);
		}

		return movableSprites;
	}
}
package views;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import game.GameTimer;

public class GameStage {
	public static final int WINDOW_WIDTH = 750;
	public static final int WINDOW_HEIGHT = 500;

	public static final int CANVAS_WIDTH = 750;
	public static final int CANVAS_HEIGHT = 500;

	private Scene scene;
	private GameTimer gameTimer;

	public GameStage() {
		StackPane root = new StackPane();
		Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		root.getChildren().add(canvas);
		scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.valueOf("F6C27D"));

		setupSceneEventHandlers();

		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		gameTimer = new GameTimer(graphicsContext);
	}

	private void setupSceneEventHandlers() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				KeyCode key = event.getCode();
				gameTimer.handleKeyPress(key);
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				KeyCode key = event.getCode();
				gameTimer.handleKeyRelease(key);
			}
		});
	}

	/**
	 * Displays the scene to a stage
	 *
	 * @param stage (Stage) : window where the scene will be loaded
	 */
	public void loadGame(Stage stage) {
		stage.setScene(scene);
		stage.show();

		gameTimer.start();
	}
}
package views;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import main.Main;
import game.GameTimer;

class GameView extends View {

	private GameTimer gameTimer;

	@Override
	protected Scene createScene() {
		StackPane root = new StackPane();

		Canvas canvas = new Canvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		gameTimer = new GameTimer(graphicsContext);

		root.getChildren().add(canvas);

		Scene scene = new Scene(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

		scene.setOnKeyPressed(
			new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					KeyCode key = event.getCode();
					gameTimer.handleKeyPress(key);
				}
			}
		);

		scene.setOnKeyReleased(
			new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					KeyCode key = event.getCode();
					gameTimer.handleKeyRelease(key);
				}
			}
		);

		return scene;
	}

	@Override
	public void loadTo(Stage stage) {
		super.loadTo(stage);
		gameTimer.start();
	}
}
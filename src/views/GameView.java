package views;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import game.GameTimer;

class GameView extends View {

	private GameTimer gameTimer;

	@Override
	protected Parent createRoot() {
		StackPane root = new StackPane();
		root.setBackground(new Background(new BackgroundFill(Color.valueOf(View.BG_COLOR), null, null)));

		Canvas canvas = new Canvas(View.WINDOW_WIDTH, View.WINDOW_HEIGHT);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

		gameTimer = new GameTimer(graphicsContext);

		root.getChildren().add(canvas);

		return root;
	}

	@Override
	public void loadTo(Stage stage) {
		super.loadTo(stage);

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

		gameTimer.receiveStage(stage);
		gameTimer.start();
	}
}
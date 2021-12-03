package game;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameStage {
	public static final int WINDOW_WIDTH = 900;
	public static final int WINDOW_HEIGHT = 600;

	private Scene scene;
	private GameTimer gametimer;

	public GameStage() {
		StackPane root = new StackPane();
		Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		root.getChildren().add(canvas);
		scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		gametimer = new GameTimer(graphicsContext);
	}

	/**
	 * Displays the scene to a stage
	 *
	 * @param stage (Stage) : window where the scene will be loaded
	 */
	public void loadTo(Stage stage) {
		stage.setTitle("Sci-fi Shooting Game");
		stage.setScene(scene);
		stage.show();

		gametimer.start();
	}



}


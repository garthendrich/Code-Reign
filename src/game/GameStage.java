package game;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

class GameStage {

	public static final int WINDOW_WIDTH = 750;
	public static final int WINDOW_HEIGHT = 500;

	public static final int CANVAS_WIDTH = 750;
	public static final int CANVAS_HEIGHT = 500;

	private Group root;
	private Stage stage;
	private Scene scene;
	private Scene splash;
	private GameTimer gameTimer;
	private Canvas canvas;

	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(root);
		this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		this.root.getChildren().add(canvas);
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
		stage.setScene(this.scene);

		setupSceneEventHandlers();

		GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
		gameTimer = new GameTimer(graphicsContext);
		gameTimer.start();
	}

	public void setStage(Stage stage){
		this.stage = stage;
		stage.setTitle("Code:Reign");
		this.initSplash(stage);
		stage.setScene(splash);
		stage.setResizable(false);
		stage.show();
	}

	private void initSplash(Stage stage){
		StackPane root = new StackPane();
		root.getChildren().addAll(createCanvas(), createVBox());
		splash = new Scene(root);
	}

	private Canvas createCanvas(){
		Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.valueOf("F6C27D"));
		gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		return canvas;
	}

	private VBox createVBox(){
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);

		Button newGame = new Button("New Game");
		Button instructions = new Button("Instructions");
		Button about = new Button("About");

		vbox.getChildren().addAll(newGame, instructions, about);

		newGame.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				loadGame(stage);
			}
		});



		return vbox;
	}
}


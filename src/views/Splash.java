package views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import main.Main;

public class Splash {
	private Group root;
	private Stage stage;
	private Scene splash;
	private Canvas canvas;

	public Splash() {
		this.root = new Group();
		this.canvas = new Canvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
		this.root.getChildren().add(this.canvas);
	}

	public void setSplashStage(Stage stage){
		this.stage = stage;
		stage.setTitle("Code:Reign");
		initSplash(stage);
		stage.setScene(splash);
		stage.setResizable(false);
		stage.show();
	}

	private void initSplash(Stage stage){
		StackPane root = new StackPane();
		root.getChildren().addAll(createCanvas(), createVBox());
		splash = new Scene(root);
	}

	public Canvas createCanvas(){
		Canvas canvas = new Canvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.valueOf("F6C27D"));
		gc.fillRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
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
				GameStage gameStage = new GameStage();
				gameStage.loadGame(stage);
			}
		});

		instructions.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				Instructions instructionScreen = new Instructions();
				instructionScreen.loadInstructions(stage);
			}
		});

		about.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				About aboutScreen = new About();
				aboutScreen.loadAbout(stage);
			}
		});

		return vbox;
	}
}


package views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

class About {
	private Group root;
	private Scene instructions;
	private GraphicsContext gc;
	private Canvas canvas;
	private Stage stage;

	public About(){
		this.root = new Group();
		this.canvas = new Canvas(GameStage.CANVAS_WIDTH, GameStage.CANVAS_HEIGHT);
		root.getChildren().add(this.canvas);
		gc = canvas.getGraphicsContext2D();
	}

	public void loadAbout(Stage stage){
		this.stage = stage;
		initAbout(stage);
		stage.setScene(instructions);
		stage.show();
	}

	private void initAbout(Stage stage){
		StackPane root = new StackPane();
		root.getChildren().addAll(createCanvas(), createVBox());
		instructions = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		displayInstructions();
	}

	private Canvas createCanvas(){
		Canvas canvas = new Canvas(GameStage.CANVAS_WIDTH, GameStage.CANVAS_HEIGHT);
		gc.setFill(Color.valueOf("F6C27D"));
		gc.fillRect(0, 0, GameStage.CANVAS_WIDTH, GameStage.CANVAS_HEIGHT);
		return canvas;
	}

	private void displayInstructions(){
		gc.setFill(Color.BLACK);
		gc.setFont(Font.loadFont("file:src/assets/fonts/Notalot60.ttf", 50));
		gc.setTextBaseline(VPos.CENTER);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText("Hello", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}

	private VBox createVBox(){
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);

		Button returnToMain = new Button("Return to Main Menu");

		vbox.getChildren().add(returnToMain);

		returnToMain.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				Splash splashScreen = new Splash();
				splashScreen.setSplashStage(stage);
			}
		});

		return vbox;
	}
}

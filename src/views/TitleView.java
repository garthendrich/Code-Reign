package views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import main.Main;

public class TitleView extends View {

	@Override
	protected Scene createScene() {
		VBox root = new VBox();
		root.setBackground(new Background(new BackgroundFill(Color.valueOf("F6C27D"), null, null)));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(8);

		Button startButton = new Button("Start Game");
		Button instructionsButton = new Button("Instructions");
		Button aboutButton = new Button("About");

		root.getChildren().addAll(startButton, instructionsButton, aboutButton);

		startButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Stage stage = getStage();

					GameView gameStage = new GameView();
					gameStage.loadTo(stage);
				}
			}
		);

		instructionsButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Stage stage = getStage();

					InstructionsView instructionsView = new InstructionsView();
					instructionsView.loadTo(stage);
				}
			}
		);

		aboutButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Stage stage = getStage();

					AboutView aboutView = new AboutView();
					aboutView.loadTo(stage);
				}
			}
		);

		return new Scene(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
	}
}


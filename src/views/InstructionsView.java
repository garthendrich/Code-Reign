package views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import main.Main;

class InstructionsView extends View {

	@Override
	Scene createScene() {
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(8);

		Button backButton = new Button("Return to Main Menu");

		root.getChildren().add(backButton);

		backButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Stage stage = getStage();

					TitleView titleView = new TitleView();
					titleView.loadTo(stage);
				}
			}
		);

		return new Scene(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
	}
}

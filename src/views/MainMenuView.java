package views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenuView extends View {

	@Override
	protected Parent createRoot() {
		StackPane root = new StackPane();
		root.setBackground(new Background(new BackgroundFill(BG_COLOR, null, null)));
		root.setAlignment(Pos.TOP_CENTER);

		ImageView backgroundElements = new ImageView(new Image("assets/images/main_menu_bg_elements.png", 768, 290, false, false));
		backgroundElements.setFitWidth(View.WINDOW_WIDTH);
		backgroundElements.setPreserveRatio(true);

		VBox content = new VBox();
		content.setAlignment(Pos.CENTER);
		content.setSpacing(32);

		Text title = createThemedText("Code:Reign", 96, 4);

		VBox buttonsWrapper = new VBox();
		buttonsWrapper.setAlignment(Pos.CENTER);
		buttonsWrapper.setSpacing(8);

		Button startGameButton = createThemedButton("Start Game");
		Button instructionsButton = createThemedButton("Instructions");
		Button aboutButton = createThemedButton("About");

		startGameButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					GameView gameStage = new GameView();
					gameStage.loadTo(stage);
				}
			}
		);

		instructionsButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					InstructionsView instructionsView = new InstructionsView();
					instructionsView.loadTo(stage);
				}
			}
		);

		aboutButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					AboutView aboutView = new AboutView();
					aboutView.loadTo(stage);
				}
			}
		);

		buttonsWrapper.getChildren().addAll(startGameButton, instructionsButton, aboutButton);
		content.getChildren().addAll(title, buttonsWrapper);
		root.getChildren().addAll(backgroundElements, content);

		return root;
	}
}


package views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import main.Main;

public class TitleView extends View {

	@Override
	protected Scene createScene() {
		StackPane root = new StackPane();
		root.setBackground(new Background(new BackgroundFill(Color.valueOf("F6C27D"), null, null)));
		root.setAlignment(Pos.TOP_CENTER);

		ImageView backgroundElements = new ImageView(new Image("assets/images/main_menu_bg_elements.png", 768, 290, false, false));
		backgroundElements.setFitWidth(Main.WINDOW_WIDTH);
		backgroundElements.setPreserveRatio(true);

		VBox content = new VBox();
		content.setAlignment(Pos.CENTER);
		content.setSpacing(32);

		Text title = new Text("Code:Reign");
		title.setFont(Font.font(Main.NOTALOT60, 96));
		title.setFill(Color.WHITE);
		title.setStroke(Color.valueOf(Main.STROKE_COLOR));
		title.setStrokeWidth(4);

		VBox buttonsWrapper = new VBox();
		buttonsWrapper.setAlignment(Pos.CENTER);
		buttonsWrapper.setSpacing(8);

		Button startGameButton = createMenuButton("Start Game");
		Button instructionsButton = createMenuButton("Instructions");
		Button aboutButton = createMenuButton("About");

		buttonsWrapper.getChildren().addAll(startGameButton, instructionsButton, aboutButton);
		content.getChildren().addAll(title, buttonsWrapper);
		root.getChildren().addAll(backgroundElements, content);

		startGameButton.setOnMouseClicked(
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

	private Button createMenuButton(String text) {
		Button menuButton = new Button(text);
		menuButton.setMaxWidth(160);
		menuButton.setBorder(new Border(new BorderStroke(Color.valueOf(Main.STROKE_COLOR), BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		menuButton.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		menuButton.setPadding(new Insets(8));
		menuButton.setFont(Font.font(Main.NOTALOT60, 20));
		menuButton.setTextFill(Color.valueOf(Main.STROKE_COLOR));
		return menuButton;
	}
}


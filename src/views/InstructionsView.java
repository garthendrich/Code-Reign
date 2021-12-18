package views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import main.Main;

class InstructionsView extends View {

	@Override
	protected Scene createScene() {
		VBox root = new VBox();
		root.setBackground(new Background(new BackgroundFill(Color.valueOf("F6C27D"), null, null)));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(75);

		Text instructionsTitle = new Text();
		instructionsTitle.setText("Instructions");
		instructionsTitle.setFont(Font.font(Main.NOTALOT60, 50));
		instructionsTitle.setTextAlignment(TextAlignment.CENTER);
		instructionsTitle.setTextOrigin(VPos.TOP);

		Text instructions = new Text();
		instructions.setText("Press the arrow keys to move the Edolite. \n "
				+ "Press Spacebar to shoot. \n "
				+ "Avoid colliding with enemies. \n "
				+ "Pickup power-ups to help you survive. \n "
				+ "To win the game, you must survive for 60 seconds.");
		instructions.setFont(Font.font(Main.NOTALOT60, 30));
		instructions.setTextAlignment(TextAlignment.CENTER);
		instructions.setTextOrigin(VPos.CENTER);

		Button backButton = new Button("Return to Main Menu");
		backButton.setAlignment(Pos.BOTTOM_CENTER);

		root.getChildren().addAll(instructionsTitle, instructions, backButton);

		backButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					MainMenuView titleView = new MainMenuView();
					titleView.loadTo(stage);
				}
			}
		);

		return new Scene(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
	}
}

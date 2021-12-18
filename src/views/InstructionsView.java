package views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class InstructionsView extends View {

	@Override
	protected Parent createRoot() {
		VBox root = new VBox();
		root.setBackground(new Background(new BackgroundFill(Color.valueOf(View.BG_COLOR), null, null)));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(24);

		Text title = createThemedText("Invasion incoming!", 48, 2);

		String instructionsString = "As an Edolite, the destined hero of Arthane, you have to survive against the "
				+ "insufferable Orglits and their king, the Agmatron.\n\n"
				+ "Avoid getting hit by the enemies.\n"
				+ "Use the arrow keys to move and space bar to shoot.\n"
				+ "There are power-ups you can pick up to help you with your battle.\n"
				+ "Survive for 60 seconds to win the game.\n\n "
				+ "Defend the throne, Edolite! The fate of Arthane is in your hands.";
		Text instructions = createThemedText(instructionsString, 24, 1);
		instructions.setWrappingWidth(View.WINDOW_WIDTH);
		instructions.setTextAlignment(TextAlignment.CENTER);

		Button backButton = createThemedButton("Return to main menu");

		backButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					MainMenuView mainMenuView = new MainMenuView();
					mainMenuView.loadTo(stage);
				}
			}
		);

		root.getChildren().addAll(title, instructions, backButton);

		return root;
	}
}

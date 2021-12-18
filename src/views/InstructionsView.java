package views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import game.ElixirOfAeons;
import game.Gemstone;
import game.Hexcore;

class InstructionsView extends View {

	@Override
	protected Parent createRoot() {
		VBox root = new VBox();
		root.setBackground(new Background(new BackgroundFill(BG_COLOR, null, null)));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(12);

		Text title = createThemedText("Invasion incoming!", 32, 1);

		String shortStoryString =
				"As an Edolite, the destined hero of Arthane, you have to "
				+ "survive against the insufferable Orglits and their king, the Agmatron.";
		Text shortStory = createThemedText(shortStoryString, 24, 1);
		shortStory.setWrappingWidth(View.WINDOW_WIDTH);
		shortStory.setTextAlignment(TextAlignment.CENTER);

		String instructionsString =
				"Avoid getting hit by the enemies.\n"
				+ "Survive for 60 seconds to win the game.\n"
				+ "Use the arrow keys to move and space bar to shoot.\n"
				+ "There are power-ups you can pick up to help you with your battle:";
		Text instructions = createThemedText(instructionsString, 24, 1);
		instructions.setWrappingWidth(View.WINDOW_WIDTH);
		instructions.setTextAlignment(TextAlignment.CENTER);

		VBox powerUpsInfo = new VBox();
		powerUpsInfo.setAlignment(Pos.CENTER);
		Label hexcoreInfo = createPowerUpLabel("Hexcore: increases strength", Hexcore.IMAGE);
		Label elixirOfAeonsInfo = createPowerUpLabel("Elixir of Aeons: grants invulnerability", ElixirOfAeons.IMAGE);
		Label gemstoneInfo = createPowerUpLabel("Gemstone: increases bullets per shot", Gemstone.IMAGE);
		powerUpsInfo.getChildren().addAll(hexcoreInfo, elixirOfAeonsInfo, gemstoneInfo);

		Text closingMessage = createThemedText("Defend the throne, Edolite! The fate of Arthane is in your hands.", 24, 1);

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

		root.getChildren().addAll(title, shortStory, instructions, powerUpsInfo, closingMessage, backButton);

		return root;
	}

	private Label createPowerUpLabel(String textString, Image image) {
		Label label = new Label(textString, new ImageView(image));
		label.setFont(Font.font(View.NOTALOT60, 20));
		label.setTextFill(PRIMARY_COLOR);
		label.setGraphicTextGap(8);
		return label;
	}
}

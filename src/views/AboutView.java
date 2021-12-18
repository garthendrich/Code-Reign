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

class AboutView extends View {

	@Override
	protected Parent createRoot() {
		VBox root = new VBox();
		root.setBackground(new Background(new BackgroundFill(Color.valueOf(View.BG_COLOR), null, null)));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(16);

		Text header = createThemedText("About", 48, 2);

		Text devsHeader = createThemedText("Game Developers", 28, 1);

		String devsInfoString = "Alesundreau Dale Ratuiste and Garth Hendrich Lapitan\n"
				+ "Sophomores, BS Computer Science\n"
				+ "University of the Philippines - Los Banos";
		Text devsInfo = createThemedText(devsInfoString, 24, 1);
		devsInfo.setTextAlignment(TextAlignment.CENTER);

		Text referencesHeader = createThemedText("References", 28, 1);

		String referencesInfoString = "Guide code: CMSC 22 Everwing by Ma'am Miyah Queliste\n"
				+ "Sprite Images: by Penusbmic (penusbmic.itch.io)\n"
				+ "Pixel Font: Notalot60 by Chequered Ink (chequered.ink)";
		Text referencesInfo = createThemedText(referencesInfoString, 24, 1);
		referencesInfo.setTextAlignment(TextAlignment.CENTER);

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

		root.getChildren().addAll(header, devsHeader, devsInfo, referencesHeader, referencesInfo, backButton);

		return root;
	}
}

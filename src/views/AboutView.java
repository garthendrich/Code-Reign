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
import javafx.stage.Stage;

import main.Main;

class AboutView extends View {

	@Override
	protected Scene createScene() {
		VBox root = new VBox();
		root.setBackground(new Background(new BackgroundFill(Color.valueOf("F6C27D"), null, null)));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);

		Text aboutTitle = new Text();
		aboutTitle.setText("About");
		aboutTitle.setFont(Font.font(Main.NOTALOT60, 50));
		aboutTitle.setTextAlignment(TextAlignment.CENTER);
		aboutTitle.setTextOrigin(VPos.TOP);

		Text devsTitle = new Text();
		devsTitle.setText("Game Developers");
		devsTitle.setFont(Font.font(Main.NOTALOT60, 30));
		devsTitle.setTextAlignment(TextAlignment.CENTER);
		devsTitle.setTextOrigin(VPos.CENTER);

		Text aboutDevs = new Text();
		aboutDevs.setText("Garth Hendrich Lapitan \n "
				+ "2nd Year, BS Computer Science, UPLB \n\n "
				+ "Alesundreau Dale Ratuiste \n "
				+ "2nd Year, BS Computer Science, UPLB");
		aboutDevs.setFont(Font.font(Main.NOTALOT60, 20));
		aboutDevs.setTextAlignment(TextAlignment.CENTER);
		aboutDevs.setTextOrigin(VPos.CENTER);

		Text referencesTitle = new Text();
		referencesTitle.setText("References");
		referencesTitle.setFont(Font.font(Main.NOTALOT60, 30));
		referencesTitle.setTextAlignment(TextAlignment.CENTER);
		referencesTitle.setTextOrigin(VPos.CENTER);

		Text references = new Text();
		references.setText("Inspiration/Template: CMSC 22 Everwing \n"
				+ "Sprite Images: Penusbmic (penusbmic.itch.io) \n"
				+ "Power-up Images: Garth Hendrich Lapitan \n"
				+ "Pixel Font: Notalot60 by Chequered Ink (chequered.ink)");
		references.setFont(Font.font(Main.NOTALOT60, 20));
		references.setTextAlignment(TextAlignment.CENTER);
		references.setTextOrigin(VPos.CENTER);

		Button backButton = new Button("Return to Main Menu");
		backButton.setAlignment(Pos.BOTTOM_CENTER);

		root.getChildren().addAll(aboutTitle, devsTitle, aboutDevs, referencesTitle, references, backButton);

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

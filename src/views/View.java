package views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;

abstract class View {

	protected Stage stage;

	abstract protected Scene createScene();

	public void loadTo(Stage stage) {
		Scene scene = createScene();
		stage.setScene(scene);
		this.stage = stage;
	}

	protected Button createThemedButton(String text) {
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

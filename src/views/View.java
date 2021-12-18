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
import javafx.scene.text.Text;
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
		Button button = new Button(text);
		button.setMinWidth(160);
		button.setBorder(new Border(new BorderStroke(Color.valueOf(Main.STROKE_COLOR), BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		button.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		button.setPadding(new Insets(8, 16, 8, 16));
		button.setFont(Font.font(Main.NOTALOT60, 20));
		button.setTextFill(Color.valueOf(Main.STROKE_COLOR));
		return button;
	}

	protected Text createThemedText(String text, int size, int strokeWidth) {
		Text textNode = new Text(text);
		textNode.setFont(Font.font(Main.NOTALOT60, size));
		textNode.setFill(Color.WHITE);
		textNode.setStroke(Color.valueOf(Main.STROKE_COLOR));
		textNode.setStrokeWidth(strokeWidth);
		return textNode;
	}
}

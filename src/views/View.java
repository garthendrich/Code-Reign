package views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
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

public abstract class View {

	public static final int WINDOW_WIDTH = 750;
	public static final int WINDOW_HEIGHT = 500;
	public static final String NOTALOT60 = "Notalot60";
	public static final String BG_COLOR = "F6C27D";
	public static final String STROKE_COLOR = "634E32";

	protected Stage stage;
	protected Scene scene;

	abstract protected Parent createRoot();

	public void loadTo(Stage stage) {
		Parent root = createRoot();
		scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setScene(scene);
		this.stage = stage;
	}

	protected Button createThemedButton(String text) {
		Button button = new Button(text);
		button.setMinWidth(160);
		button.setBorder(new Border(new BorderStroke(Color.valueOf(View.STROKE_COLOR), BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		button.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		button.setPadding(new Insets(8, 16, 8, 16));
		button.setFont(Font.font(View.NOTALOT60, 20));
		button.setTextFill(Color.valueOf(View.STROKE_COLOR));
		return button;
	}

	protected Text createThemedText(String text, int size, int strokeWidth) {
		Text textNode = new Text(text);
		textNode.setFont(Font.font(View.NOTALOT60, size));
		textNode.setFill(Color.WHITE);
		textNode.setStroke(Color.valueOf(View.STROKE_COLOR));
		textNode.setStrokeWidth(strokeWidth);
		return textNode;
	}
}

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
	public static final Color BG_COLOR = Color.valueOf("F6C27D");
	public static final Color PRIMARY_COLOR = Color.valueOf("634E32");

	protected Stage stage;
	protected Scene scene;

	/**
	 * Creates a root node for the view.
	 *
	 * @return The root node
	 */
	abstract protected Parent createRoot();

	/**
	 * Creates a scene from a particular root node and sets it to the given stage.
	 *
	 * @param stage The stage where the scene will be set.
	 */
	public void loadTo(Stage stage) {
		Parent root = createRoot();
		scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setScene(scene);
		this.stage = stage;
	}

	/**
	 * Creates a designed Text node.
	 *
	 * @param text
	 * @param size
	 * @param strokeWidth
	 * @return The designed Text node
	 */
	protected Text createThemedText(String text, int size, int strokeWidth) {
		Text textNode = new Text(text);
		textNode.setFont(Font.font(View.NOTALOT60, size));
		textNode.setFill(Color.WHITE);
		textNode.setStroke(PRIMARY_COLOR);
		textNode.setStrokeWidth(strokeWidth);
		return textNode;
	}

	/**
	 * Creates a designed Button node.
	 *
	 * @param text
	 * @return The designed Button node
	 */
	protected Button createThemedButton(String text) {
		Button button = new Button(text);
		button.setMinWidth(160);
		button.setBorder(new Border(new BorderStroke(PRIMARY_COLOR, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		button.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		button.setPadding(new Insets(8, 16, 8, 16));
		button.setFont(Font.font(View.NOTALOT60, 20));
		button.setTextFill(PRIMARY_COLOR);
		return button;
	}
}
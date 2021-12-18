package views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
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

public class GameOverView extends View {

	private int edoliteStrength;
	private int orglitsKilled;

	public GameOverView(int edoliteStrength, int orglitsKilled) {
		this.edoliteStrength = edoliteStrength;
		this.orglitsKilled = orglitsKilled;
	}

	@Override
	protected Scene createScene() {
		VBox root = new VBox();
		root.setBackground(new Background(new BackgroundFill(Color.valueOf("F6C27D"), null, null)));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(50);

		Text outComeMessageText;

		if (edoliteStrength >= 0) outComeMessageText = new Text("CONGRATULATIONS! YOU WIN!");
		else outComeMessageText = new Text("GAME OVER! YOU LOSE!");

		outComeMessageText.setFont(Font.font(Main.NOTALOT60, 50));

		Text orglitsKilledText = new Text("Orglits killed: " + orglitsKilled);
		orglitsKilledText.setFont(Font.font(Main.NOTALOT60, 30));
		orglitsKilledText.setTextAlignment(TextAlignment.CENTER);

		Button backButton = new Button("Return to Main Menu");
		backButton.setAlignment(Pos.BOTTOM_CENTER);

		root.getChildren().addAll(outComeMessageText, orglitsKilledText, backButton);

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

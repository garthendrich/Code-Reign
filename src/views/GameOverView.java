package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
		root.setAlignment(Pos.CENTER);
		root.setSpacing(8);

		Text outComeMessageText;

		if (edoliteStrength >= 0) outComeMessageText = new Text("CONGRATULATIONS! YOU WIN!");
		else outComeMessageText = new Text("GAME OVER! YOU LOSE!");

		outComeMessageText.setFont(Font.font(Main.NOTALOT60, 48));

		Text orglitsKilledText = new Text("Orglits killed: " + orglitsKilled);
		orglitsKilledText.setFont(Font.font(Main.NOTALOT60, 32));

		root.getChildren().addAll(outComeMessageText, orglitsKilledText);

		return new Scene(root, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
	}

}

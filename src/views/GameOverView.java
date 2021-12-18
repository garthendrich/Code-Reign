package views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameOverView extends View {

	private int edoliteStrength;
	private int orglitsKilled;

	public GameOverView(int edoliteStrength, int orglitsKilled) {
		this.edoliteStrength = edoliteStrength;
		this.orglitsKilled = orglitsKilled;
	}

	@Override
	protected Parent createRoot() {
		VBox root = new VBox();
		root.setBackground(new Background(new BackgroundFill(BG_COLOR, null, null)));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(32);

		VBox gameResultWrapper = new VBox();
		gameResultWrapper.setAlignment(Pos.CENTER);
		gameResultWrapper.setSpacing(8);

		String outComeMessageString;
		if (edoliteStrength >= 0) outComeMessageString = "CONGRATULATIONS!\nYOU WIN!";
		else outComeMessageString = "GAME OVER! YOU LOSE!";

		Text outComeMessageText = createThemedText(outComeMessageString, 64, 2);
		outComeMessageText.setTextAlignment(TextAlignment.CENTER);

		Text orglitsKilledText = createThemedText("Orglits killed: " + orglitsKilled, 32, 1);

		VBox buttonsWrapper = new VBox();
		buttonsWrapper.setAlignment(Pos.CENTER);
		buttonsWrapper.setSpacing(8);

		Button newGameButton = createThemedButton("Play again");
		Button mainMenuButton = createThemedButton("Main menu");

		newGameButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					GameView gameStage = new GameView();
					gameStage.loadTo(stage);
				}
			}
		);

		mainMenuButton.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					MainMenuView mainMenuView = new MainMenuView();
					mainMenuView.loadTo(stage);
				}
			}
		);

		gameResultWrapper.getChildren().addAll(outComeMessageText, orglitsKilledText);
		buttonsWrapper.getChildren().addAll(newGameButton, mainMenuButton);
		root.getChildren().addAll(gameResultWrapper, buttonsWrapper);

		return root;
	}

}

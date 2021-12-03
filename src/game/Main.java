package game;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public void start(Stage stage){
		GameStage theGameStage = new GameStage();
		theGameStage.loadTo(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

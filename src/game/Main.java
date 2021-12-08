package game;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public void start(Stage stage){
		Splash splashScreen = new Splash();
		splashScreen.setSplashStage(stage);;
	}

	public static void main(String[] args) {
		launch(args);
	}
}

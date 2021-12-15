package main;

import javafx.application.Application;
import javafx.stage.Stage;

import views.Splash;

public class Main extends Application {

	public static final int WINDOW_WIDTH = 750;
	public static final int WINDOW_HEIGHT = 500;

	public void start(Stage stage){
		Splash splashScreen = new Splash();
		splashScreen.setSplashStage(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

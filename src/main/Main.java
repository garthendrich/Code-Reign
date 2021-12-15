package main;

import javafx.application.Application;
import javafx.stage.Stage;

import views.TitleView;

public class Main extends Application {

	public static final int WINDOW_WIDTH = 750;
	public static final int WINDOW_HEIGHT = 500;

	@Override
	public void start(Stage stage) {
		TitleView titleView = new TitleView();
		titleView.loadTo(stage);

		stage.setTitle("Code:Reign");
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

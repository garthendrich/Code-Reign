package main;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import views.MainMenuView;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		Font.loadFont("file:src/assets/fonts/Notalot60.ttf", 20);

		MainMenuView titleView = new MainMenuView();
		titleView.loadTo(stage);

		stage.getIcons().add(new Image("assets/images/icon.png", 64, 64, false, false));
		stage.setTitle("Code:Reign");
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

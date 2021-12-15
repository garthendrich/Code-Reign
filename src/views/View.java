package views;

import javafx.scene.Scene;
import javafx.stage.Stage;

abstract class View {

	private Scene scene;

	View() {
		scene = createScene();
	}

	abstract Scene createScene();

	public void loadTo(Stage stage) {
		stage.setScene(scene);
	}
}

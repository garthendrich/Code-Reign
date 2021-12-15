package views;

import javafx.scene.Scene;
import javafx.stage.Stage;

abstract class View {

	private Scene scene;
	private Stage stage;

	View() {
		scene = createScene();
	}

	abstract Scene createScene();

	public void loadTo(Stage stage) {
		stage.setScene(scene);
		this.stage = stage;
	}

	protected Stage getStage() {
		return stage;
	}
}

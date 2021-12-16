package views;

import javafx.scene.Scene;
import javafx.stage.Stage;

abstract class View {

	private Stage stage;

	abstract protected Scene createScene();

	public void loadTo(Stage stage) {
		Scene scene = createScene();
		stage.setScene(scene);
		this.stage = stage;
	}

	protected Stage getStage() {
		return stage;
	}
}

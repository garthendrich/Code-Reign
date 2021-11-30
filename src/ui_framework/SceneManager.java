package ui_framework;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
	private HashMap<String, View> routes = new HashMap<String, View>();
	private Stage stage;

	public SceneManager(Stage stage) {
		this.stage = stage;
	}

	public void addRoute(String path, View view) {
		routes.put(path, view);
	}

	public void load(String path) {
		View view = routes.get(path);

		Scene scene = view.getScene();
		stage.setScene(scene);

		view.start();
	}
}

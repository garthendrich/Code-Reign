package ui_framework;

import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class View {
    protected Scene scene;
	protected SceneManager sceneManager;

    View(SceneManager sceneManager) {
		this.sceneManager = sceneManager;

    	Parent root = createRoot();
    	scene = new Scene(root);
    }

    protected abstract Parent createRoot();
    public abstract void start();

    public Scene getScene() {
    	return scene;
    }
}
package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameTimer extends AnimationTimer{

	public static final int SPRITE_MOVING_DISTANCE = 10;
	public static final int ENEMY_SPAWN_COUNT = 3;

	private GraphicsContext graphicsContext;
	private Character character = new Character("Going merry", 150, 250);
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	GameTimer(GraphicsContext graphicsContext){
		this.graphicsContext = graphicsContext;
	}

	@Override
	public void handle(long currentNanoTime) {
	}
}

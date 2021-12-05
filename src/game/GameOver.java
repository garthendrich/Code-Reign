package game;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameOver {
	private StackPane pane;
	private Scene scene;
	private GraphicsContext gc;
	private Canvas canvas;

	GameOver(int state){
		this.pane = new StackPane();
		this.scene = new Scene(pane, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.CANVAS_WIDTH, GameStage.CANVAS_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.setProperties(state);
	}

	private void setProperties(int state){
		gc.setFill(new ImagePattern(null));
		gc.fillRect(0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		Font gameOverFont = Font.font("Impact", FontWeight.EXTRA_BOLD, 50);
		gc.setFont(gameOverFont);

		switch(state){
		case 1:
			gc.setFill(Color.RED);
			gc.fillText("Game Over!", GameStage.WINDOW_WIDTH * 0.3, GameStage.WINDOW_HEIGHT * 0.3);
			break;
		case 2:
			gc.setFill(Color.GREEN);
			gc.fillText("You Win!", GameStage.WINDOW_WIDTH * 0.3, GameStage.WINDOW_HEIGHT * 0.3);
			break;
		}

		Button restart = new Button("Restart");
		Button exit = new Button("Exit");
		exitEventHandler(exit);

		pane.getChildren().add(this.canvas);
		pane.getChildren().add(restart);
		pane.getChildren().add(exit);
	}

	private void exitEventHandler(Button btn){
		btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent arg0){
				System.exit(0);
			}
		});
	}

	Scene getGameOverScene(){
		return scene;
	}
}

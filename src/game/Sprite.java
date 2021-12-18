package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

abstract class Sprite {

	protected int xPos;
	protected int yPos;
	protected int width;
	protected int height;
	protected Image image;
	protected boolean isFacingRight = true;
	protected boolean isHidden = false;

	public Sprite(int xPos, int yPos, Image image) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.image = image;
		this.width = (int) image.getWidth();
		this.height = (int) image.getHeight();
	}

	//method that will check for collision of two sprites
	public boolean collidesWith(Sprite rect2)	{
		Rectangle2D rectangle1 = getHitBox();
		Rectangle2D rectangle2 = rect2.getHitBox();
		return rectangle1.intersects(rectangle2);
	}

	//method that will return the bounds of an image
	private Rectangle2D getHitBox(){
		return new Rectangle2D(xPos, yPos, width, height);
	}

	void render(GraphicsContext graphicsContext) {
		if (isFacingRight) {
			graphicsContext.drawImage(image, xPos, yPos);
		}
		else {
			graphicsContext.drawImage(image, 0, 0, width, height, xPos + width, yPos, -width, height);
		}
	}

	protected void faceLeft() {
		isFacingRight = false;
	}

	protected void faceRight() {
		isFacingRight = true;
	}

	void vanish() {
		isHidden = true;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	boolean isHidden() {
		return isHidden;
	}
}
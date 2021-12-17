package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

abstract class Sprite {
	protected Image image;
	protected int xPos, yPos, width, height;
	protected boolean isHorizontallyFlipped = false;

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
		if (!isHorizontallyFlipped) {
			graphicsContext.drawImage(image, xPos, yPos);
		}
		else {
			graphicsContext.drawImage(image, 0, 0, width, height, xPos + width, yPos, -width, height);
		}
	}

	protected void faceLeft() {
		isHorizontallyFlipped = true;
	}

	protected void faceRight() {
		isHorizontallyFlipped = false;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
}
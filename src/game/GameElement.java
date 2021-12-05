package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

abstract class GameElement {
	protected Image image;
	protected int xPos, yPos, width, height;

	public GameElement(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	protected void setImage(Image image) {
		this.image = image;
		this.width = (int) image.getWidth();
		this.height = (int) image.getHeight();
	}

	//method that will check for collision of two sprites
	public boolean collidesWith(GameElement rect2)	{
		Rectangle2D rectangle1 = getHitBox();
		Rectangle2D rectangle2 = rect2.getHitBox();
		return rectangle1.intersects(rectangle2);
	}
	//method that will return the bounds of an image
	private Rectangle2D getHitBox(){
		return new Rectangle2D(xPos, yPos, width, height);
	}

	//method to return the image
	Image getImage(){
		return image;
	}
	//getters
	public int getXPos() {
    	return xPos;
	}

	public int getYPos() {
    	return yPos;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
}
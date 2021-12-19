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

	Sprite(int xPos, int yPos, Image image) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.image = image;
		this.width = (int) image.getWidth();
		this.height = (int) image.getHeight();
	}

	/**
	 * Checks whether this sprite is colliding with another given sprite.
	 *
	 * @param anotherSprite
	 * @return true if both sprites collide, false otherwise.
	 */
	boolean isCollidingWith(Sprite anotherSprite) {
		Rectangle2D thisSpriteHitbox = getHitBox();
		Rectangle2D anotherSpriteHitbox = anotherSprite.getHitBox();
		return thisSpriteHitbox.intersects(anotherSpriteHitbox);
	}

	private Rectangle2D getHitBox() {
		return new Rectangle2D(xPos, yPos, width, height);
	}

	/**
	 * Renders the sprite's image to the graphics context's canvas
	 *
	 * The image will be rendered normally if the sprite is facing to the right. Otherwise, the
	 * image will be rendered horizontally flipped if the sprite is facing to the left.
	 *
	 * @param graphicsContext The graphics context of the game canvas
	 */
	void render(GraphicsContext graphicsContext) {
		if (isFacingRight) {
			graphicsContext.drawImage(image, xPos, yPos);
		}
		else {
			graphicsContext.drawImage(image, xPos + width, yPos, -width, height);
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

	int getWidth() {
		return width;
	}

	int getHeight() {
		return height;
	}

	boolean isHidden() {
		return isHidden;
	}
}
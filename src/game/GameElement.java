package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameElement {
	protected Image img;
	protected int x, y, dx, dy;
	protected boolean visible;
	protected double width;
	protected double height;

	public GameElement(int xPos, int yPos){
		x = xPos;
		y = yPos;
		visible = true;
	}

	//method to set the object's image
	protected void loadImage(Image img){
		try{
	        setSize();
		} catch(Exception e){}
	}

	//method to set the image to the image view node
	void render(GraphicsContext gc){
		gc.drawImage(img, x, y);

    }

	//method to set the object's width and height properties
	private void setSize(){
		width = img.getWidth();
	    height = img.getHeight();
	}
	//method that will check for collision of two sprites
	public boolean collidesWith(GameElement rect2)	{
		Rectangle2D rectangle1 = getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2);
	}
	//method that will return the bounds of an image
	private Rectangle2D getBounds(){
		return new Rectangle2D(x, y, width, height);
	}

	//method to return the image
	Image getImage(){
		return img;
	}
	//getters
	public int getX() {
    	return x;
	}

	public int getY() {
    	return y;
	}

	public boolean getVisible(){
		return visible;
	}
	public boolean isVisible(){
		if(visible) return true;
		return false;
	}

	//setters
	public void setDX(int dx){
		this.dx = dx;
	}

	public void setDY(int dy){
		this.dy = dy;
	}

	public void setWidth(double val){
		width = val;
	}

	public void setHeight(double val){
		height = val;
	}

	public void setVisible(boolean value){
		visible = value;
	}
}
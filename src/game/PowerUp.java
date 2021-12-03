package game;

abstract class PowerUp extends GameElement{

	public PowerUp(int xPos, int yPos) {
		super(xPos, yPos);
	}

	abstract void checkCollision(Character character);
}

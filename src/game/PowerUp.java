package game;

abstract class PowerUp extends GameElement {

	public final static int SIZE = 24;

	public PowerUp(int x, int y) {
		super(x, y);
	}

	abstract void applyTo(Edolite edolite);
}

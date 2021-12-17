package game;

class StatusEffect extends Thread {

    public static final String INVULNERABILITY = "invulnerability";
    public static final String WARRIORS_FURY = "warrior's fury";

    private String type;
    private double durationSeconds;
    private Edolite affectedEdolite;

    StatusEffect(String type, double durationSeconds, Edolite affectedEdolite) {
        this.type = type;
        this.durationSeconds = durationSeconds;
        this.affectedEdolite = affectedEdolite;
    }

    @Override
	public void run() {
		try {
			Thread.sleep((long) (1000 * durationSeconds));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		affectedEdolite.removeStatusEffect(this);
	}

	boolean isOfType(String typeToCompare) {
		return (this.type == typeToCompare);
	}
}

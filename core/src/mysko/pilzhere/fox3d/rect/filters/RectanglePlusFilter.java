package mysko.pilzhere.fox3d.rect.filters;

public enum RectanglePlusFilter {
	NONE(-1), WALL(1), DOOR(2), PLAYER(3), ENEMY(4), ENEMY_PROJECTILE(5);

	private final int value;

	private RectanglePlusFilter(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

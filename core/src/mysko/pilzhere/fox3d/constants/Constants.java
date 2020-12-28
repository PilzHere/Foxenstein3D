package mysko.pilzhere.fox3d.constants;

public abstract class Constants {
	public static final float PPU = 1f / 16f;
	public static final float TILE_SIZE = 16f;
	public static final float HALF_UNIT = 0.5f;

	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 480;

	public static final int VIEWPORT_WIDTH = 640;
	public static final int VIEWPORT_HEIGHT = 480;

	public static final int FBO_WIDTH_ORIGINAL = VIEWPORT_WIDTH / 4; // 160
	public static final int FBO_HEIGHT_ORIGINAL = VIEWPORT_HEIGHT / 4; // 120

	public static final int FBO_WIDTH_DECENT = VIEWPORT_WIDTH / 2;
	public static final int FBO_HEIGHT_DECENT = VIEWPORT_HEIGHT / 2;

	public static final int FBO_WIDTH_DELUXE = VIEWPORT_WIDTH;
	public static final int FBO_HEIGHT_DELUXE = VIEWPORT_HEIGHT;
}

package mysko.pilzhere.fox3d.constants;

public abstract class Constants {
	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 1;
	public static final long VERSION_REVISION = 010120212334L;
	public static final String VERSION = VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_REVISION;

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

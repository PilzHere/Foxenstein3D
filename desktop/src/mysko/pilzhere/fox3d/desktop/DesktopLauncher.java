package mysko.pilzhere.fox3d.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

import mysko.pilzhere.fox3d.Foxenstein3D;

public class DesktopLauncher {

//	TODO Decide genre of game: One floor, First Person. Think Wolf3D.
//	TODO Decide window/viewport resolution: 640x480px. FBO is 160*120.
//	TODO Decide Wall/floor/ceiling width*height: 16^2 or 32^2.
//	TODO Color Palette: Pico-8 32 colors. OR no limit.
//	TODO Decide angles for enemies: Preferable 1. Else 2; front, back. Sides only if needed.
//	TODO List of enemies that should be simpler to draw:
//	Bat, Floating Eye, Ghost, Bird, Skeleton head...
//	TODO Ghosts can move thorugh doors. Maybe walls too?
//	TODO Implement enemies from list.
//	TODO Weapons: Staff? Cast fire, ice and lighting?
//	TODO Find fitting music.
//	TODO Implement fitting music.
//	TODO AI: Record player positions every 3rd of a second.

//	DONE Nothing yet.

//	TODO After publish --------------------------------------------------
//	FIXME Change title to new title.

	public static void main(final String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Ultra Nightmare";
		config.x = 0; // Is this needed on Windows too?
		config.y = 0;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60; // change to 60 later
		config.fullscreen = false;
		config.width = 640;
		config.height = 480;
		config.initialBackgroundColor = new Color(66 / 256f, 33 / 256f, 54 / 256f, 1f);
		config.vSyncEnabled = false;
		config.useGL30 = false; // maybe?
		config.samples = 0;
		config.resizable = true;
//		config.addIcon(path, fileType);
		config.allowSoftwareMode = true;
		config.useGL30 = true;

		new LwjglApplication(new Foxenstein3D(), config);
	}
}

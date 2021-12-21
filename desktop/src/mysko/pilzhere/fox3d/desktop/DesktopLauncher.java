package mysko.pilzhere.fox3d.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
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
        final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("Ultra Nightmare");
//		config.x = 0; // I need this in Linux to center screen. Comment for release.
//		config.y = 0;
		//config.bacbackgroundFPS = 60;
		config.setForegroundFPS(60);//foregroundFPS = 60; // change to 60 later
		//config.setFullscreenMode();fullscreen = false;
		config.setWindowSizeLimits(640,480,640, 480);
		config.setInitialBackgroundColor(new Color(66 / 256f, 33 / 256f, 54 / 256f, 1f));//initialBackgroundColor = ;
		//config.vSyncEnabled = false;
		//config.useOpenGL3();//useGL30 = false; // maybe?
		//config.sasamples = 0;
		config.setResizable(true);//resizable = true;
//		config.addIcon(path, fileType);
		//config.allowSoftwareMode = true;
		//config.forceExit = false;

		new Lwjgl3Application(new Foxenstein3D(), config);
//		new LwjglApplication(new Foxenstein3D(), config);
	}
}

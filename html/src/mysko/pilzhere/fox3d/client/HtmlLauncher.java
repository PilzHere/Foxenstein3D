package mysko.pilzhere.fox3d.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import mysko.pilzhere.fox3d.Foxenstein3D;

public class HtmlLauncher extends GwtApplication {

	@Override
	public ApplicationListener createApplicationListener() {
		return new Foxenstein3D();
	}

	@Override
	public GwtApplicationConfiguration getConfig() {
		// Resizable application, uses available space in browser
		// return new GwtApplicationConfiguration(true);
		// Fixed size application:
		return new GwtApplicationConfiguration(640, 480); // 480, 320
	}
}
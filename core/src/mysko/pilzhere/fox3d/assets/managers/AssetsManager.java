package mysko.pilzhere.fox3d.assets.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetsManager extends AssetManager {
	public final String atlas01 = "textures/atlas01.png";
	public final String bgSky01 = "textures/bgSky01.png";

	public final String map01 = "maps/map01.tmx";

	public AssetsManager() {
		loadTextures();
		loadMaps();
	}

	public void loadMaps() {
		setLoader(TiledMap.class, new TmxMapLoader()); // Tile atlas should be in same folder.

		load(map01, TiledMap.class);
	}

	public void loadTextures() {
		load(atlas01, Texture.class);
		load(bgSky01, Texture.class);
	}
}

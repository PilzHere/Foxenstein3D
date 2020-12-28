package mysko.pilzhere.fox3d.assets.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetsManager extends AssetManager {
	public final String atlas01 = "textures/atlas01.png";
	public final String enemies = "textures/enemies.png";
	public final String bgSky01 = "textures/bgSky01.png";
	public final String guiBG = "textures/guiBG.png";
	public final String guiGun = "textures/gun.png";
	public final String guiTitle = "textures/title.png";

	public final String map01 = "maps/map01.tmx";
	public final String mapTitle = "maps/mapTitle.tmx";

	public final String font03_64 = "fonts/font03_64.fnt";
	public final String font03_32 = "fonts/font03_32.fnt";

	public final String sfxDoorOpening = "sound/sfx/doorOpening.mp3";
	public final String sfxAmbientDark = "sound/sfx/ambientDark.wav";
	public final String sfxItem = "sound/sfx/item.wav";

	public final String musicBackground01 = "sound/music/Biological-Weapon.ogg";

	public AssetsManager() {
		loadTextures();
		loadSounds();
		loadFonts();
		loadMaps();
	}

	public void loadFonts() {
		final FileHandleResolver resolver = new InternalFileHandleResolver();
		setLoader(BitmapFont.class, new BitmapFontLoader(resolver)); // Tile atlas should be in same folder.

		load(font03_64, BitmapFont.class);
		load(font03_32, BitmapFont.class);
	}

	public void loadMaps() {
		setLoader(TiledMap.class, new TmxMapLoader()); // Tile atlas should be in same folder.

		load(mapTitle, TiledMap.class);
		load(map01, TiledMap.class);
	}

	public void loadSounds() {
//		sfx
		load(sfxDoorOpening, Sound.class);
		load(sfxAmbientDark, Sound.class);
		load(sfxItem, Sound.class);

//		music
		load(musicBackground01, Sound.class);
	}

	public void loadTextures() {

		load(atlas01, Texture.class);
		load(bgSky01, Texture.class);
		load(enemies, Texture.class);
		load(guiBG, Texture.class);
		load(guiGun, Texture.class);
		load(guiTitle, Texture.class);
	}
}

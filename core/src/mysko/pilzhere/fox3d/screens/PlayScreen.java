package mysko.pilzhere.fox3d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mysko.pilzhere.fox3d.Foxenstein3D;
import mysko.pilzhere.fox3d.constants.Constants;
import mysko.pilzhere.fox3d.entities.player.Player;

public class PlayScreen extends GameScreen {
	private final TextureRegion skyBg;
	private final TextureRegion guiBG;
	private final TextureRegion guiGun;

	private final Environment env;

	private final Color fogColor;

	public PlayScreen(final Foxenstein3D game) {
		super(game);

		viewport = new StretchViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 1, 1f));
		fogColor = new Color(66 / 256f, 33 / 256f, 54 / 256f, 1f);
		env.set(new ColorAttribute(ColorAttribute.Fog, fogColor));

		skyBg = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().bgSky01));
		skyBg.flip(false, true);
		guiBG = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().guiBG), 0, 0, 160, 16);
		guiGun = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().guiGun));

		game.getMapBuilder().buildMap((TiledMap) game.getAssMan().get(game.getAssMan().map01));

//		game.getEntMan().addEntity(new Grid(this));

		player = new Player(this);
		game.getEntMan().addEntity(player);

		currentCam = player.playerCam;
		viewport.setCamera(currentCam);

		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void handleInput(final float delta) {
		super.handleInput(delta);

		player.handleInput(delta);

		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			game.getEntMan().entities.get(game.getEntMan().entities.size - 1).setDestroy(true);
			game.getEntMan().entities.get(game.getEntMan().entities.size - 1).destroy();
		}
	}

	@Override
	public void render(final float delta) {
		super.render(delta);

		currentCam.update();

		game.getFbo().begin();
		Gdx.gl.glClearColor(fogColor.r, fogColor.g, fogColor.b, fogColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

//		game.getBatch().getProjectionMatrix().setToOrtho2D(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		game.getBatch().begin();
		game.getBatch().draw(skyBg, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		game.getBatch().end();

//		game.getEntMan().render2DAllEntities(delta);
		game.getMdlBatch().begin(currentCam);
		game.getEntMan().render3DAllEntities(game.getMdlBatch(), env, delta);
		game.getMdlBatch().end();
		game.getFbo().end();

//		render final fbo
		game.getBatch().begin();
		game.getBatch().draw(game.getFbo().getColorBufferTexture(), 0, 0, viewport.getWorldWidth(),
				viewport.getWorldHeight());
//		gui
		game.getBatch().draw(guiGun, viewport.getWorldWidth() / 2f - 7.5f * 8f, 0, 7.5f * 16f, 7.5f * 32f);
		game.getBatch().draw(guiBG, 0, 0, viewport.getWorldWidth(), 7.5f * 8f);
		game.getBatch().end();
	}

	@Override
	public void resize(final int width, final int height) {
		super.resize(width, height);
	}

	@Override
	public void tick(final float delta) {
		super.tick(delta);
	}

}

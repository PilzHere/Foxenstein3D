package mysko.pilzhere.fox3d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import mysko.pilzhere.fox3d.assets.managers.AssetsManager;
import mysko.pilzhere.fox3d.constants.Constants;
import mysko.pilzhere.fox3d.filters.OverlapFilterManager;
import mysko.pilzhere.fox3d.maps.MapBuilder;
import mysko.pilzhere.fox3d.models.ModelMaker;
import mysko.pilzhere.fox3d.rect.RectManager;
import mysko.pilzhere.fox3d.screens.MainMenuScreen;
import mysko.pilzhere.fox3d.utils.EntityManager;

public class Foxenstein3D extends Game {
	private SpriteBatch batch;

	private ModelBatch mdlBatch;

	private FrameBuffer fbo, fboPlayerHit;
	public TextureRegion texRegPlayerHitOverlay;
	private AssetsManager assMan;

	private EntityManager entMan;

	private RectManager rectMan;
	private ModelMaker cellBuilder;
	private OverlapFilterManager overlapFilterMan;

	private MapBuilder mapBuilder;

	public boolean gameIsPaused = false;

	private float timeSinceLaunch = 0;

	private float currentAmbientVolume = 0.1f;

	private float currentSfxVolume = 0.25f;
	private float currentMusicVolume = 0.05f;

	@Override
	public void create() {
		batch = new SpriteBatch();
		mdlBatch = new ModelBatch();
		createNewMainFbo(Constants.FBO_WIDTH_ORIGINAL, Constants.FBO_HEIGHT_ORIGINAL);
		fboPlayerHit = createNewFbo(Format.RGBA8888, 2, 2, false);
		texRegPlayerHitOverlay = new TextureRegion(fboPlayerHit.getColorBufferTexture(), 0, 0, 2, 2);

		assMan = new AssetsManager();
		assMan.finishLoading();

		overlapFilterMan = new OverlapFilterManager();

		cellBuilder = new ModelMaker(this); // builds models...

		entMan = new EntityManager();
		rectMan = new RectManager(this);

		mapBuilder = new MapBuilder(this);

//		setScreen(new PlayScreen(this));
		setScreen(new MainMenuScreen(this));
	}

	private FrameBuffer createNewFbo(final Format format, final int width, final int height, final boolean hasDepth) {
		final FrameBuffer fbo = new FrameBuffer(format, width, height, hasDepth);
		fbo.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		return fbo;
	}

	public void createNewMainFbo(final int width, final int height) {
		fbo = new FrameBuffer(Format.RGB888, width, height, true);
		fbo.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}

	@Override
	public void dispose() {
		getScreen().dispose();

		batch.dispose();
		mdlBatch.dispose();
		fboPlayerHit.dispose();
		fbo.dispose();

		assMan.dispose();
	}

	public float getAmbientVolume() {
		return currentAmbientVolume;
	}

	public AssetsManager getAssMan() {
		return assMan;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public ModelMaker getCellBuilder() {
		return cellBuilder;
	}

	public EntityManager getEntMan() {
		return entMan;
	}

	public FrameBuffer getFbo() {
		return fbo;
	}

	public MapBuilder getMapBuilder() {
		return mapBuilder;
	}

	public ModelBatch getMdlBatch() {
		return mdlBatch;
	}

	public float getMusicVolume() {
		return currentMusicVolume;
	}

	public OverlapFilterManager getOverlapFilterMan() {
		return overlapFilterMan;
	}

	public RectManager getRectMan() {
		return rectMan;
	}

	public float getSfxVolume() {
		return currentSfxVolume;
	}

	public float getTimeSinceLaunch() {
		return timeSinceLaunch;
	}

	@Override
	public void render() {
		timeSinceLaunch += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		getScreen().render(Gdx.graphics.getDeltaTime());
	}

	public void setAmbientVolume(final float currentAmbientVolume) {
		this.currentAmbientVolume = currentAmbientVolume;
	}

	public void setMusicVolume(final float newMusicVolume) {
		this.currentMusicVolume = newMusicVolume;
	}

	public void setSfxVolume(final float currentSfxVolume) {
		this.currentSfxVolume = currentSfxVolume;
	}
}

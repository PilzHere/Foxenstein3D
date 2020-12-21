package mysko.pilzhere.fox3d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import mysko.pilzhere.fox3d.assets.managers.AssetsManager;
import mysko.pilzhere.fox3d.cellbuilder.Cell3DBuilder;
import mysko.pilzhere.fox3d.maps.MapBuilder;
import mysko.pilzhere.fox3d.screens.PlayScreen;
import mysko.pilzhere.fox3d.utils.EntityManager;

public class Foxenstein3D extends Game {
	private SpriteBatch batch;

	private ModelBatch mdlBatch;

	private FrameBuffer fbo;

	private AssetsManager assMan;
	private EntityManager entMan;

	private Cell3DBuilder cellBuilder;

	private MapBuilder mapBuilder;

	@Override
	public void create() {
		batch = new SpriteBatch();
		mdlBatch = new ModelBatch();
		fbo = new FrameBuffer(Format.RGB888, 640 / 2, 480 / 2, true); // should be / 4
		fbo.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		assMan = new AssetsManager();
		assMan.finishLoading();

		cellBuilder = new Cell3DBuilder(this); // builds models...

		entMan = new EntityManager();

		mapBuilder = new MapBuilder(this);

		setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose() {
		getScreen().dispose();

		batch.dispose();
		mdlBatch.dispose();
		fbo.dispose();

		assMan.dispose();
	}

	public AssetsManager getAssMan() {
		return assMan;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public Cell3DBuilder getCellBuilder() {
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

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		getScreen().render(Gdx.graphics.getDeltaTime());
	}
}

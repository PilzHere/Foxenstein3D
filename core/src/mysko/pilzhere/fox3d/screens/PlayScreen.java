package mysko.pilzhere.fox3d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mysko.pilzhere.fox3d.Foxenstein3D;

public class PlayScreen extends GameScreen {

	private final TextureRegion tempBg;
	private final Sprite tempSpriteBg;

	public PlayScreen(final Foxenstein3D game) {
		super(game);

		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		currentCam = new PerspectiveCamera(640, 480, 70); // currentCam should be null, only to be set when player
															// spawns (players cam). photo mode? <-- flying cam
		currentCam.position.set(new Vector3(0, 0.5f, 0));
		currentCam.lookAt(new Vector3(0, 0.5f, -1));
		currentCam.near = 0.01f;
		currentCam.far = 100f;
		currentCam.update();
		viewport.setCamera(currentCam);

		tempBg = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().bgSky01));
		tempSpriteBg = new Sprite(tempBg);
		tempSpriteBg.flip(false, true);

		game.getMapBuilder().buildMap((TiledMap) game.getAssMan().get(game.getAssMan().map01));

//		game.getEntMan().addEntity(new Grid(this));
		// game.getEntMan().addEntity(new Cell(new Vector3(0, 0, 0), this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void handleInput(final float delta) {
		super.handleInput(delta);

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			currentCam.translate(0, 0, -10f * delta);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			currentCam.translate(0, 0, 10f * delta);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			currentCam.translate(10f * delta, 0, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			currentCam.translate(-10f * delta, 0, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			currentCam.rotate(Vector3.Y, 133f * delta);
//			currentCam.translate(0, -10f * delta, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			currentCam.rotate(Vector3.Y, -133f * delta);
//			currentCam.translate(0, 10f * delta, 0);
		}
	}

	@Override
	public void render(final float delta) {
		super.render(delta);

		currentCam.update();

		game.getFbo().begin();
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		game.getBatch().getProjectionMatrix().setToOrtho2D(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		game.getBatch().begin();
		tempSpriteBg.draw(game.getBatch());
		game.getBatch().end();

//		game.getEntMan().render2DAllEntities(delta);
		game.getMdlBatch().begin(currentCam);
		game.getEntMan().render3DAllEntities(game.getMdlBatch(), delta);
		game.getMdlBatch().end();
		game.getFbo().end();

//		render final fbo
		game.getBatch().begin();
		game.getBatch().draw(game.getFbo().getColorBufferTexture(), 0, 0, viewport.getWorldWidth(),
				viewport.getWorldHeight());
		game.getBatch().end();
	}

	@Override
	public void resize(final int width, final int height) {
		super.resize(width, height);
	}

	@Override
	public void tick(final float delta) {
		super.tick(delta);

//		tick here
	}

}

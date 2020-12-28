package mysko.pilzhere.fox3d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mysko.pilzhere.fox3d.Foxenstein3D;
import mysko.pilzhere.fox3d.constants.Constants;

public class MainMenuScreen extends GameScreen {

	private final TextureRegion skyBg;
//	private final TextureRegion guiBG;
//	private final TextureRegion guiGun;
	private final TextureRegion guiTitle;
	private final BitmapFont guiFont01_64;
	private final BitmapFont guiFont01_32;
	private final String optionStartGame = "START GAME";
	private final String optionQuitGame = "QUIT GAME";
	private final String optionChangeRenderQuality = "CHANGE RENDER QUAILITY";
	private final String optionDisplayControls = "DISPLAY CONTROLS";
	private final String infoControls01 = "MOVE: W-A-S-D";
	private final String infoControls02 = "ROTATE: MOUSE";
	private final String infoControls03 = "SHOOT: LEFT MOUSE BUTTON";
	private final String infoControls04 = "USE: E  INVENTORY: 1->6";
	private final String infoControls05 = "SPACE/ENTER/ESCAPE GO BACK";
	private final String infoMadeBy = "MADE BY CHRISTIAN \"PILZHERE\" PILZ * LIBGDX JAM 15 2020";
	private final String infoMusicBy = "MUSIC FROM PATRICKDEARTEAGA.COM";
	private final String infoSource = "SOURCECODE: GITHUB.COM/PILZHERE/FOXENSTEIN3D";
	private final String selectedOptionMark = ">";
	private final GlyphLayout glyphLayoutOptionStartGame;
	private final GlyphLayout glyphLayoutOptionQuitGame;
	private final GlyphLayout glyphLayoutOptionDisplayControls;
	private final GlyphLayout glyphLayoutChangeRenderQuality;
	private final GlyphLayout glyphLayoutInfoSource;
	private final GlyphLayout glyphLayoutInfoMusic;
	private final GlyphLayout glyphLayoutInfoControls1;
	private final GlyphLayout glyphLayoutInfoControls2;
	private final GlyphLayout glyphLayoutInfoControls3;
	private final GlyphLayout glyphLayoutInfoControls4;
	private final GlyphLayout glyphLayoutInfoControls5;
	private int selectedOption = 0;
	private int fboOption = 0;
	private boolean displayControls = false;

	private final Environment env;

	private final Color fogColor;

	private final Sound sfxAmbient;
	private final long sfxAmbientId;
	private final Sound sfxChoice;
	private long sfxChoiceId;

	public MainMenuScreen(final Foxenstein3D game) {
		super(game);

		viewport = new StretchViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 1, 1f));
		fogColor = new Color(66 / 256f, 33 / 256f, 54 / 256f, 1f);
		env.set(new ColorAttribute(ColorAttribute.Fog, fogColor));

		skyBg = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().bgSky01));
		skyBg.flip(false, true);
//		guiBG = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().guiBG), 0, 0, 160, 16);
//		guiGun = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().guiGun));
		guiTitle = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().guiTitle));
		guiFont01_64 = game.getAssMan().get(game.getAssMan().font03_64);
		guiFont01_32 = game.getAssMan().get(game.getAssMan().font03_32);

		glyphLayoutOptionStartGame = new GlyphLayout(guiFont01_64, optionStartGame);
		glyphLayoutOptionQuitGame = new GlyphLayout(guiFont01_64, optionQuitGame);
		glyphLayoutChangeRenderQuality = new GlyphLayout(guiFont01_64, optionChangeRenderQuality);
		glyphLayoutOptionDisplayControls = new GlyphLayout(guiFont01_64, optionDisplayControls);
		glyphLayoutInfoSource = new GlyphLayout(guiFont01_32, infoSource);
		glyphLayoutInfoMusic = new GlyphLayout(guiFont01_32, infoMusicBy);
		glyphLayoutInfoControls1 = new GlyphLayout(guiFont01_64, infoControls01);
		glyphLayoutInfoControls2 = new GlyphLayout(guiFont01_64, infoControls02);
		glyphLayoutInfoControls3 = new GlyphLayout(guiFont01_64, infoControls03);
		glyphLayoutInfoControls4 = new GlyphLayout(guiFont01_64, infoControls04);
		glyphLayoutInfoControls5 = new GlyphLayout(guiFont01_64, infoControls05);

		sfxAmbient = game.getAssMan().get(game.getAssMan().sfxAmbientDark);
		sfxChoice = game.getAssMan().get(game.getAssMan().sfxItem);

		game.getMapBuilder().buildMap((TiledMap) game.getAssMan().get(game.getAssMan().mapTitle));

		currentCam = new PerspectiveCamera(70, 640, 480);
		currentCam.position.set(new Vector3(0, 0.5f, 0));
		currentCam.lookAt(new Vector3(0, 0.5f, 1));
		currentCam.near = 0.01f;
		currentCam.far = 10f;
		currentCam.update();
		currentCam.position.set(game.getMapBuilder().mapLoadSpawnPosition.x + 0.5f, 0.5f,
				game.getMapBuilder().mapLoadSpawnPosition.y + 0.5f);

		viewport.setCamera(currentCam);

		Gdx.input.setCursorCatched(true);

		sfxAmbientId = sfxAmbient.play(game.getAmbientVolume());
		sfxAmbient.setLooping(sfxAmbientId, true);
	}

	@Override
	public void handleInput(final float delta) {
		super.handleInput(delta);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if (!displayControls) {
				switch (selectedOption) {
				case 0:
					sfxAmbient.stop(sfxAmbientId);

					removeAllEntities();

					game.setScreen(new PlayScreen(game));
					break;
				case 1:
					displayControls = !displayControls;
					break;
				case 2:
					fboOption++;
					limitFboOption();

					switch (fboOption) {
					case 0:
						game.createNewFbo(Constants.FBO_WIDTH_ORIGINAL, Constants.FBO_HEIGHT_ORIGINAL);
						break;
					case 1:
						game.createNewFbo(Constants.FBO_WIDTH_DECENT, Constants.FBO_HEIGHT_DECENT);
						break;
					case 2:
						game.createNewFbo(Constants.FBO_WIDTH_DELUXE, Constants.FBO_HEIGHT_DELUXE);
						break;
					default:
						game.createNewFbo(Constants.FBO_WIDTH_ORIGINAL, Constants.FBO_HEIGHT_ORIGINAL);
						break;
					}
					break;
				case 3:
					Gdx.app.exit();
					break;
				default:
					sfxAmbient.stop(sfxAmbientId);

					removeAllEntities();

					game.setScreen(new PlayScreen(game));
					break;
				}
			} else {
				displayControls = false;
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			if (!displayControls) {
				Gdx.app.exit();
			} else {
				displayControls = false;
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if (!displayControls) {
				selectedOption--;
				limitSelectedOption();

				sfxChoiceId = sfxChoice.play(game.getSfxVolume());
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if (!displayControls) {
				selectedOption++;
				limitSelectedOption();

				sfxChoiceId = sfxChoice.play(game.getSfxVolume());
			}
		}
	}

	private void limitFboOption() {
		if (fboOption > 2) {
			fboOption = 0;
		} else if (selectedOption < 0) {
			selectedOption = 0;
		}
	}

	private void limitSelectedOption() {
		if (selectedOption < 0) {
			selectedOption = 3;
		} else if (selectedOption > 3) {
			selectedOption = 0;
		}
	}

	@Override
	public void render(final float delta) {
		super.render(delta);

		currentCam.rotate(Vector3.Y, -6f * delta);

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
//		game.getBatch().draw(guiGun, viewport.getWorldWidth() / 2f - 7.5f * 8f, 0, 7.5f * 16f, 7.5f * 32f);
//		game.getBatch().draw(guiBG, 0, 0, viewport.getWorldWidth(), 7.5f * 8f);
		game.getBatch().draw(guiTitle, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

		if (!displayControls) {
			final float newGameX = viewport.getWorldWidth() / 2f - glyphLayoutOptionStartGame.width / 2f;
			guiFont01_64.draw(game.getBatch(), optionStartGame, newGameX, 188);

			final float displayControlsX = viewport.getWorldWidth() / 2f - glyphLayoutOptionDisplayControls.width / 2f;
			guiFont01_64.draw(game.getBatch(), optionDisplayControls, displayControlsX, 188 - 32);

			final float changeRenderQualityX = viewport.getWorldWidth() / 2f
					- glyphLayoutChangeRenderQuality.width / 2f;
			guiFont01_64.draw(game.getBatch(), optionChangeRenderQuality, changeRenderQualityX, 188 - 64);

			final float quitGameX = viewport.getWorldWidth() / 2f - glyphLayoutOptionQuitGame.width / 2f;
			guiFont01_64.draw(game.getBatch(), optionQuitGame, quitGameX, 188 - 96);

			guiFont01_32.draw(game.getBatch(), infoMadeBy, 4, 48);
			final float infoMusicX = viewport.getWorldWidth() / 2f - glyphLayoutInfoMusic.width / 2f;
			guiFont01_32.draw(game.getBatch(), infoMusicBy, infoMusicX, 32);

			final float infoSourceX = viewport.getWorldWidth() / 2f - glyphLayoutInfoSource.width / 2f;
			guiFont01_32.draw(game.getBatch(), infoSource, infoSourceX, 16);

			switch (selectedOption) {
			case 0:
				guiFont01_64.draw(game.getBatch(), selectedOptionMark, newGameX - 32, 188);
				break;
			case 1:
				guiFont01_64.draw(game.getBatch(), selectedOptionMark, displayControlsX - 32, 188 - 32);
				break;
			case 2:
				guiFont01_64.draw(game.getBatch(), selectedOptionMark, changeRenderQualityX - 32, 188 - 64);
				break;
			case 3:
				guiFont01_64.draw(game.getBatch(), selectedOptionMark, quitGameX - 32, 188 - 96);
				break;
			default:
				guiFont01_64.draw(game.getBatch(), selectedOptionMark, newGameX - 32, 188);
				break;
			}
		} else {
			final float infoControls01X = viewport.getWorldWidth() / 2f - glyphLayoutInfoControls1.width / 2f;
			guiFont01_64.draw(game.getBatch(), infoControls01, infoControls01X, 188);
			final float infoControls02X = viewport.getWorldWidth() / 2f - glyphLayoutInfoControls2.width / 2f;
			guiFont01_64.draw(game.getBatch(), infoControls02, infoControls02X, 188 - 32);
			final float infoControls03X = viewport.getWorldWidth() / 2f - glyphLayoutInfoControls3.width / 2f;
			guiFont01_64.draw(game.getBatch(), infoControls03, infoControls03X, 188 - 64);
			final float infoControls04X = viewport.getWorldWidth() / 2f - glyphLayoutInfoControls4.width / 2f;
			guiFont01_64.draw(game.getBatch(), infoControls04, infoControls04X, 188 - 96);
			final float infoControls05X = viewport.getWorldWidth() / 2f - glyphLayoutInfoControls5.width / 2f;
			guiFont01_64.draw(game.getBatch(), infoControls05, infoControls05X, 188 - 128);

		}

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

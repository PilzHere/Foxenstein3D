package mysko.pilzhere.fox3d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mysko.pilzhere.fox3d.Foxenstein3D;
import mysko.pilzhere.fox3d.constants.Constants;
import mysko.pilzhere.fox3d.entities.IUsable;
import mysko.pilzhere.fox3d.entities.enemies.Eye;
import mysko.pilzhere.fox3d.entities.enemies.Fireball;
import mysko.pilzhere.fox3d.entities.enemies.Skull;
import mysko.pilzhere.fox3d.rect.RectanglePlus;
import mysko.pilzhere.fox3d.rect.filters.RectanglePlusFilter;

public class PlayScreen extends GameScreen {

	private final TextureRegion tempBg;
//	private final Sprite tempSpriteBg;

	private final RectanglePlus playerRect;

	private final Vector2 playerRectOldPos = new Vector2();

	private final Vector2 playerRectNewPos = new Vector2();

	boolean overlapX = false;
	boolean overlapY = false;

	private final Vector3 movementDir = new Vector3();

	final Vector2 movementDirVec2 = new Vector2(movementDir.x, movementDir.z);

//	private final Array<Class<?>> interactiveEntities = new Array<>(); // test

//	private Entity currrentInteractiveEntity;

	private final float rangeDistanceFromCam = 2f;

	private IUsable currentUsableInterface;

	private RectanglePlus closestRectInRange = null;

	private final float closestRectInRangeDistance = rangeDistanceFromCam;

	private float currentRectInRangeDistance = rangeDistanceFromCam;

	private final float cameraRotationSpeed = 100f;

	private final float playerMoveSpeed = 5f;

	Environment env;
	final Color fogColor;

	public PlayScreen(final Foxenstein3D game) {
		super(game);

		viewport = new StretchViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		currentCam = new PerspectiveCamera(70, 640, 480); // I KNOW THIS IS WRONG. Should be 70, 640, 480. This is why x
															// / y are negative when actually positive.
		currentCam.position.set(new Vector3(0, 0.5f, 0));
		currentCam.lookAt(new Vector3(0, 0.5f, -1));
		currentCam.near = 0.01f;
		currentCam.far = 10f;
		currentCam.update();
		viewport.setCamera(currentCam);

		env = new Environment();
		env.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 1, 1f));
		fogColor = new Color(66 / 256f, 33 / 256f, 54 / 256f, 1f);
		env.set(new ColorAttribute(ColorAttribute.Fog, fogColor));

		tempBg = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().bgSky01));
		tempBg.flip(false, true);
//		tempSpriteBg = new Sprite(tempBg);
//		tempSpriteBg.se
//		tempSpriteBg.flip(false, true);

		game.getMapBuilder().buildMap((TiledMap) game.getAssMan().get(game.getAssMan().map01));

//		game.getEntMan().addEntity(new Grid(this));

		playerRect = new RectanglePlus(0, 0, 0.25f, 0.25f, -1, RectanglePlusFilter.PLAYER); // test
//		playerRect.setPosition(-1 - playerRect.width / 2f, -1 - playerRect.height / 2f);
		playerRectOldPos.set(playerRect.x, playerRect.y);

		currentCam.position.set(playerRect.x + playerRect.width / 2f, 0.5f, playerRect.y + playerRect.height / 2f);

		Gdx.input.setCursorCatched(true);

		game.getEntMan().addEntity(new Skull(new Vector3(), this));
		game.getEntMan().addEntity(new Eye(new Vector3(5, 0, 5), this));
		game.getEntMan().addEntity(new Fireball(new Vector3(-5, 0, -5), this));
	}

	private void checkOverlaps(final float delta) {
		checkOverlapX(delta);
		checkOverlapY(delta);

		overlapX = false;
		overlapY = false;
	}

	/**
	 * Check for overlap in angle X.
	 */
	private void checkOverlapX(final float delta) {
		playerRect.setX(playerRectNewPos.x);

		overlapX = game.getRectMan().checkCollision(playerRect);

		if (overlapX) {
			playerRectNewPos.x = playerRectOldPos.x;
//			System.err.println("OVERLAP X");
		}

		playerRect.setX(playerRectNewPos.x);
	}

	/**
	 * Check for overlap in angle Y.
	 */
	private void checkOverlapY(final float delta) {
		playerRect.setY(playerRectNewPos.y);

		overlapY = game.getRectMan().checkCollision(playerRect);

		if (overlapY) {
			playerRectNewPos.y = playerRectOldPos.y;
//			System.err.println("OVERLAP Y");
		}

		playerRect.setY(playerRectNewPos.y);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public final void getRectInRangeFromCam() {
		currentUsableInterface = null;
		closestRectInRange = null;

		for (final RectanglePlus rect : game.getRectMan().rects) {
			if (Intersector.intersectSegmentRectangle(currentCam.position.x, currentCam.position.z,
					currentCam.position.x + currentCam.direction.x * rangeDistanceFromCam,
					currentCam.position.z + currentCam.direction.z * rangeDistanceFromCam, rect)) {

				currentRectInRangeDistance = Vector2.dst2(currentCam.position.x, currentCam.position.z,
						rect.x + rect.getWidth() / 2, rect.y + rect.getHeight() / 2);

				if (currentRectInRangeDistance < closestRectInRangeDistance) {
					closestRectInRange = rect;
				}

				if (closestRectInRange != null) {
//					System.out.println(currentRectInRangeDistance);

					if (game.getEntMan()
							.getEntityFromId(closestRectInRange.getConnectedEntityId()) instanceof IUsable) {
						currentUsableInterface = (IUsable) game.getEntMan()
								.getEntityFromId(closestRectInRange.getConnectedEntityId());
					}
				}
			}
		}
	}

	@Override
	public void handleInput(final float delta) {
		super.handleInput(delta);

		movementDir.setZero();

		if (Gdx.input.getDeltaX() != 0) {
			currentCam.rotate(Vector3.Y, Gdx.input.getDeltaX() * -cameraRotationSpeed * delta);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			movementDir.add(currentCam.direction.cpy());
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			movementDir.sub(currentCam.direction.cpy());
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			movementDir.sub(currentCam.direction.cpy().crs(currentCam.up));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			movementDir.add(currentCam.direction.cpy().crs(currentCam.up));
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			useUsableInterface(currentUsableInterface);

		}

		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {

		}

		movementDirVec2.set(movementDir.x, movementDir.z);
		playerRectNewPos.set(playerRect.getPosition(new Vector2()).cpy()
				.add(movementDirVec2.nor().cpy().scl(playerMoveSpeed * delta)));
	}

//	private void use

	@Override
	public void render(final float delta) {
		super.render(delta);

		currentCam.update();

		game.getFbo().begin();
//		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glClearColor(fogColor.r, fogColor.g, fogColor.b, fogColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

//		game.getBatch().getProjectionMatrix().setToOrtho2D(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		game.getBatch().begin();
		game.getBatch().draw(tempBg, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
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
		game.getBatch().end();
	}

	@Override
	public void resize(final int width, final int height) {
		super.resize(width, height);
	}

	@Override
	public void tick(final float delta) {
		super.tick(delta);

//		playerRect.setPosition(currentCam.position.x, currentCam.position.z);

//		game.getRectMan().checkCollision(playerRect); // player test

		checkOverlaps(delta);

		currentCam.position.set(playerRect.x + playerRect.width / 2f, 0.5f, playerRect.y + playerRect.height / 2f);

		getRectInRangeFromCam();

		playerRectOldPos.set(playerRect.x, playerRect.y);
	}

	private void useUsableInterface(final IUsable usableInterface) {
		if (currentUsableInterface != null) {
			usableInterface.onUse();
		}
	}
}

package mysko.pilzhere.fox3d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import mysko.pilzhere.fox3d.Foxenstein3D;
import mysko.pilzhere.fox3d.entities.player.Player;
import mysko.pilzhere.fox3d.models.ModelInstanceBB;
import mysko.pilzhere.fox3d.rect.RectanglePlus;

public class GameScreen implements Screen {
	public final Foxenstein3D game;

	protected Camera currentCam;

	protected Viewport viewport;
	private final Vector3 currentSpherePos = new Vector3();
	protected Player player;

	public GameScreen(final Foxenstein3D game) {
		this.game = game;

		game.getEntMan().setScreen(this);
	}

	public void checkOverlaps(final RectanglePlus rect, final float delta) {
		checkOverlapX(rect, delta);
		checkOverlapY(rect, delta);

		rect.overlapX = false;
		rect.overlapY = false;
	}

	/**
	 * Check for overlap in angle X.
	 */
	private void checkOverlapX(final RectanglePlus rect, final float delta) {
		rect.setX(rect.newPosition.x);

		rect.overlapX = game.getRectMan().checkCollision(rect);

		if (rect.overlapX) {
			rect.newPosition.x = rect.oldPosition.x;
//			System.err.println("OVERLAP X");
		}

		rect.setX(rect.newPosition.x);
	}

	/**
	 * Check for overlap in angle Y.
	 */
	private void checkOverlapY(final RectanglePlus rect, final float delta) {
		rect.setY(rect.newPosition.y);

		rect.overlapY = game.getRectMan().checkCollision(rect);

		if (rect.overlapY) {
			rect.newPosition.y = rect.oldPosition.y;
//			System.err.println("OVERLAP Y");
		}

		rect.setY(rect.newPosition.y);
	}

	@Override
	public void dispose() {

	}

	public boolean frustumCull(final Camera cam, final ModelInstanceBB modelInst) {
//		modelInst.calculateTransforms(); // Use if animations or moving mesh parts/bones.
		modelInst.calculateBoundingBox(modelInst.renderBox);
		modelInst.renderBox.mul(modelInst.transform.cpy());

		modelInst.transform.getTranslation(currentSpherePos);
		currentSpherePos.add(modelInst.center);

		return cam.frustum.sphereInFrustum(currentSpherePos, modelInst.radius);
	}

	public Camera getCurrentCam() {
		return currentCam;
	}

	public Player getPlayer() {
		return player;
	}

	public void handleInput(final float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void render(final float delta) {
		handleInput(delta);
		tick(delta);
	}

	@Override
	public void resize(final int width, final int height) {
		if (viewport != null) {
			viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {

	}

	public void tick(final float delta) {
		game.getEntMan().tickAllEntities(delta);
	}

}

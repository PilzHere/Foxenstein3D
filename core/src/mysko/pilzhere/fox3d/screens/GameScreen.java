package mysko.pilzhere.fox3d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import mysko.pilzhere.fox3d.Foxenstein3D;
import mysko.pilzhere.fox3d.cellbuilder.ModelInstanceBB;

public class GameScreen implements Screen {
	public final Foxenstein3D game;

	protected Camera currentCam;
	protected Viewport viewport;
	private final Vector3 currentSpherePos = new Vector3();

	public GameScreen(final Foxenstein3D game) {
		this.game = game;

		game.getEntMan().setScreen(this);
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

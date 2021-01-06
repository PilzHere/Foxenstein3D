package mysko.pilzhere.fox3d.input;

import com.badlogic.gdx.InputProcessor;

public class GameInputProcessor implements InputProcessor {

	public boolean scrolledYUp = false;
	public boolean scrolledYDown = false;

	@Override
	public boolean keyDown(final int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(final char character) {
		return false;
	}

	@Override
	public boolean keyUp(final int keycode) {
		return false;
	}

	@Override
	public boolean mouseMoved(final int screenX, final int screenY) {
		return false;
	}

	public void resetScrolled() {
		scrolledYUp = false;
		scrolledYDown = false;
	}

	@Override
	public boolean scrolled(final float amountX, final float amountY) {
		if (amountY < 0) {
			scrolledYUp = true;
			return true;
		} else if (amountY > 0) {
			scrolledYDown = true;
			return true;
		}

		return false;
	}

	@Override
	public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
		return false;
	}

	@Override
	public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
		return false;
	}

	@Override
	public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
		return false;
	}

}

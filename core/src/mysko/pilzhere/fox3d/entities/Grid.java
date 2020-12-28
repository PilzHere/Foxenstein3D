package mysko.pilzhere.fox3d.entities;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.models.ModelInstanceBB;
import mysko.pilzhere.fox3d.screens.GameScreen;

public class Grid extends Entity {

	private final Vector3 position = new Vector3();
	private final ModelInstanceBB mdlInst;

	public Grid(final GameScreen screen) {
		super(screen);

		mdlInst = new ModelInstanceBB(screen.game.getCellBuilder().mdlGrid);
	}

	@Override
	public void render3D(final ModelBatch mdlBatch, final Environment env, final float delta) {
		mdlBatch.render(mdlInst);
	}

	@Override
	public void tick(final float delta) {

	}
}

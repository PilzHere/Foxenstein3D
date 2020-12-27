package mysko.pilzhere.fox3d.entities.enemies;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.Entity;
import mysko.pilzhere.fox3d.models.ModelInstanceBB;
import mysko.pilzhere.fox3d.rect.RectanglePlus;
import mysko.pilzhere.fox3d.screens.GameScreen;

public class Enemy extends Entity {
	protected Vector3 position = new Vector3();

	protected ModelInstanceBB mdlInst;

	protected RectanglePlus rect;

	protected boolean isPlayerInRange = false;

	public Enemy(final Vector3 position, final GameScreen screen) {
		super(screen);

		this.position = position;
	}

	public Vector3 getPosition() {
		return position;
	}

	public RectanglePlus getRect() {
		return rect;
	}

	public boolean isPlayerInRange() {
		return isPlayerInRange;
	}

	@Override
	public void render3D(final ModelBatch mdlBatch, final Environment env, final float delta) {
		if (mdlInst != null) {
			mdlInst.setInFrustum(screen.frustumCull(screen.getCurrentCam(), mdlInst));
			if (mdlInst.isInFrustum()) {
				mdlBatch.render(mdlInst, env);
			}
		}
	}

	public void setIsPlayerInRange(final boolean isInRange) { // controlled by the player
		this.isPlayerInRange = isInRange;
	}
}

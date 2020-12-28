package mysko.pilzhere.fox3d.entities.enemies;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.entities.Entity;
import mysko.pilzhere.fox3d.models.ModelInstanceBB;
import mysko.pilzhere.fox3d.rect.RectanglePlus;
import mysko.pilzhere.fox3d.screens.GameScreen;

public class Enemy extends Entity {
	protected Vector3 position = new Vector3();

	protected ModelInstanceBB mdlInst;

	protected RectanglePlus rect;

	protected boolean isPlayerInRange = false;
	protected boolean isDead = false;

	protected int maxHp = 100;
	protected int currentHp = maxHp;

	public Enemy(final Vector3 position, final GameScreen screen) {
		super(screen);

		this.position = position;
	}

	public void addHp(final int amount) {
		currentHp += amount;
		limitHP();
		checkIfDead();
		destroyIfDead();
	}

	private void checkIfDead() {
		if (currentHp == 0) {
			isDead = true;
		}
	}

	@Override
	public void destroy() {
		if (destroy) {
			if (rect != null) {
				screen.game.getRectMan().removeRect(rect);
			}
		}

		super.destroy(); // should be last.
	}

	private void destroyIfDead() {
		if (isDead) {
			System.out.println("Enemy is dead.");

			destroy = true;
			destroy();
		}
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

	protected void limitHP() {
		if (currentHp > maxHp) {
			currentHp = maxHp;
		} else if (currentHp < 0) {
			currentHp = 0;
		}
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

	public void subHp(final int amount) {
		currentHp -= amount;
		limitHP();
		checkIfDead();
		destroyIfDead();
	}
}

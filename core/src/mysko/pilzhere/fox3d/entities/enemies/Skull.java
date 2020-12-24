package mysko.pilzhere.fox3d.entities.enemies;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.constants.Constants;
import mysko.pilzhere.fox3d.models.ModelInstanceBB;
import mysko.pilzhere.fox3d.rect.RectanglePlus;
import mysko.pilzhere.fox3d.rect.filters.RectanglePlusFilter;
import mysko.pilzhere.fox3d.screens.GameScreen;

public class Skull extends Enemy {
	private final TextureRegion currentTexReg;
	private final TextureRegion idleTexReg;

	public Skull(final Vector3 position, final GameScreen screen) {
		super(position, screen);
		this.position.add(-Constants.HALF_UNIT, 0, -Constants.HALF_UNIT);

		super.mdlInst = new ModelInstanceBB(screen.game.getCellBuilder().mdlEnemy);

		idleTexReg = new TextureRegion((Texture) screen.game.getAssMan().get(screen.game.getAssMan().enemies), 0, 0, 16,
				16);
		currentTexReg = idleTexReg;

		mdlInst.materials.get(0).set(TextureAttribute.createDiffuse(currentTexReg));
		mdlInst.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

		rect = new RectanglePlus(this.position.x, this.position.z, 0.25f, 0.25f, id, RectanglePlusFilter.ENEMY);
		rect.setPosition(this.position.x - rect.getWidth() / 2, this.position.z - rect.getHeight() / 2);
		screen.game.getRectMan().addRect(rect);
	}

	@Override
	public void render3D(final ModelBatch mdlBatch, final Environment env, final float delta) {
		mdlInst.transform.setToLookAt(screen.getCurrentCam().direction.cpy().rotate(Vector3.Z, 180f), Vector3.Y);
		mdlInst.transform.setTranslation(position.cpy().add(0, Constants.HALF_UNIT, 0));

		super.render3D(mdlBatch, env, delta);
	}

	@Override
	public void tick(final float delta) {
//		ai tick here

		rect.setPosition(this.position.x - rect.getWidth() / 2, this.position.z - rect.getHeight() / 2);
	}

}

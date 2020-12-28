package mysko.pilzhere.fox3d.entities.objects;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.constants.Constants;
import mysko.pilzhere.fox3d.entities.Entity;
import mysko.pilzhere.fox3d.entities.player.Player;
import mysko.pilzhere.fox3d.models.ModelInstanceBB;
import mysko.pilzhere.fox3d.rect.RectanglePlus;
import mysko.pilzhere.fox3d.rect.filters.RectanglePlusFilter;
import mysko.pilzhere.fox3d.screens.GameScreen;

public class Key extends Entity {

	private final int keyType;

	Vector3 position = new Vector3();
	ModelInstanceBB mdlInst;
	RectanglePlus rect;

	private final TextureRegion currentTexReg;
	private final TextureRegion idleTexReg;

	public Key(final Vector3 position, final int keyType, final GameScreen screen) {
		super(screen);
		this.position = position.cpy();
		this.position.add(Constants.HALF_UNIT, 0, Constants.HALF_UNIT);
		this.keyType = keyType;

		mdlInst = new ModelInstanceBB(screen.game.getCellBuilder().mdlEnemy);

		switch (keyType) {
		case 1:
			idleTexReg = new TextureRegion((Texture) screen.game.getAssMan().get(screen.game.getAssMan().guiBG), 160, 0,
					16, 16);
			break;
		case 2:
			idleTexReg = new TextureRegion((Texture) screen.game.getAssMan().get(screen.game.getAssMan().guiBG),
					160 + 16, 0, 16, 16);
			break;
		case 3:
			idleTexReg = new TextureRegion((Texture) screen.game.getAssMan().get(screen.game.getAssMan().guiBG),
					160 + 32, 0, 16, 16);
			break;
		case 4:
			idleTexReg = new TextureRegion((Texture) screen.game.getAssMan().get(screen.game.getAssMan().guiBG),
					160 + 48, 0, 16, 16);
			break;
		default:
			idleTexReg = new TextureRegion((Texture) screen.game.getAssMan().get(screen.game.getAssMan().guiBG),
					160 + 64, 0, 16, 16);
			break;
		}

		currentTexReg = idleTexReg;

		mdlInst.materials.get(0).set(TextureAttribute.createDiffuse(currentTexReg));
		mdlInst.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
		mdlInst.materials.get(0).set(new FloatAttribute(FloatAttribute.AlphaTest));

		final float rectWidth = Constants.HALF_UNIT;
		final float rectHeight = Constants.HALF_UNIT;
		rect = new RectanglePlus(this.position.x, this.position.z, rectWidth, rectHeight, id, RectanglePlusFilter.ITEM);
		rect.setPosition(this.position.x - rect.getWidth() / 2, this.position.z - rect.getHeight() / 2);
		screen.game.getRectMan().addRect(rect);

		rect.oldPosition.set(rect.x, rect.y);
		rect.newPosition.set(rect.x, rect.y);
	}

	@Override
	public void destroy() {
		if (destroy) {
			screen.game.getRectMan().removeRect(rect);
		}

		super.destroy(); // should be last.
	}

	@Override
	public void onCollision(final RectanglePlus otherRect) {
		if (otherRect.filter == RectanglePlusFilter.PLAYER) {
			super.onCollision(otherRect);

			if (collidedEntity instanceof Player) {
				switch (keyType) {
				case 1:
					screen.getPlayer().hasRedKeycard = true;
					break;
				case 2:
					screen.getPlayer().hasGreenKeycard = true;
					break;
				case 3:
					screen.getPlayer().hasBlueKeycard = true;
					break;
				case 4:
					screen.getPlayer().hasGoldenKeycard = true;
					break;
				default:
					screen.getPlayer().hasRedKeycard = true;
					break;
				}

				setDestroy(true);
				destroy();
			}
		}

		collidedEntity = null;
	}

	@Override
	public void render3D(final ModelBatch mdlBatch, final Environment env, final float delta) {
		mdlInst.transform.setToLookAt(screen.getCurrentCam().direction.cpy().rotate(Vector3.Z, 180f), Vector3.Y);
		mdlInst.transform.setTranslation(position.cpy().add(0, Constants.HALF_UNIT, 0));

//		super.render3D(mdlBatch, env, delta); // from Enemy class, dont use.

		if (mdlInst != null) { // should be somewhere else -> Item object super class.
			mdlInst.setInFrustum(screen.frustumCull(screen.getCurrentCam(), mdlInst));
			if (mdlInst.isInFrustum()) {
				mdlBatch.render(mdlInst, env);
			}
		}
	}

	@Override
	public void tick(final float delta) {
		screen.checkOverlaps(rect, delta);

		position.set(rect.x + rect.getWidth() / 2, 0, rect.y + rect.getHeight() / 2);

		rect.oldPosition.set(rect.x, rect.y);
	}

}

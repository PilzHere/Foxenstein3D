package mysko.pilzhere.fox3d.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.Entity;
import mysko.pilzhere.fox3d.constants.Constants;
import mysko.pilzhere.fox3d.models.ModelInstanceBB;
import mysko.pilzhere.fox3d.rect.RectanglePlus;
import mysko.pilzhere.fox3d.rect.filters.RectanglePlusFilter;
import mysko.pilzhere.fox3d.screens.GameScreen;

public class Door extends Entity implements IUsable {

	public final Vector3 position = new Vector3();

	private final ModelInstanceBB mdlInstDoor;

	private int direction = 0;
	private boolean locked = false;
	private boolean closed = true;
	private final Vector3 openPos = new Vector3();
	private final Vector3 closedPos = new Vector3();
	private boolean animate = false;
	private final float doorMoveSpeed = 2f;
	private TextureRegion texRegDoorLockedNorth, texRegDoorLockedSouth;

	private RectanglePlus rect;

	private final Vector3 currentTranslation = new Vector3();

	public Door(final Vector3 position, final int direction, final boolean locked, final GameScreen screen) {
		super(screen);
		this.position.set(position.cpy().add(Constants.HALF_UNIT, Constants.HALF_UNIT, 0));
		this.direction = direction;
		this.locked = locked;

		mdlInstDoor = new ModelInstanceBB(screen.game.getCellBuilder().mdlDoor);

		if (locked) {
			texRegDoorLockedNorth = new TextureRegion(
					(Texture) screen.game.getAssMan().get(screen.game.getAssMan().atlas01), 80, 48, -16, 16);
			texRegDoorLockedSouth = new TextureRegion(
					(Texture) screen.game.getAssMan().get(screen.game.getAssMan().atlas01), 64, 48, 16, 16);
			final TextureAttribute ta1 = (TextureAttribute) mdlInstDoor.materials.get(0).get(TextureAttribute.Diffuse);
			ta1.set(texRegDoorLockedNorth);
			final TextureAttribute ta2 = (TextureAttribute) mdlInstDoor.materials.get(1).get(TextureAttribute.Diffuse);
			ta2.set(texRegDoorLockedSouth);
		}

		if (direction == 0) {
//			face door south in game, north on map
			closedPos.set(this.position.cpy().add(new Vector3(0, 0, 1 - Constants.PPU * 2)));
			openPos.set(closedPos.cpy().add(1 - Constants.PPU * 2, 0, 0));
		} else if (direction == 1) {
//			face door north in game, south on map
			closedPos.set(this.position.cpy().add(new Vector3(0, 0, 0 + Constants.PPU * 2)));
			openPos.set(closedPos.cpy().add(1 - Constants.PPU * 2, 0, 0));
		} else if (direction == 2) {
//			face door east in game, west on map
			closedPos.set(this.position.cpy()
					.add(new Vector3(-Constants.HALF_UNIT + Constants.PPU * 2, 0, Constants.HALF_UNIT)));
			openPos.set(closedPos.cpy().add(0, 0, 1 - Constants.PPU * 2));
		} else if (direction == 3) {
//			face door west in game, east on map
			closedPos.set(this.position.cpy()
					.add(new Vector3(Constants.HALF_UNIT - Constants.PPU * 2, 0, Constants.HALF_UNIT)));
			openPos.set(closedPos.cpy().add(0, 0, 1 - Constants.PPU * 2));
		}

		mdlInstDoor.transform.setToTranslation(closedPos.cpy());
		if (direction > 1) {
			mdlInstDoor.transform.rotate(Vector3.Y, -90);
		}

		setupRect();

		screen.game.getRectMan().addRect(rect);
	}

	public void moveDoor() {
		animate = true;
	}

	@Override
	public void onUse() {
		useDoor();
//		System.out.println("door! dir: " + direction + " closed: " + closed);
	}

	@Override
	public void render3D(final ModelBatch mdlBatch, final Environment env, final float delta) {
		mdlInstDoor.setInFrustum(screen.frustumCull(screen.getCurrentCam(), mdlInstDoor));
		if (mdlInstDoor.isInFrustum()) {
			mdlBatch.render(mdlInstDoor, env);
		}
	}

	private void setupRect() {
		if (direction < 2) {
			rect = new RectanglePlus(
					mdlInstDoor.transform.getTranslation(new Vector3()).x - mdlInstDoor.renderBox.getWidth() / 2,
					mdlInstDoor.transform.getTranslation(new Vector3()).z - mdlInstDoor.renderBox.getDepth() / 2,
					mdlInstDoor.renderBox.getWidth(), mdlInstDoor.renderBox.getDepth(), id, RectanglePlusFilter.DOOR);
		} else {
			rect = new RectanglePlus(
					mdlInstDoor.transform.getTranslation(new Vector3()).x - mdlInstDoor.renderBox.getDepth() / 2,
					mdlInstDoor.transform.getTranslation(new Vector3()).z - mdlInstDoor.renderBox.getWidth() / 2,
					mdlInstDoor.renderBox.getDepth(), mdlInstDoor.renderBox.getWidth(), id, RectanglePlusFilter.DOOR);
		}
	}

	@Override
	public void tick(final float delta) {
		if (direction < 2) {
			if (animate) {
				if (closed) {
					mdlInstDoor.transform.getTranslation(currentTranslation);
					currentTranslation.x += doorMoveSpeed * delta;
					mdlInstDoor.transform.setTranslation(currentTranslation.cpy());

					if (currentTranslation.x >= openPos.x) {
						mdlInstDoor.transform.setTranslation(openPos.cpy());
						closed = false;
						animate = false;
					}
				} else {
					mdlInstDoor.transform.getTranslation(currentTranslation);
					currentTranslation.x -= doorMoveSpeed * delta;
					mdlInstDoor.transform.setTranslation(currentTranslation.cpy());

					if (currentTranslation.x <= closedPos.x) {
						mdlInstDoor.transform.setTranslation(closedPos.cpy());
						closed = true;
						animate = false;
					}
				}
			}
		} else {
			if (animate) {
				if (closed) {
					mdlInstDoor.transform.getTranslation(currentTranslation);
					currentTranslation.z += doorMoveSpeed * delta;
					mdlInstDoor.transform.setTranslation(currentTranslation.cpy());

					if (currentTranslation.z >= openPos.z) {
						mdlInstDoor.transform.setTranslation(openPos.cpy());
						closed = false;
						animate = false;
					}
				} else {
					mdlInstDoor.transform.getTranslation(currentTranslation);
					currentTranslation.z -= doorMoveSpeed * delta;
					mdlInstDoor.transform.setTranslation(currentTranslation.cpy());

					if (currentTranslation.z <= closedPos.z) {
						mdlInstDoor.transform.setTranslation(closedPos.cpy());
						closed = true;
						animate = false;
					}
				}
			}
		}

		rect.setPosition(mdlInstDoor.transform.getTranslation(new Vector3()).x - mdlInstDoor.renderBox.getWidth() / 2,
				mdlInstDoor.transform.getTranslation(new Vector3()).z - mdlInstDoor.renderBox.getDepth() / 2);

	}

	public void useDoor() {
		if (!locked) {
			moveDoor();
		}
	}
}

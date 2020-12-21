package mysko.pilzhere.fox3d.cells;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.Entity;
import mysko.pilzhere.fox3d.cellbuilder.ModelInstanceBB;
import mysko.pilzhere.fox3d.screens.GameScreen;

public class Cell3D extends Entity {
	public final Vector3 position = new Vector3();

	private ModelInstanceBB mdlInstWallNorth;
	private ModelInstanceBB mdlInstWallSouth;
	private ModelInstanceBB mdlInstWallWest;
	private ModelInstanceBB mdlInstWallEast;
	private ModelInstanceBB mdlInstFloor;
	private ModelInstanceBB mdlInstCeiling;

	public boolean hasWallNorth = true;
	public boolean hasWallSouth = true;
	public boolean hasWallWest = true;
	public boolean hasWallEast = true;
	public boolean hasFloor = false;
	public boolean hasCeiling = false;

	public boolean neighbourCellNorth = false;
	public boolean neighbourCellSouth = false;
	public boolean neighbourCellWest = false;
	public boolean neighbourCellEast = false;

	public Cell3D(final Vector3 position, final GameScreen screen) {
		super(screen);
		this.position.set(position.cpy().add(0, 0.5f, 0));
	}

	public void buildCell() {
		if (hasWallNorth) {
			mdlInstWallNorth = new ModelInstanceBB(screen.game.getCellBuilder().mdlWallNorth);
			mdlInstWallNorth.transform.setToTranslation(this.position.cpy().add(new Vector3(0, 0, -0.5f)));
		}

		if (hasWallSouth) {
			mdlInstWallSouth = new ModelInstanceBB(screen.game.getCellBuilder().mdlWallSouth);
			mdlInstWallSouth.transform.setToTranslation(this.position.cpy().add(new Vector3(0, 0, 0.5f)));
		}

		if (hasWallWest) {
			mdlInstWallWest = new ModelInstanceBB(screen.game.getCellBuilder().mdlWallWest);
			mdlInstWallWest.transform.setToTranslation(this.position.cpy().add(new Vector3(0.5f, 0, 0)));
		}

		if (hasWallEast) {
			mdlInstWallEast = new ModelInstanceBB(screen.game.getCellBuilder().mdlWallEast);
			mdlInstWallEast.transform.setToTranslation(this.position.cpy().add(new Vector3(-0.5f, 0, 0)));
		}

		if (hasFloor) {
			mdlInstFloor = new ModelInstanceBB(screen.game.getCellBuilder().mdlFloor);
			mdlInstFloor.transform.setToTranslation(this.position.cpy().add(new Vector3(0, -0.5f, 0)));
		}

		if (hasCeiling) {
			mdlInstCeiling = new ModelInstanceBB(screen.game.getCellBuilder().mdlCeiling);
			mdlInstCeiling.transform.setToTranslation(this.position.cpy().add(new Vector3(0, 0.5f, 0)));
		}

	}

	@Override
	public void render3D(final ModelBatch mdlBatch, final float delta) {

		if (hasWallNorth) {
			mdlInstWallNorth.setInFrustum(screen.frustumCull(screen.getCurrentCam(), mdlInstWallNorth));
			if (mdlInstWallNorth.isInFrustum()) {
				mdlBatch.render(mdlInstWallNorth);
			}
		}

		if (hasWallSouth) {
			mdlInstWallSouth.setInFrustum(screen.frustumCull(screen.getCurrentCam(), mdlInstWallSouth));
			if (mdlInstWallSouth.isInFrustum()) {
				mdlBatch.render(mdlInstWallSouth);
			}
		}

		if (hasWallWest) {
			mdlInstWallWest.setInFrustum(screen.frustumCull(screen.getCurrentCam(), mdlInstWallWest));
			if (mdlInstWallWest.isInFrustum()) {
				mdlBatch.render(mdlInstWallWest);
			}
		}

		if (hasWallEast) {
			mdlInstWallEast.setInFrustum(screen.frustumCull(screen.getCurrentCam(), mdlInstWallEast));
			if (mdlInstWallEast.isInFrustum()) {
				mdlBatch.render(mdlInstWallEast);
			}
		}

		if (hasFloor) {
			mdlInstFloor.setInFrustum(screen.frustumCull(screen.getCurrentCam(), mdlInstFloor));
			if (mdlInstFloor.isInFrustum()) {
				mdlBatch.render(mdlInstFloor);
			}
		}

		if (hasCeiling) {
			mdlInstCeiling.setInFrustum(screen.frustumCull(screen.getCurrentCam(), mdlInstCeiling));
			if (mdlInstCeiling.isInFrustum()) {
				mdlBatch.render(mdlInstCeiling);
			}
		}
	}

}

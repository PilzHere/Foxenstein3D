package mysko.pilzhere.fox3d.cellbuilder;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class ModelInstanceBB extends ModelInstance {

	private boolean isInFrustum = false;

	public final Vector3 center = new Vector3(); // for sphere
	public final Vector3 dimensions = new Vector3(); // for sphere
	public float radius; // for sphere
	public final BoundingBox renderBox = new BoundingBox();
//	public Color devRendererColor;

	// These are currently only used by dev.
//	public String bbName;

	public ModelInstanceBB(final Model model) {
		super(model);

		calculateTransforms();
		calculateBoundingBox(renderBox);
		renderBox.mul(transform);

		renderBox.getCenter(center);
		renderBox.getDimensions(dimensions);
		radius = dimensions.len() / 2f;
	}

	public boolean isInFrustum() {
		return isInFrustum;
	}

//	public boolean isOccluded() {
//		return isOccluded;
//	}

	public void setInFrustum(final boolean isVisible) {
		this.isInFrustum = isVisible;
	}

//	public void setOccluded(final boolean isOccluded) {
//		this.isOccluded = isOccluded;
//	}
}

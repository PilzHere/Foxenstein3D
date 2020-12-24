package mysko.pilzhere.fox3d.rect;

import com.badlogic.gdx.math.Rectangle;

import mysko.pilzhere.fox3d.rect.filters.RectanglePlusFilter;

public class RectanglePlus extends Rectangle {
	private static final long serialVersionUID = 6589196508238637331L;

	private int connectedEntityId = 0;
	public RectanglePlusFilter filter = RectanglePlusFilter.NONE;

	public RectanglePlus(final float x, final float y, final float width, final float height) {
		super(x, y, width, height);
	}

	public RectanglePlus(final float x, final float y, final float width, final float height,
			final int connectedEntityId, final RectanglePlusFilter filter) {
		this(x, y, width, height);

		this.connectedEntityId = connectedEntityId;
		this.filter = filter;
	}

	public int getConnectedEntityId() {
		return connectedEntityId;
	}

	public void setConnectedEntityId(final int connectedEntityId) {
		this.connectedEntityId = connectedEntityId;
	}

}

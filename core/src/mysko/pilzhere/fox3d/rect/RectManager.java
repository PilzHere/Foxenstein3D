package mysko.pilzhere.fox3d.rect;

import com.badlogic.gdx.utils.Array;

import mysko.pilzhere.fox3d.Foxenstein3D;

public class RectManager {

	public final Array<RectanglePlus> rects = new Array<>();

	private final Foxenstein3D game;

	public RectManager(final Foxenstein3D game) {
		this.game = game;
	}

	public void addRect(final RectanglePlus rect) {
		rects.add(rect);
	}

	public boolean checkCollision(final RectanglePlus rect) {
		for (final RectanglePlus otherRect : rects) {
			if (game.getOverlapFilterMan().doesFiltersOverlap(rect.filter, otherRect.filter)) {
				if (otherRect != rect) { // if not itself...
					if (rect.overlaps(otherRect)) {
//						System.out.println(otherRect.filter); // check filter here
						return true;
					}
				}
			}
		}
		return false;
	}

//	public void removeRect(final int id) {
//		for (int i = 0; i < rects.size; i++) {
//			if (id == rects.get(i).getId()) {
//				if (rects.get(i).shouldDestroy()) {
//					rects.removeIndex(i);
//				}
//			}
//		}
//	}
}

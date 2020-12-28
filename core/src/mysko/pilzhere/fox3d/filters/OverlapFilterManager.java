package mysko.pilzhere.fox3d.filters;

import mysko.pilzhere.fox3d.rect.filters.RectanglePlusFilter;

public class OverlapFilterManager {

	public final int[][] overlapFilters = new int[8][8];

	public OverlapFilterManager() {
		setupFilters();
	}

	public boolean doesFiltersOverlap(final RectanglePlusFilter thisFilter, final RectanglePlusFilter otherFilter) {
//		System.out.println(thisFilter.getValue() + " x " + otherFilter.getValue());

//		Having issues with overlaps? Did you register the filter first?

		for (int x = 0; x < overlapFilters.length; x++) {
			if (x == thisFilter.getValue()) {
				for (int y = 1; y < overlapFilters[x].length; y++) { // y != 0!
					if (overlapFilters[x][y] == otherFilter.getValue()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private void setupFilters() {
//		Setup filters
		overlapFilters[0][0] = RectanglePlusFilter.NONE.getValue(); // Don't touch me.

		overlapFilters[1][0] = RectanglePlusFilter.WALL.getValue();
		overlapFilters[2][0] = RectanglePlusFilter.DOOR.getValue();
		overlapFilters[3][0] = RectanglePlusFilter.PLAYER.getValue();
		overlapFilters[4][0] = RectanglePlusFilter.ENEMY.getValue();
		overlapFilters[5][0] = RectanglePlusFilter.ENEMY_PROJECTILE.getValue();
		overlapFilters[6][0] = RectanglePlusFilter.ITEM.getValue();

//		Set what filters collide with
//		Wall
//		overlapFilters[1][1] = RectanglePlusFilter.PLAYER.getValue();
//		overlapFilters[1][2] = RectanglePlusFilter.ENEMY.getValue();
//		overlapFilters[1][3] = RectanglePlusFilter.ENEMY_PROJECTILE.getValue();

//		Door
		overlapFilters[2][1] = RectanglePlusFilter.PLAYER.getValue();
		overlapFilters[2][2] = RectanglePlusFilter.ENEMY.getValue();
		overlapFilters[2][3] = RectanglePlusFilter.ENEMY_PROJECTILE.getValue();

//		Player
		overlapFilters[3][1] = RectanglePlusFilter.WALL.getValue();
		overlapFilters[3][2] = RectanglePlusFilter.DOOR.getValue();
//		overlapFilters[3][3] = RectanglePlusFilter.PLAYER.getValue();
		overlapFilters[3][4] = RectanglePlusFilter.ENEMY.getValue();
		overlapFilters[3][5] = RectanglePlusFilter.ENEMY_PROJECTILE.getValue();
		overlapFilters[3][6] = RectanglePlusFilter.ITEM.getValue();

//		Enemy
		overlapFilters[4][1] = RectanglePlusFilter.WALL.getValue();
		overlapFilters[4][2] = RectanglePlusFilter.DOOR.getValue();
		overlapFilters[4][3] = RectanglePlusFilter.PLAYER.getValue();
		overlapFilters[4][4] = RectanglePlusFilter.ENEMY.getValue();

//		Projectile
		overlapFilters[5][1] = RectanglePlusFilter.WALL.getValue();
		overlapFilters[5][2] = RectanglePlusFilter.DOOR.getValue();
		overlapFilters[5][3] = RectanglePlusFilter.PLAYER.getValue();
//		overlapFilters[5][4] = RectanglePlusFilter.ENEMY.getValue();

//		Items
		overlapFilters[6][1] = RectanglePlusFilter.PLAYER.getValue();

//		print out
//		System.err.println("FILTERS SETUP:");
//		for (int x = 0; x < OVERLAP_FILTERS.length; x++) {
//			System.out.println();
//			for (int y = 0; y < OVERLAP_FILTERS[x].length; y++) {
//				System.out.print("[" + OVERLAP_FILTERS[x][y] + "]");
//			}
//		}
//		System.out.println("\n");
	}
}

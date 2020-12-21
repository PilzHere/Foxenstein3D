package mysko.pilzhere.fox3d.maps;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import mysko.pilzhere.fox3d.Foxenstein3D;
import mysko.pilzhere.fox3d.cells.Cell3D;

public class MapBuilder {

	private final Foxenstein3D game;

	private int currentMapWidth;
	private int currentMapHeight;

	public MapBuilder(final Foxenstein3D game) {
		this.game = game;
	}

	public void buildMap(final TiledMap map) {
		final MapProperties props = map.getProperties();
		currentMapWidth = props.get("width", Integer.class);
		currentMapHeight = props.get("height", Integer.class);

		System.out.println("map width: " + currentMapWidth);
		System.out.println("map height: " + currentMapHeight);

		final MapLayers mapLayers = map.getLayers();
		final TiledMapTileLayer floorLayer = (TiledMapTileLayer) mapLayers.get("floor");
		final TiledMapTileLayer ceilingLayer = (TiledMapTileLayer) mapLayers.get("ceiling");

		final Array<Cell3D> cell3DsForWorld = new Array<>();

		final int tileFloorId = 17;
		final int tileCeilingId = 1;

		final float halfUnit = 0.5f;

		for (int x = 0; x < currentMapWidth; x++) {
			for (int z = 0; z < currentMapHeight; z++) {
				Cell currentCell = floorLayer.getCell(x, z);
				Cell3D currentCell3D = null;

				if (currentCell != null) {
					if (currentCell.getTile().getId() == tileFloorId + 1) {
						if (currentCell3D == null) {
							currentCell3D = new Cell3D(new Vector3(-x, 0, -z), game.getEntMan().getScreen());
						}

						currentCell3D.hasFloor = true;
					}
				}

				currentCell = ceilingLayer.getCell(x, z);

				if (currentCell != null) {
					if (currentCell.getTile().getId() == tileCeilingId + 1) {
						if (currentCell3D == null) {
							currentCell3D = new Cell3D(new Vector3(-x, 0, -z), game.getEntMan().getScreen());
						}

						currentCell3D.hasCeiling = true;
					}
				}

				if (currentCell3D != null) {
					cell3DsForWorld.add(currentCell3D);
				}
			}
		}

//		Check for walls
//		FIXME: Cant have ceiling alone. But floor is ok.
		for (int i = 0; i < cell3DsForWorld.size; i++) {
			final Cell3D currentCell3D = cell3DsForWorld.get(i);

			for (int j = 0; j < cell3DsForWorld.size; j++) {
				final Cell3D otherCell3D = cell3DsForWorld.get(j);

				if (otherCell3D.position.x == currentCell3D.position.x - 1
						&& otherCell3D.position.z == currentCell3D.position.z) {
					currentCell3D.hasWallEast = false;
				}

				if (otherCell3D.position.x == currentCell3D.position.x + 1
						&& otherCell3D.position.z == currentCell3D.position.z) {
					currentCell3D.hasWallWest = false;
				}

				if (otherCell3D.position.x == currentCell3D.position.x
						&& otherCell3D.position.z == currentCell3D.position.z - 1) {
					currentCell3D.hasWallNorth = false;
				}

				if (otherCell3D.position.x == currentCell3D.position.x
						&& otherCell3D.position.z == currentCell3D.position.z + 1) {
					currentCell3D.hasWallSouth = false;
				}
			}
		}

		for (final Cell3D cell3d : cell3DsForWorld) {
			cell3d.position.add(currentMapWidth / 2, 0, currentMapHeight / 2).add(-halfUnit, 0, -halfUnit);
			cell3d.buildCell();
			game.getEntMan().addEntity(cell3d);
		}

		cell3DsForWorld.clear();
	}
}

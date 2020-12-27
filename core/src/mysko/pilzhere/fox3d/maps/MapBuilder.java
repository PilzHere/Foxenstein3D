package mysko.pilzhere.fox3d.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import mysko.pilzhere.fox3d.Foxenstein3D;
import mysko.pilzhere.fox3d.cell.Cell3D;
import mysko.pilzhere.fox3d.entities.Door;
import mysko.pilzhere.fox3d.entities.enemies.Enemy;
import mysko.pilzhere.fox3d.entities.enemies.Eye;
import mysko.pilzhere.fox3d.entities.enemies.Skull;
import mysko.pilzhere.fox3d.rect.RectanglePlus;
import mysko.pilzhere.fox3d.rect.filters.RectanglePlusFilter;

public class MapBuilder {

	private final Foxenstein3D game;

	private int currentMapWidth;
	private int currentMapHeight;

	public Vector2 mapLoadSpawnPosition = new Vector2();

	public MapBuilder(final Foxenstein3D game) {
		this.game = game;
	}

	public void buildMap(final TiledMap map) {
		final MapProperties props = map.getProperties();
		currentMapWidth = props.get("width", Integer.class);
		currentMapHeight = props.get("height", Integer.class);

//		System.out.println("map width: " + currentMapWidth);
//		System.out.println("map height: " + currentMapHeight);

		final MapLayers mapLayers = map.getLayers();
		final TiledMapTileLayer floorLayer = (TiledMapTileLayer) mapLayers.get("floor");
		final TiledMapTileLayer ceilingLayer = (TiledMapTileLayer) mapLayers.get("ceiling");
		final MapObjects rects = mapLayers.get("rects").getObjects();
		final MapObjects enemies = mapLayers.get("enemies").getObjects();
		final MapObjects doors = mapLayers.get("doors").getObjects();
		final MapObjects teleports = mapLayers.get("teleports").getObjects();

		final Array<Cell3D> cell3DsForWorld = new Array<>();

		final int tileDevFloorId = 17 + 1;
		final int tileFloor01Id = 3 + 1;
		final int tileFloor02Id = 5 + 1;

		final int tileDevCeilingId = 1 + 1;

		final float halfUnit = 0.5f;
		final float tileSize = 16f;

		for (int x = 0; x < currentMapWidth; x++) {
			for (int z = 0; z < currentMapHeight; z++) {
				Cell currentCell = floorLayer.getCell(x, z);
				Cell3D currentCell3D = null;

				if (currentCell != null) {
					switch (currentCell.getTile().getId()) {
					case tileDevFloorId:
						currentCell3D = new Cell3D(new Vector3(x, 0, z), game.getEntMan().getScreen());
						break;
					case tileFloor01Id:
						currentCell3D = new Cell3D(new Vector3(x, 0, z), game.getEntMan().getScreen());

//						This should be setup somewhere else but i dont have time!
						currentCell3D.texRegNorth = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 32, 0, 16, 16);
						currentCell3D.texRegSouth = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 32, 0, 16, 16);
						currentCell3D.texRegWest = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 32, 0, 16, 16);
						currentCell3D.texRegEast = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 32, 0, 16, 16);
						currentCell3D.texRegCeiling = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 64, 0, 16, 16);
						currentCell3D.texRegFloor = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 48, 0, 16, 16);
						break;
					case tileFloor02Id:
						currentCell3D = new Cell3D(new Vector3(x, 0, z), game.getEntMan().getScreen());

						currentCell3D.texRegNorth = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 96, 0, 16, 16);
						currentCell3D.texRegSouth = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 96, 0, 16, 16);
						currentCell3D.texRegWest = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 96, 0, 16, 16);
						currentCell3D.texRegEast = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 96, 0, 16, 16);
						currentCell3D.texRegCeiling = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 48, 0, 16, 16);
						currentCell3D.texRegFloor = new TextureRegion(
								(Texture) game.getAssMan().get(game.getAssMan().atlas01), 80, 0, 16, 16);
						break;
					default:
						currentCell3D = new Cell3D(new Vector3(x, 0, z), game.getEntMan().getScreen());
						break;
					}

					currentCell3D.hasFloor = true;
				}

				currentCell = ceilingLayer.getCell(x, z);

				if (currentCell != null) {
					if (currentCell.getTile().getId() == tileDevCeilingId) {
						if (currentCell3D == null) {
							currentCell3D = new Cell3D(new Vector3(x, 0, z), game.getEntMan().getScreen());
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

//		Build rects - the real walls
		for (final MapObject rectObj : rects) {
			final RectanglePlus rect = new RectanglePlus((float) rectObj.getProperties().get("x") / tileSize,
					(float) rectObj.getProperties().get("y") / tileSize,
					(float) rectObj.getProperties().get("width") / tileSize,
					(float) rectObj.getProperties().get("height") / tileSize, -1, RectanglePlusFilter.WALL);

			rect.x = rect.x - currentMapWidth / 2;
			rect.y = rect.y - currentMapHeight / 2;

			game.getRectMan().addRect(rect);
		}

		for (final Cell3D cell3d : cell3DsForWorld) {
			cell3d.position.add(-currentMapWidth / 2, 0, -currentMapHeight / 2).add(halfUnit, 0, halfUnit);

////			set textures here
//			cell3d.texRegNorthId =

			cell3d.buildCell();
			game.getEntMan().addEntity(cell3d);
		}

		cell3DsForWorld.clear();

		for (final MapObject doorObj : doors) {
			final String direction = (String) doorObj.getProperties().get("direction");
			int doorDir = 0;

			switch (direction) {
			case "north":
				doorDir = 0;
				break;
			case "south":
				doorDir = 1;
				break;
			case "west":
				doorDir = 2;
				break;
			case "east":
				doorDir = 3;
				break;
			default:
				doorDir = 0;
				break;
			}

			final boolean locked = doorObj.getProperties().get("locked", Boolean.class);
			final Door door = new Door(
					new Vector3((float) doorObj.getProperties().get("x") / tileSize - currentMapWidth / 2, 0,
							(float) doorObj.getProperties().get("y") / tileSize - currentMapHeight / 2),
					doorDir, locked, game.getEntMan().getScreen());

			game.getEntMan().addEntity(door);
		}

		for (final MapObject enemyObj : enemies) {
			final String enemyType = enemyObj.getProperties().get("EnemyType", String.class);

			if (!enemyType.isEmpty() || enemyType != null || enemyType.equalsIgnoreCase("null")) {
				Enemy enemy = null;

				switch (enemyType) {
				case "Skull":
					enemy = new Skull(
							new Vector3((float) enemyObj.getProperties().get("x") / tileSize - currentMapWidth / 2, 0,
									(float) enemyObj.getProperties().get("y") / tileSize - currentMapHeight / 2),
							game.getEntMan().getScreen());
					break;
				case "Eye":
					enemy = new Eye(
							new Vector3((float) enemyObj.getProperties().get("x") / tileSize - currentMapWidth / 2, 0,
									(float) enemyObj.getProperties().get("y") / tileSize - currentMapHeight / 2),
							game.getEntMan().getScreen());
					break;
//				case "Fireball":
//					enemy = new Fireball(
//							new Vector3((float) enemyObj.getProperties().get("x") / tileSize - currentMapWidth / 2, 0,
//									(float) enemyObj.getProperties().get("y") / tileSize - currentMapHeight / 2),
//							game.getEntMan().getScreen());
//					break;
				default:
					enemy = null;
					break;
				}

				if (enemy != null) {
					game.getEntMan().addEntity(enemy);
				}
			}
		}

		for (final MapObject teleportObj : teleports) {
			final boolean spawnOnMapLoad = teleportObj.getProperties().get("MapSpawn", Boolean.class);

			if (spawnOnMapLoad) {
				mapLoadSpawnPosition.set((float) teleportObj.getProperties().get("x") / tileSize - currentMapWidth / 2,
						(float) teleportObj.getProperties().get("y") / tileSize - currentMapHeight / 2);
			}
		}
	}
}

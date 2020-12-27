package mysko.pilzhere.fox3d.models;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.Foxenstein3D;
import mysko.pilzhere.fox3d.constants.Constants;

public class ModelMaker {

	final Foxenstein3D game;

	private final ModelBuilder mdlBuilder;

	public Model mdlGrid = new Model();

	public Model mdlWallNorth = new Model();
	public Model mdlWallSouth = new Model();
	public Model mdlWallWest = new Model();
	public Model mdlWallEast = new Model();
	public Model mdlFloor = new Model();
	public Model mdlCeiling = new Model();

	public Model mdlDoor = new Model();

	public Model mdlEnemy = new Model();

	public ModelMaker(final Foxenstein3D game) {
		this.game = game;

		mdlBuilder = new ModelBuilder();

		buildModels();
	}

	private void buildModels() {
		mdlGrid = mdlBuilder.createLineGrid(10, 10, 1, 1, new Material(), Usage.Position | Usage.Normal);

//		ENEMY
//			final TextureRegion texRegNorth = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01),
//					32, 16, -16, 16); // flip x

		final TextureAttribute taEnemy = new TextureAttribute(TextureAttribute.Diffuse);
		taEnemy.textureDescription.minFilter = TextureFilter.Nearest;
		taEnemy.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matEnemy = new Material();
		matEnemy.set(taEnemy);

		mdlEnemy = mdlBuilder.createRect(Constants.HALF_UNIT, Constants.HALF_UNIT, 0, -Constants.HALF_UNIT,
				Constants.HALF_UNIT, 0, -Constants.HALF_UNIT, -Constants.HALF_UNIT, 0, Constants.HALF_UNIT,
				-Constants.HALF_UNIT, 0, 0, 0, -1, matEnemy, Usage.Position | Usage.Normal | Usage.TextureCoordinates);

//	NORTH WALL
		final TextureRegion texRegNorth = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01),
				32, 16, -16, 16); // flip x

		final TextureAttribute taNorth = new TextureAttribute(TextureAttribute.Diffuse, texRegNorth);
		taNorth.textureDescription.minFilter = TextureFilter.Nearest;
		taNorth.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matNorth = new Material();
		matNorth.set(taNorth);

		mdlWallNorth = mdlBuilder.createRect(Constants.HALF_UNIT, Constants.HALF_UNIT, 0, -Constants.HALF_UNIT,
				Constants.HALF_UNIT, 0, -Constants.HALF_UNIT, -Constants.HALF_UNIT, 0, Constants.HALF_UNIT,
				-Constants.HALF_UNIT, 0, 0, 0, -1, matNorth, Usage.Position | Usage.Normal | Usage.TextureCoordinates);

//	mdlInstWallNorth = new ModelInstance(mdlWallNorth);

//	SOUTH WALL
		final TextureRegion texRegSouth = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01),
				64, 16, -16, 16); // flip x

		final TextureAttribute taSouth = new TextureAttribute(TextureAttribute.Diffuse, texRegSouth);
		taSouth.textureDescription.minFilter = TextureFilter.Nearest;
		taSouth.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matSouth = new Material();
		matSouth.set(taSouth);

		mdlWallSouth = mdlBuilder.createRect(Constants.HALF_UNIT, Constants.HALF_UNIT, 0, -Constants.HALF_UNIT,
				Constants.HALF_UNIT, 0, -Constants.HALF_UNIT, -Constants.HALF_UNIT, 0, Constants.HALF_UNIT,
				-Constants.HALF_UNIT, 0, 0, 0, -1, matSouth, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		mdlWallSouth.nodes.get(0).rotation.set(Vector3.Y, 180f);

//	mdlInstWallSouth = new ModelInstance(mdlWallSouth);

//	WEST WALL
		final TextureRegion texRegWest = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01), 0,
				16, 16, 16);

		final TextureAttribute taWest = new TextureAttribute(TextureAttribute.Diffuse, texRegWest);
		taWest.textureDescription.minFilter = TextureFilter.Nearest;
		taWest.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matWest = new Material();
		matWest.set(taWest);

		mdlWallWest = mdlBuilder.createRect(Constants.HALF_UNIT, Constants.HALF_UNIT, 0, -Constants.HALF_UNIT,
				Constants.HALF_UNIT, 0, -Constants.HALF_UNIT, -Constants.HALF_UNIT, 0, Constants.HALF_UNIT,
				-Constants.HALF_UNIT, 0, 0, 0, -1, matWest, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		mdlWallWest.nodes.get(0).rotation.set(Vector3.Y, -90f);

//	mdlInstWallWest = new ModelInstance(mdlWallWest);

//	EAST WALL
		final TextureRegion texRegEast = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01), 48,
				16, -16, 16); // flip y

		final TextureAttribute taEast = new TextureAttribute(TextureAttribute.Diffuse, texRegEast);
		taEast.textureDescription.minFilter = TextureFilter.Nearest;
		taEast.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matEast = new Material();
		matEast.set(taEast);

		mdlWallEast = mdlBuilder.createRect(Constants.HALF_UNIT, Constants.HALF_UNIT, 0, -Constants.HALF_UNIT,
				Constants.HALF_UNIT, 0, -Constants.HALF_UNIT, -Constants.HALF_UNIT, 0, Constants.HALF_UNIT,
				-Constants.HALF_UNIT, 0, 0, 0, -1, matEast, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		mdlWallEast.nodes.get(0).rotation.set(Vector3.Y, 90f);

//	mdlInstWallEast = new ModelInstance(mdlWallEast);

//	FLOOR
		final TextureRegion texRegFloor = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01),
				32, 32, -16, 16); // flip x

		final TextureAttribute taFloor = new TextureAttribute(TextureAttribute.Diffuse, texRegFloor);
		taFloor.textureDescription.minFilter = TextureFilter.Nearest;
		taFloor.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matFloor = new Material();
		matFloor.set(taFloor);

		mdlFloor = mdlBuilder.createRect(Constants.HALF_UNIT, Constants.HALF_UNIT, 0, -Constants.HALF_UNIT,
				Constants.HALF_UNIT, 0, -Constants.HALF_UNIT, -Constants.HALF_UNIT, 0, Constants.HALF_UNIT,
				-Constants.HALF_UNIT, 0, 0, 1, 0, matFloor, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		mdlFloor.nodes.get(0).rotation.set(Vector3.X, 90f);

//	mdlInstFloor = new ModelInstance(mdlFloor);

//		CEILING
		final TextureRegion texRegCeiling = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01),
				32, 0, -16, 16); // flip x

		final TextureAttribute taCeiling = new TextureAttribute(TextureAttribute.Diffuse, texRegCeiling);
		taCeiling.textureDescription.minFilter = TextureFilter.Nearest;
		taCeiling.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matCeiling = new Material();
		matCeiling.set(taCeiling);

		mdlCeiling = mdlBuilder.createRect(Constants.HALF_UNIT, Constants.HALF_UNIT, 0, -Constants.HALF_UNIT,
				Constants.HALF_UNIT, 0, -Constants.HALF_UNIT, -Constants.HALF_UNIT, 0, Constants.HALF_UNIT,
				-Constants.HALF_UNIT, 0, 0, -1, 0, matCeiling,
				Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		mdlCeiling.nodes.get(0).rotation.set(Vector3.X, -90f); // not totally correct. Should flip Y. Maybe not needed.

//		mdlInstCeiling = new ModelInstance(mdlCeiling);

//	DOOR
		final TextureRegion texRegDoorNorth = new TextureRegion(
				(Texture) game.getAssMan().get(game.getAssMan().atlas01), 96, 48, -16, 16);
		final TextureRegion texRegDoorSouth = new TextureRegion(
				(Texture) game.getAssMan().get(game.getAssMan().atlas01), 80, 48, 16, 16);
		final TextureRegion texRegDoorMiddle = new TextureRegion(
				(Texture) game.getAssMan().get(game.getAssMan().atlas01), 100, 48, -4, 16);

		final TextureAttribute taDoorNorth = new TextureAttribute(TextureAttribute.Diffuse, texRegDoorNorth);
		taDoorNorth.textureDescription.minFilter = TextureFilter.Nearest;
		taDoorNorth.textureDescription.magFilter = TextureFilter.Nearest;

		final TextureAttribute taDoorSouth = new TextureAttribute(TextureAttribute.Diffuse, texRegDoorSouth);
		taDoorSouth.textureDescription.minFilter = TextureFilter.Nearest;
		taDoorSouth.textureDescription.magFilter = TextureFilter.Nearest;

		final TextureAttribute taDoorMiddle = new TextureAttribute(TextureAttribute.Diffuse, texRegDoorMiddle);
		taDoorMiddle.textureDescription.minFilter = TextureFilter.Nearest;
		taDoorMiddle.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matDoorNorth = new Material();
		matDoorNorth.set(taDoorNorth);
		final Material matDoorSouth = new Material();
		matDoorSouth.set(taDoorSouth);
		final Material matDoorMiddle = new Material();
		matDoorMiddle.set(taDoorMiddle);

		mdlBuilder.begin();
		MeshPartBuilder meshBuilder;
		final Node node0 = mdlBuilder.node();
		meshBuilder = mdlBuilder.part("northSide", GL20.GL_TRIANGLES,
				Usage.Position | Usage.Normal | Usage.TextureCoordinates, matDoorNorth);
		meshBuilder.rect(new Vector3(Constants.HALF_UNIT, Constants.HALF_UNIT, 0),
				new Vector3(-Constants.HALF_UNIT, Constants.HALF_UNIT, 0),
				new Vector3(-Constants.HALF_UNIT, -Constants.HALF_UNIT, 0),
				new Vector3(Constants.HALF_UNIT, -Constants.HALF_UNIT, 0), new Vector3(0, 0, -1));
		node0.translation.set(0, 0, Constants.PPU * 2);

		final Node node1 = mdlBuilder.node();
		meshBuilder = mdlBuilder.part("southSide", GL20.GL_TRIANGLES,
				Usage.Position | Usage.Normal | Usage.TextureCoordinates, matDoorSouth);
		meshBuilder.rect(new Vector3(Constants.HALF_UNIT, Constants.HALF_UNIT, 0),
				new Vector3(-Constants.HALF_UNIT, Constants.HALF_UNIT, 0),
				new Vector3(-Constants.HALF_UNIT, -Constants.HALF_UNIT, 0),
				new Vector3(Constants.HALF_UNIT, -Constants.HALF_UNIT, 0), new Vector3(0, 0, 1));

		node1.rotation.set(Vector3.Y, 180f);
		node1.translation.set(0, 0, Constants.PPU * -2);

		final Node node2 = mdlBuilder.node();
		meshBuilder = mdlBuilder.part("middleSide", GL20.GL_TRIANGLES,
				Usage.Position | Usage.Normal | Usage.TextureCoordinates, matDoorMiddle);
		meshBuilder.rect(new Vector3(Constants.PPU * 2, Constants.HALF_UNIT, 0),
				new Vector3(-Constants.PPU * 2, Constants.HALF_UNIT, 0),
				new Vector3(-Constants.PPU * 2, -Constants.HALF_UNIT, 0),
				new Vector3(Constants.PPU * 2, -Constants.HALF_UNIT, 0), new Vector3(0, 0, -1));

		node2.rotation.set(Vector3.Y, -90f);
		node2.translation.set(-0.5f, 0, 0);
		mdlDoor = mdlBuilder.end();
	}
}

package mysko.pilzhere.fox3d.cellbuilder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.Foxenstein3D;

public class Cell3DBuilder {

	final Foxenstein3D game;

	float halfUnit = 0.5f;

	public Model mdlGrid = new Model();

	public Model mdlWallNorth = new Model();
	public Model mdlWallSouth = new Model();
	public Model mdlWallWest = new Model();
	public Model mdlWallEast = new Model();
	public Model mdlFloor = new Model();
	public Model mdlCeiling = new Model();

	private final ModelBuilder mdlBuilder;

	public Cell3DBuilder(final Foxenstein3D game) {
		this.game = game;

		mdlBuilder = new ModelBuilder();

		buildModels();
	}

	private void buildModels() {
		mdlGrid = mdlBuilder.createLineGrid(10, 10, 1, 1, new Material(), Usage.Position | Usage.Normal);

//	NORTH WALL
		final TextureRegion texRegNorth = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01),
				16, 32, 16, -16); // flip y

		final TextureAttribute taNorth = new TextureAttribute(TextureAttribute.Diffuse, texRegNorth);
		taNorth.textureDescription.minFilter = TextureFilter.Nearest;
		taNorth.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matNorth = new Material();
		matNorth.set(taNorth);

		mdlWallNorth = mdlBuilder.createRect(halfUnit, halfUnit, 0, -halfUnit, halfUnit, 0, -halfUnit, -halfUnit, 0,
				halfUnit, -halfUnit, 0, 0, 0, -1, matNorth, Usage.Position | Usage.Normal | Usage.TextureCoordinates);

//	mdlInstWallNorth = new ModelInstance(mdlWallNorth);

//	SOUTH WALL
		final TextureRegion texRegSouth = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01),
				48, 32, 16, -16); // flip y

		final TextureAttribute taSouth = new TextureAttribute(TextureAttribute.Diffuse, texRegSouth);
		taSouth.textureDescription.minFilter = TextureFilter.Nearest;
		taSouth.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matSouth = new Material();
		matSouth.set(taSouth);

		mdlWallSouth = mdlBuilder.createRect(halfUnit, halfUnit, 0, -halfUnit, halfUnit, 0, -halfUnit, -halfUnit, 0,
				halfUnit, -halfUnit, 0, 0, 0, -1, matSouth, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		mdlWallSouth.nodes.get(0).rotation.set(Vector3.Y, 180f);

//	mdlInstWallSouth = new ModelInstance(mdlWallSouth);

//	WEST WALL
		final TextureRegion texRegWest = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01), 0,
				32, 16, -16); // flip y

		final TextureAttribute taWest = new TextureAttribute(TextureAttribute.Diffuse, texRegWest);
		taWest.textureDescription.minFilter = TextureFilter.Nearest;
		taWest.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matWest = new Material();
		matWest.set(taWest);

		mdlWallWest = mdlBuilder.createRect(halfUnit, halfUnit, 0, -halfUnit, halfUnit, 0, -halfUnit, -halfUnit, 0,
				halfUnit, -halfUnit, 0, 0, 0, -1, matWest, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		mdlWallWest.nodes.get(0).rotation.set(Vector3.Y, -90f);

//	mdlInstWallWest = new ModelInstance(mdlWallWest);

//	EAST WALL
		final TextureRegion texRegEast = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01), 32,
				32, 16, -16); // flip y

		final TextureAttribute taEast = new TextureAttribute(TextureAttribute.Diffuse, texRegEast);
		taEast.textureDescription.minFilter = TextureFilter.Nearest;
		taEast.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matEast = new Material();
		matEast.set(taEast);

		mdlWallEast = mdlBuilder.createRect(halfUnit, halfUnit, 0, -halfUnit, halfUnit, 0, -halfUnit, -halfUnit, 0,
				halfUnit, -halfUnit, 0, 0, 0, -1, matEast, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		mdlWallEast.nodes.get(0).rotation.set(Vector3.Y, 90f);

//	mdlInstWallEast = new ModelInstance(mdlWallEast);

//	FLOOR
		final TextureRegion texRegFloor = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01),
				16, 48, 16, -16); // flip y

		final TextureAttribute taFloor = new TextureAttribute(TextureAttribute.Diffuse, texRegFloor);
		taFloor.textureDescription.minFilter = TextureFilter.Nearest;
		taFloor.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matFloor = new Material();
		matFloor.set(taFloor);

		mdlFloor = mdlBuilder.createRect(halfUnit, halfUnit, 0, -halfUnit, halfUnit, 0, -halfUnit, -halfUnit, 0,
				halfUnit, -halfUnit, 0, 0, 0, -1, matFloor, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		mdlFloor.nodes.get(0).rotation.set(Vector3.X, -90f);

//	mdlInstFloor = new ModelInstance(mdlFloor);

//		CEILING
		final TextureRegion texRegCeiling = new TextureRegion((Texture) game.getAssMan().get(game.getAssMan().atlas01),
				16, 16, 16, -16); // flip y

		final TextureAttribute taCeiling = new TextureAttribute(TextureAttribute.Diffuse, texRegCeiling);
		taCeiling.textureDescription.minFilter = TextureFilter.Nearest;
		taCeiling.textureDescription.magFilter = TextureFilter.Nearest;

		final Material matCeiling = new Material();
		matCeiling.set(taCeiling);

		mdlCeiling = mdlBuilder.createRect(halfUnit, halfUnit, 0, -halfUnit, halfUnit, 0, -halfUnit, -halfUnit, 0,
				halfUnit, -halfUnit, 0, 0, 0, -1, matCeiling, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		mdlCeiling.nodes.get(0).rotation.set(Vector3.X, 90f); // not totally correct. Should flip Y. Maybe not needed.

//		mdlInstCeiling = new ModelInstance(mdlCeiling);
	}
}

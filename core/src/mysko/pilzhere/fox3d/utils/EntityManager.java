package mysko.pilzhere.fox3d.utils;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Array;

import mysko.pilzhere.fox3d.entities.Entity;
import mysko.pilzhere.fox3d.screens.GameScreen;

public class EntityManager {
	private int nextId = 0;

	public final Array<Entity> entities = new Array<>();

	private GameScreen screen;

	public void addEntity(final Entity ent) {
		entities.add(ent);
	}

	public int assignId() {
		nextId++;
//		System.out.println("New entity added ID: " + (nextId - 1));
		return nextId - 1;
	}

	public Entity getEntityFromId(final int id) {
		for (int i = 0; i < entities.size; i++) {
			if (entities.get(i).getId() == id) {
				return entities.get(i);
			}
		}

		return null;
	}

	public GameScreen getScreen() {
		return screen;
	}

	public void removeEntity(final int id) {
		for (int i = 0; i < entities.size; i++) {
			if (id == entities.get(i).getId()) {
				entities.removeIndex(i);
				break;
			}
		}
	}

	public void render2DAllEntities(final float delta) {
		for (final Entity ent : entities) {
			if (ent.shouldRender2D()) {
				ent.render2D(delta);
			}
		}
	}

	public void render3DAllEntities(final ModelBatch mdlBatch, final Environment env, final float delta) {
		for (final Entity ent : entities) {
//			ent.setRender3D(false);

			if (ent.shouldRender3D()) {
				ent.render3D(mdlBatch, env, delta);
			}

		}
	}

	public void setScreen(final GameScreen screen) {
		this.screen = screen;
	}

	public void spawnEntity(final Entity ent) {
		entities.add(ent);
	}

	public void tickAllEntities(final float delta) {
		for (final Entity ent : entities) {
			if (ent.shouldTick()) {
				ent.tick(delta);
			}
		}
	}
}

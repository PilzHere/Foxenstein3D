package mysko.pilzhere.fox3d.entities;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import mysko.pilzhere.fox3d.rect.RectanglePlus;
import mysko.pilzhere.fox3d.screens.GameScreen;

public class Entity {
	public final GameScreen screen;
	protected int id;
	protected boolean tick = true;
	protected boolean render2D = true;
	protected boolean render3D = true;
	protected boolean destroy = false;

	protected Entity collidedEntity = null;

	public Entity(final GameScreen screen) {
		this.screen = screen;

		id = screen.game.getEntMan().assignId();
	}

	public void destroy() {
		if (destroy) {
			screen.game.getEntMan().removeEntity(id);
		}
	}

	public int getId() {
		return id;
	}

	public void onCollision(final RectanglePlus otherRect) {
		collidedEntity = screen.game.getEntMan().getEntityFromId(otherRect.getConnectedEntityId());
	}

	public void render2D(final float delta) {

	}

	public void render3D(final ModelBatch mdlBatch, final Environment env, final float delta) {

	}

	public void setDestroy(final boolean destroy) {
		this.destroy = destroy;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setRender2D(final boolean render) {
		this.render2D = render;
	}

	public void setRender3D(final boolean render3d) {
		render3D = render3d;
	}

	public void setTick(final boolean tick) {
		this.tick = tick;
	}

	public boolean shouldDestroy() {
		return destroy;
	}

	public boolean shouldRender2D() {
		return render2D;
	}

	public boolean shouldRender3D() {
		return render3D;
	}

	public boolean shouldTick() {
		return tick;
	}

	public void tick(final float delta) {

	}

}

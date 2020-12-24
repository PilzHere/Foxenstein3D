package mysko.pilzhere.fox3d.entities.enemies.ai;

import mysko.pilzhere.fox3d.Entity;

public class EnemyAI {
	protected enum AiState {
		IDLE, MOVING, ATTACKING;
	}

	protected Entity parent;
	protected AiState aiState = AiState.IDLE;

	public EnemyAI(final Entity parent) {
		this.parent = parent;
	}

	public void tick(final float delta) {
	}
}

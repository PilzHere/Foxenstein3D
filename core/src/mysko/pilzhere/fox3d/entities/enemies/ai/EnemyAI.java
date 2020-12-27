package mysko.pilzhere.fox3d.entities.enemies.ai;

import mysko.pilzhere.fox3d.entities.enemies.Enemy;

public class EnemyAI {
	protected enum AiState {
		IDLE, MOVING, ATTACKING;
	}

	protected Enemy parent;
	protected AiState aiState = AiState.IDLE;

	public EnemyAI(final Enemy parent) {
		this.parent = parent;
	}

	public void tick(final float delta) {

	}
}

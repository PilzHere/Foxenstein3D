package mysko.pilzhere.fox3d.entities.enemies.ai;

import com.badlogic.gdx.math.Vector2;

import mysko.pilzhere.fox3d.entities.enemies.Enemy;
import mysko.pilzhere.fox3d.entities.enemies.Fireball;

public class FireballAI extends EnemyAI {
	private final Vector2 rectTargetPos = new Vector2();
	private final Vector2 rectDirection = new Vector2();
	private final Vector2 currentRectPos = new Vector2();

	public boolean targetPosSet = false;
//	private float distanceFromTargetPos;
//	public boolean targetPosReached = false;

	private final float moveSpeed = 13.333f;
//	private final float targetPosRandomMaxMin = 2f;
//	private final float inRangeDistance = Constants.HALF_UNIT;

//	private final boolean timerMovementSet = false;
//	private long timerMovementStart;
//	private long timerMovementEnd;
//	private final long timerMovementTime = 1000L;

	public FireballAI(final Enemy parent) {
		super(parent);
	}

	@Override
	public void tick(final float delta) {
		currentRectPos.set(parent.getRect().x, parent.getRect().y);

		switch (aiState) {
		case IDLE:
			aiState = AiState.MOVING;
			break;
		case MOVING:
			if (!targetPosSet) {
				rectTargetPos.set(((Fireball) parent).targetPosition.cpy());

				rectDirection.x = rectTargetPos.x - currentRectPos.x;
				rectDirection.y = rectTargetPos.y - currentRectPos.y;

				targetPosSet = true;
			} else {
				rectDirection.nor().scl(moveSpeed * delta);
				parent.getRect().newPosition.add(rectDirection.x, rectDirection.y);
			}
		case ATTACKING:
			aiState = AiState.IDLE;
			break;
		default:
			aiState = AiState.IDLE;
			break;
		}
	}

}

package mysko.pilzhere.fox3d.entities.enemies.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import mysko.pilzhere.fox3d.constants.Constants;
import mysko.pilzhere.fox3d.entities.enemies.Enemy;

public class EyeAI extends EnemyAI {
	private final Vector2 rectTargetPos = new Vector2();
	private final Vector2 rectDirection = new Vector2();
	private final Vector2 currentRectPos = new Vector2();

	public boolean targetPosSet = false;
	private float distanceFromTargetPos;
	public boolean targetPosReached = false;

	private final float moveSpeed = 1f;
	private final float targetPosRandomMaxMin = 2f;
	private final float inRangeDistance = Constants.HALF_UNIT;

	private boolean timerSet = false;
	private long timerStart;
	private long timerEnd;
	private final long timerTime = 1000L;

	public EyeAI(final Enemy parent) {
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
				rectTargetPos.set(currentRectPos.x + MathUtils.random(-targetPosRandomMaxMin, targetPosRandomMaxMin),
						currentRectPos.y + MathUtils.random(-targetPosRandomMaxMin, targetPosRandomMaxMin));

				timerStart = System.currentTimeMillis();
				timerEnd = timerStart + timerTime;
				timerSet = true;

				targetPosSet = true;
			} else {
				if (timerSet) {
					if (System.currentTimeMillis() >= timerEnd) {
//						System.out.println("time!");
						timerSet = false;
						targetPosSet = false;
						break;
					} else {
						rectDirection.x = rectTargetPos.x - currentRectPos.x;
						rectDirection.y = rectTargetPos.y - currentRectPos.y;

						rectDirection.nor().scl(moveSpeed * delta);

						distanceFromTargetPos = Vector2.dst(currentRectPos.x, currentRectPos.y, rectTargetPos.x,
								rectTargetPos.y);

						if (distanceFromTargetPos < inRangeDistance) {
//							System.out.println("close enough!");
							timerSet = false;
							targetPosSet = false;
							break;
						} else {
							parent.getRect().newPosition.add(rectDirection.x, rectDirection.y);
						}
					}
				}
			}
			break;
		case ATTACKING:
			break;
		default:
			aiState = AiState.IDLE;
			break;
		}
	}

}

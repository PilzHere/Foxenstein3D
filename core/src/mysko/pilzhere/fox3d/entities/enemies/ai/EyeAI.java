package mysko.pilzhere.fox3d.entities.enemies.ai;

import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.Entity;
import mysko.pilzhere.fox3d.entities.enemies.Eye;

public class EyeAI extends EnemyAI {

//	public Vector3 targetPos = new Vector3();
//	public Vector3 direction = new Vector3();
	public Vector3 currentPos = new Vector3();

//	public boolean hasSetNextPosition = false;

//	private final float moveSpeed = 1f;
//	float maxDistance = 0.5f;

//	Vector3 tmp = new Vector3();

	public EyeAI(final Entity parent) {
		super(parent);
	}

	@Override
	public void tick(final float delta) {
		currentPos.set(((Eye) parent).getPosition().cpy());

		switch (aiState) {
		case IDLE:
			aiState = AiState.MOVING;
			break;
		case MOVING:

			break;
		case ATTACKING:
			break;
		default:
			aiState = AiState.IDLE;
			break;
		}
	}

}

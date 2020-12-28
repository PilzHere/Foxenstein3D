package mysko.pilzhere.fox3d.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import mysko.pilzhere.fox3d.constants.Constants;
import mysko.pilzhere.fox3d.entities.Entity;
import mysko.pilzhere.fox3d.entities.IUsable;
import mysko.pilzhere.fox3d.entities.enemies.Enemy;
import mysko.pilzhere.fox3d.rect.RectanglePlus;
import mysko.pilzhere.fox3d.rect.filters.RectanglePlusFilter;
import mysko.pilzhere.fox3d.screens.GameScreen;
import mysko.pilzhere.fox3d.screens.PlayScreen;

public class Player extends Entity {
	private final Vector3 movementDir = new Vector3();

	final Vector2 movementDirVec2 = new Vector2(movementDir.x, movementDir.z);

	private final float cameraRotationSpeed = 100f;

	private final float playerMoveSpeed = 4f;
	private final int maxHP = 100;
	private int currentHP = 100;
	public final PerspectiveCamera playerCam;

	public RectanglePlus rect;
	private IUsable currentUsableInterface;

	public boolean isDead = false;

	private boolean shootTimerSet = false;
	private long shootTimerEnd;
	private final long shootTimerCD = 1500L;

	public boolean shootAnimationTimerSet = false;
	private long shootAnimationTimerEnd;
	private final long shootAnimationTimerCD = 1000L;

	private final TextureRegion guiGun, guiGunShoot;
	public TextureRegion guiCurrentGun;

	float camY = Constants.HALF_UNIT;
	private final float gunYStart = -3;
	public float gunY = gunYStart;
	float headbobSpeed = 5f;

	public boolean hasRedKeycard = false;
	public boolean hasGreenKeycard = false;
	public boolean hasBlueKeycard = false;
	public boolean hasGoldenKeycard = false;

//	public final int[] inventorySlots;
	public int currentInventorySlot = 1;

	boolean headbob = false;

	public Player(final GameScreen screen) {
		super(screen);

		guiGun = new TextureRegion((Texture) screen.game.getAssMan().get(screen.game.getAssMan().guiGun), 0, 0, 32, 64);
		guiGunShoot = new TextureRegion((Texture) screen.game.getAssMan().get(screen.game.getAssMan().guiGun), 32, 0,
				32, 64);
		guiCurrentGun = guiGun;

		playerCam = new PerspectiveCamera(70, 640, 480);
		playerCam.position.set(new Vector3(0, 0.5f, 0));
		playerCam.lookAt(new Vector3(0, 0.5f, 1));
		playerCam.near = 0.01f;
		playerCam.far = 10f;
		playerCam.update();

		rect = new RectanglePlus(screen.game.getMapBuilder().mapLoadSpawnPosition.x + Constants.HALF_UNIT - 0.25f / 2f,
				screen.game.getMapBuilder().mapLoadSpawnPosition.y + Constants.HALF_UNIT - 0.25f / 2f, 0.25f, 0.25f, id,
				RectanglePlusFilter.PLAYER);
		rect.oldPosition.set(rect.x, rect.y);
		rect.newPosition.set(rect.x, rect.y); // Needed for spawning at correct position.
		screen.game.getRectMan().addRect(rect); // never forget!

		playerCam.position.set(rect.x + rect.width / 2f, 0.5f, rect.y + rect.height / 2f);

//		inventorySlots = new int[6];
//		currentInventorySlot = inventorySlots[0];
	}

	public void addHP(final int addHP) {
		this.currentHP += addHP;

		if (currentHP > maxHP) {
			currentHP = maxHP;
		}

//		System.out.println("Current HP: " + currentHP);
	}

	@Override
	public void destroy() {
		if (destroy) {
			screen.game.getRectMan().removeRect(rect);
		}

		super.destroy(); // should be last.
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public final void getEnemyInRangeAroundCam() {
		RectanglePlus closestSecondRect = null;
		float currentClosestDistanceBetweenRects = 75f;
		float currentDistanceBetweenRects = currentClosestDistanceBetweenRects;
		RectanglePlus secondRect = null;

		for (final RectanglePlus firstRect : screen.game.getRectMan().rects) {
			currentClosestDistanceBetweenRects = 75f;
			currentDistanceBetweenRects = currentClosestDistanceBetweenRects;
			closestSecondRect = null;
			secondRect = null;

			if (firstRect.filter == RectanglePlusFilter.ENEMY) { // check only from enemies
				((Enemy) screen.game.getEntMan().getEntityFromId(firstRect.getConnectedEntityId()))
						.setIsPlayerInRange(false);

				for (int i = 0; i < screen.game.getRectMan().rects.size; i++) {
					secondRect = screen.game.getRectMan().rects.get(i);

					if (firstRect != secondRect) {
						if (Intersector.intersectSegmentRectangle(firstRect.x + firstRect.getWidth() / 2f,
								firstRect.y + firstRect.getHeight() / 2f, playerCam.position.x, playerCam.position.z,
								secondRect)) {

							currentDistanceBetweenRects = Vector2.dst2(secondRect.x + secondRect.getWidth() / 2f,
									secondRect.y + secondRect.getHeight() / 2f, firstRect.x + firstRect.getWidth() / 2f,
									firstRect.y + firstRect.getHeight() / 2f);

							if (currentDistanceBetweenRects < currentClosestDistanceBetweenRects) {
								closestSecondRect = secondRect;
								currentClosestDistanceBetweenRects = currentDistanceBetweenRects;
							}

							if (closestSecondRect != null) {
								if (closestSecondRect.filter == RectanglePlusFilter.PLAYER) {
									((Enemy) screen.game.getEntMan().getEntityFromId(firstRect.getConnectedEntityId()))
											.setIsPlayerInRange(true);
//									System.out.println("Enemy sees u!");
								}
							}
						}
					}
				}
			}
		}
	}

	public final void getEnemyRectInRangeFromCam() {
		final float rangeDistanceFromCam = 75f;
		float currentRectInRangeDistance = rangeDistanceFromCam;
		float closestRectInRangeDistance = rangeDistanceFromCam;
		RectanglePlus closestRectInRange = null;

		for (final RectanglePlus rect : screen.game.getRectMan().rects) {
			if (Intersector.intersectSegmentRectangle(playerCam.position.x, playerCam.position.z,
					playerCam.position.x + playerCam.direction.x * rangeDistanceFromCam,
					playerCam.position.z + playerCam.direction.z * rangeDistanceFromCam, rect)) {

				currentRectInRangeDistance = Vector2.dst2(playerCam.position.x, playerCam.position.z,
						rect.x + rect.getWidth() / 2, rect.y + rect.getHeight() / 2);

				if (currentRectInRangeDistance < closestRectInRangeDistance) {
					closestRectInRange = rect;
					closestRectInRangeDistance = currentRectInRangeDistance;
				}

				if (closestRectInRange != null) {
//					System.out.println(closestRectInRange.filter);

					if (closestRectInRange.filter == RectanglePlusFilter.ENEMY) {
						if ((Enemy) screen.game.getEntMan()
								.getEntityFromId(closestRectInRange.getConnectedEntityId()) != null) {
							((Enemy) screen.game.getEntMan().getEntityFromId(closestRectInRange.getConnectedEntityId()))
									.subHp(20);
						}
					}
				}
			}
		}
	}

	public final void getRectInRangeFromCam() {
		final float rangeDistanceFromCam = 2f;
		float currentRectInRangeDistance = rangeDistanceFromCam;
		float closestRectInRangeDistance = rangeDistanceFromCam;
		RectanglePlus closestRectInRange = null;
		currentUsableInterface = null;
		closestRectInRange = null;

		for (final RectanglePlus rect : screen.game.getRectMan().rects) {
			if (Intersector.intersectSegmentRectangle(playerCam.position.x, playerCam.position.z,
					playerCam.position.x + playerCam.direction.x * rangeDistanceFromCam,
					playerCam.position.z + playerCam.direction.z * rangeDistanceFromCam, rect)) {

				currentRectInRangeDistance = Vector2.dst2(playerCam.position.x, playerCam.position.z,
						rect.x + rect.getWidth() / 2, rect.y + rect.getHeight() / 2);

				if (currentRectInRangeDistance < closestRectInRangeDistance) {
					closestRectInRange = rect;
					closestRectInRangeDistance = currentRectInRangeDistance;
				}

				if (closestRectInRange != null) {
//					System.out.println(currentRectInRangeDistance);

					if (screen.game.getEntMan()
							.getEntityFromId(closestRectInRange.getConnectedEntityId()) instanceof IUsable) {
						currentUsableInterface = (IUsable) screen.game.getEntMan()
								.getEntityFromId(closestRectInRange.getConnectedEntityId());
					}
				}
			}
		}
	}

	public void handleInput(final float delta) {
		movementDir.setZero();

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			shoot();
		}

		if (Gdx.input.getDeltaX() != 0) {
			playerCam.rotate(Vector3.Y, Gdx.input.getDeltaX() * -cameraRotationSpeed * delta);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			movementDir.add(playerCam.direction.cpy());
			headbob = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			movementDir.sub(playerCam.direction.cpy());
			headbob = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			movementDir.sub(playerCam.direction.cpy().crs(playerCam.up));
			headbob = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			movementDir.add(playerCam.direction.cpy().crs(playerCam.up));
			headbob = true;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			useUsableInterface(currentUsableInterface);
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			currentInventorySlot = 1;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			currentInventorySlot = 2;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			currentInventorySlot = 3;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
			currentInventorySlot = 4;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
			currentInventorySlot = 5;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
			currentInventorySlot = 6;
		}

		if (headbob) {
			camY = Constants.HALF_UNIT;
			final float sinOffset = (float) (Math.sin(screen.game.getTimeSinceLaunch() * playerMoveSpeed * 4f)
					* 0.01875f);
			camY += sinOffset;

			gunY = gunYStart;
			gunY += sinOffset * 200f;

			headbob = false;
		}

		movementDirVec2.set(movementDir.x, movementDir.z);
		rect.newPosition.set(
				rect.getPosition(new Vector2()).cpy().add(movementDirVec2.nor().cpy().scl(playerMoveSpeed * delta)));
	}

	@Override
	public void onCollision(final RectanglePlus otherRect) {
		super.onCollision(otherRect);

		if (otherRect.filter == RectanglePlusFilter.ITEM) {
			((PlayScreen) screen).playItemSound();
		}
	}

	private void shoot() {
		if (!shootTimerSet) {
			shootTimerEnd = System.currentTimeMillis() + shootTimerCD;

			if (!shootAnimationTimerSet) {
				shootAnimationTimerEnd = System.currentTimeMillis() + shootAnimationTimerCD;
				guiCurrentGun = guiGunShoot;
				shootAnimationTimerSet = true;
			}

			getEnemyRectInRangeFromCam();
//			System.out.println("shot");

			shootTimerSet = true;
		}
	}

	public void subHP(final int subHP) {
		this.currentHP -= subHP;

		if (currentHP < 0) {
			currentHP = 0;
		}

//		System.out.println("Current HP: " + currentHP);
	}

	@Override
	public void tick(final float delta) {
		if (currentHP == 0) {
			isDead = true;
//			System.out.println("Player is dead.");
		}

		if (shootTimerSet) {
			if (System.currentTimeMillis() >= shootTimerEnd) {
				shootAnimationTimerSet = false;
				shootTimerSet = false;
			}
		}

		if (shootAnimationTimerSet) {
			if (System.currentTimeMillis() >= shootAnimationTimerEnd) {
				shootAnimationTimerSet = false;
			}
		}

		if (!shootAnimationTimerSet) {
			guiCurrentGun = guiGun;
		}

		screen.checkOverlaps(rect, delta);

		playerCam.position.set(rect.x + rect.width / 2f, camY, rect.y + rect.height / 2f);

		getEnemyInRangeAroundCam();
		getRectInRangeFromCam();

		rect.oldPosition.set(rect.x, rect.y);
	}

	private void useUsableInterface(final IUsable usableInterface) {
		if (currentUsableInterface != null) {
			usableInterface.onUse();
		}
	}
}

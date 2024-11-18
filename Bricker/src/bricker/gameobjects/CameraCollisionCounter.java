package bricker.gameobjects;

import bricker.brick_strategies.CameraCollisionStrategy;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class CameraCollisionCounter extends GameObject {
    private final Ball ball;
    private    int cameraCounter;
    private final CameraCollisionStrategy strategy;
    private int currCounter;

    //
    public CameraCollisionCounter(Ball ball,
                                  int cameraCounter,
                                  CameraCollisionStrategy strategy) {
        super(ball.getTopLeftCorner(),ball.getDimensions(),null);
        this.ball = ball;
        this.cameraCounter = cameraCounter;
        this.strategy = strategy;
        this.currCounter = ball.getCollisionCounter();
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);
        if (cameraCounter <= ball.getCollisionCounter() - this.currCounter) {
            strategy.turnOffCameraChange();
            strategy.getGameObjects().removeGameObject(this,Layer.BACKGROUND);
        }
    }
}

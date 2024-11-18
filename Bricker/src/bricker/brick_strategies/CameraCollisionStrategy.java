package bricker.brick_strategies;
import bricker.gameobjects.Ball;
import bricker.gameobjects.CameraCollisionCounter;
import bricker.gameobjects.Puck;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.io.Console;

public class CameraCollisionStrategy extends Strategy {
    public  final int COUNTER_RESET_CAMERA = 4;
    private WindowController windowController;
    private BrickerGameManager gameManager;
    private Ball ball;


    public CameraCollisionStrategy(CollisionStrategy strategy,
                                   WindowController windowController,
                                   BrickerGameManager gameManager) {
        super(strategy);
        this.windowController = windowController;
        this.gameManager = gameManager;

    }


    @Override
    public void onCollision(GameObject obj1, GameObject obj2, Counter counter) {
        System.out.println("Collision detected");
        super.onCollision(obj1, obj2, counter);
        boolean checkValidBall = obj2.getTag().equals("Ball"); // Check if the object is the main ball
        boolean checkWork = gameManager.camera() == null;
        if (checkWork) {
            for (GameObject gameObject : getGameObjects()) {
                if (gameObject instanceof Ball && (!(gameObject instanceof Puck))) {
                    CameraCollisionCounter ballCollisionCountdownAgent =
                            new CameraCollisionCounter((Ball) gameObject,
                                    COUNTER_RESET_CAMERA  ,
                                    this);
                    getGameObjects().addGameObject(ballCollisionCountdownAgent,Layer.BACKGROUND);
                    gameManager.setCamera(
                            new Camera(
                                    obj2, //object to follow
                                    Vector2.ZERO, //follow the center of the object
                                    windowController.getWindowDimensions().mult(1.2f), //widen the frame a bit
                                    windowController.getWindowDimensions() //share the window dimensions
                            )
                    );
                    return;
                }
            }
        }

    }

    public void turnOffCameraChange() {
        gameManager.setCamera(null);
    }
}





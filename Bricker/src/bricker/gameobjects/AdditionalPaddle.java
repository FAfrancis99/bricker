package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class AdditionalPaddle extends Paddle {


    private final GameObjectCollection gameObjectCollection;
    public static boolean ValidPaddle = true;
    private int collusionCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener
     * @param windowDim
     */
    public AdditionalPaddle(Vector2 topLeftCorner, Vector2 dimensions,
                            Renderable renderable, UserInputListener inputListener, Vector2 windowDim,
                            GameObjectCollection gameObjectCollection , int collusionCounter) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDim);
        this.gameObjectCollection = gameObjectCollection;
        this.collusionCounter = collusionCounter;
        ValidPaddle = false;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("Ball")) {
            this.collusionCounter--;
            if (this.collusionCounter == 0) {
                ValidPaddle = true;
                this.gameObjectCollection.removeGameObject(this);
            }
        }


    }
}

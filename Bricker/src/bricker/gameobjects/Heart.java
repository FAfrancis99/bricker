package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Heart extends GameObject {

    private final GameObjectCollection gameObjectCollection;
    private final Counter graphicLifeCount;
    public static boolean checkExtraHeart = false;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 GameObjectCollection gameObjectCollection, Counter graphicLifeCount) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;

        this.graphicLifeCount = graphicLifeCount;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (graphicLifeCount.value() < 4){
            checkExtraHeart = this.getTag().equals("extraLife");
            gameObjectCollection.removeGameObject(this);
        }

    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        boolean checkMainPaddle = other.getTag().equals("mainPaddle");
        boolean checkExtraPaddle = !other.getTag().equals("extraPaddle");
        return checkExtraPaddle && checkMainPaddle;
    }

}

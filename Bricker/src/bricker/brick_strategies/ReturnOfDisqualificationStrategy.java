package bricker.brick_strategies;


import bricker.gameobjects.Ball;
import bricker.gameobjects.GraphicLifeCount;
import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ReturnOfDisqualificationStrategy extends Strategy{
    private final ImageReader imageReader;
    private final Vector2 windowDimensions;
    private final Counter graphicLifeCount;

    public ReturnOfDisqualificationStrategy(CollisionStrategy strategy, ImageReader imageReader,
                                            Vector2 windowDimensions, Counter graphicLifeCount) {
        super(strategy);
        this.imageReader = imageReader;
        this.windowDimensions = windowDimensions;
        this.graphicLifeCount = graphicLifeCount;
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2, Counter counter) {
        super.onCollision(obj1, obj2, counter);
        // creating extra life:
        Renderable extraLifeImage = imageReader.readImage("assets/heart.png",
                true);
        GameObject extraHeart = new Heart(Vector2.ZERO
                ,new Vector2(20,20),
                extraLifeImage,
                getGameObjects(),graphicLifeCount);
        extraHeart.setVelocity(new Vector2(0,100));
        // Set the heart's position to the brick's position
        extraHeart.setCenter(obj1.getCenter());
        getGameObjects().addGameObject(extraHeart);
        extraHeart.setTag("extraLife");
    }
}

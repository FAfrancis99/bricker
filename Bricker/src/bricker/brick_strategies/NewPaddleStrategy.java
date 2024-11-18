package bricker.brick_strategies;

import bricker.gameobjects.AdditionalPaddle;
import danogl.GameObject;
import danogl.gui.UserInputListener;

import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import danogl.gui.ImageReader;

public class NewPaddleStrategy extends Strategy{
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;


    public NewPaddleStrategy(CollisionStrategy strategy, ImageReader imageReader,
                             UserInputListener inputListener, Vector2 windowDimensions) {
        super(strategy);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2, Counter counter) {
        super.onCollision(obj1, obj2, counter);
        // create additional paddle:
        if(AdditionalPaddle.ValidPaddle) {
            GameObject extraPaddle = new AdditionalPaddle(
                    new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2),
                    new Vector2(100, 15),
                    this.imageReader.readImage("assets/paddle.png",
                            true),
                    inputListener,
                    windowDimensions, getGameObjects(), 4);

            getGameObjects().addGameObject(extraPaddle);
            extraPaddle.setTag("extraPaddle");
        }
    }


}

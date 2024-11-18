package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCount extends GameObject {
    private Counter numericLifeCounter;
    private  GameObjectCollection gameObjects;
    private TextRenderable text;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     *
     */
    public NumericLifeCount(Vector2 topLeftCorner, Vector2 dimensions,
                            Counter numericLifeCounter, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, null);

        this.numericLifeCounter = numericLifeCounter;
        this.gameObjects = gameObjects;
        // create numeric number
        text = new TextRenderable(String.valueOf(numericLifeCounter.value()));
        text.setColor(Color.GREEN);
        gameObjects.addGameObject(new GameObject(topLeftCorner,dimensions,text), Layer.BACKGROUND);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        switch (numericLifeCounter.value()) {
            case 1:
                text.setColor(Color.RED);
                break;
            case 2:
                text.setColor(Color.YELLOW);
                break;
            default:
                text.setColor(Color.GREEN);
                break;
        }
        text.setString(String.valueOf(numericLifeCounter.value()));
    }


    public GameObjectCollection getGameObjects() {
        return gameObjects;
    }
}

package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy {
    private GameObjectCollection gameObjects;

    public BasicCollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2, Counter counter) {
        if (gameObjects.removeGameObject(obj1, Layer.STATIC_OBJECTS)){
            counter.decrement();
        }


    }

    public GameObjectCollection getGameObjects() {
        return gameObjects;
    }


}

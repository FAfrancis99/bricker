package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public interface CollisionStrategy {
    void onCollision(GameObject obj1, GameObject obj2, Counter counter);
    GameObjectCollection getGameObjects();
}

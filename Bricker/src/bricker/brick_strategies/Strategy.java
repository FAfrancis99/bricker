package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public  abstract class Strategy implements CollisionStrategy{
    private final CollisionStrategy strategy;

    public Strategy(CollisionStrategy strategy){

        this.strategy = strategy;
    }
    @Override
    public GameObjectCollection getGameObjects() {
        return strategy.getGameObjects();
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2, Counter counter) {
        strategy.onCollision(obj1,obj2,counter);
    }
}

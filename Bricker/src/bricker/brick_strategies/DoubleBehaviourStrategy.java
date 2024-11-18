package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.Random;

public class DoubleBehaviourStrategy extends Strategy{

    private static final int MAX_STRATEGIES = 3;
    private final StrategyBrickFactory factory;
    private final GameObjectCollection gameObjects;
    private final CollisionStrategy strategy;
    private int strategyCounter;
    private Random random;
    public DoubleBehaviourStrategy(StrategyBrickFactory factory, GameObjectCollection gameObjects,
                                   CollisionStrategy strategy ){
        super(strategy);
        this.factory = factory;
        this.gameObjects = gameObjects;
        this.strategy = strategy;
        random = new Random();

    }
    @Override
    public void onCollision(GameObject obj1, GameObject obj2, Counter counter) {

        int indexRandom = random.nextInt(5);
        int strategyRand1 = random.nextInt(4),strategyRand2 = random.nextInt(4);
        CollisionStrategy oneStrategy = factory.chooseSpecialStrategy(strategy
                , strategyRand1);
        CollisionStrategy twoStrategy = factory.chooseSpecialStrategy(oneStrategy, strategyRand2);

        if (indexRandom <=  MAX_STRATEGIES)       // means two strategies.
             twoStrategy.onCollision(obj1,obj2,counter);
        else {       // means three strategies.
            int strategyRand3 = random.nextInt(4);
            CollisionStrategy threeStrategy = factory.chooseSpecialStrategy(twoStrategy, strategyRand3);
            threeStrategy.onCollision(obj1,obj2,counter);
        }


    }


    @Override
    public GameObjectCollection getGameObjects() {
        return gameObjects;
    }
}

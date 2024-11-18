package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class StrategyBrickFactory {
    private final GameObjectCollection gameObjectCollection;
    private final BrickerGameManager brickerGameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;
    private final Counter liveCounter;
//    private final Ball ball;



    public StrategyBrickFactory(GameObjectCollection gameObjectCollection,
                                BrickerGameManager brickerGameManager, ImageReader imageReader,
                                SoundReader soundReader,
                                UserInputListener inputListener,
                                WindowController windowController,
                                Vector2 windowDimensions,
                                Counter liveCounter
                                ){

        this.gameObjectCollection = gameObjectCollection;
        this.brickerGameManager = brickerGameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;


        this.liveCounter = liveCounter;
//        this.ball = ball;
    }
    public CollisionStrategy randomBrickStrategy(CollisionStrategy strategy){
        int randomIndex = new Random().nextInt(10); // generates a random number between 0 and 9

        if (randomIndex < 5) { // probability 1/2
            // Regular behavior - the behavior you already implemented in
            // the first part (the brick breaks and disappears).
            return strategy;
//        } else if (randomIndex == 5) { // probability 1/10
//            // Additional balls.
//            return new PukeBallsStrategy(strategy,soundReader,imageReader);
//        } else if (randomIndex == 6) { // probability 1/10
//            // Additional disk.
//            return new NewPaddleStrategy(strategy,imageReader,inputListener,windowDimensions);
        } else if (randomIndex == 7) { // probability 1/10
            // Camera change.
            return new CameraCollisionStrategy(strategy,windowController,brickerGameManager);
        } else if (randomIndex == 8) { // probability 1/10
            // Return of the missile.
            return new ReturnOfDisqualificationStrategy(strategy, imageReader, windowDimensions, liveCounter);
//        } else { // probability 1/10
//            // Double behavior.
//            return new DoubleBehaviourStrategy(this,gameObjectCollection,strategy);
        }else {
            return strategy;
        }

    }
    public CollisionStrategy getBrickStrategy(){
        return randomBrickStrategy(new BasicCollisionStrategy(gameObjectCollection));
    }

    public CollisionStrategy chooseSpecialStrategy(CollisionStrategy strategy , int randomIndex){
        if (randomIndex == 0) {
            // Additional balls.
            return new PukeBallsStrategy(strategy, soundReader, imageReader);
        }else if (randomIndex == 1) {
            // Additional disk.
            return new NewPaddleStrategy(strategy,imageReader,inputListener,windowDimensions);
//        } else if (randomIndex == 2) {
//            // Camera change.
//            return new CameraCollisionStrategy(strategy,windowController,brickerGameManager);
        } else {
            // Return of the missile.
            return new ReturnOfDisqualificationStrategy(strategy,imageReader,windowDimensions,liveCounter);
        }

    }
}




package bricker.main;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.Strategy;
import bricker.brick_strategies.StrategyBrickFactory;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.Random;

public class BrickerGameManager extends GameManager{
    private static final float HEIGHT_OF_BRICK = 15;
    private static final float BALL_SPEED = 200;
    private static final int TARGET_FRAMERATE = 80;
    private static final int BORDER_WIDTH = 10;
    private static final int DEFULT_NUMBER_BRICKS_IN_ROW = 7;
    private static final int DEFULT_NUMBER_ROWS = 8;

    private int numOfBricksInRow ;
    private int numOfRows;
    private Ball ball;
    private Vector2 windowDimensions ;
    private WindowController windowController;
    private final int NUMBER_OF_ROUNDS = 3;
    private Counter livesCounter;
    private Counter brickerCounter;
    private UserInputListener inputListener;
    private int currentBallCollisionCount;
    private boolean isCameraOn;
    private  Counter count = new Counter(0);
    public BrickerGameManager(String windowTitle, Vector2  windowDimensions,
                              int numOfBricksInRow, int numOfRows){
        super(windowTitle,windowDimensions);
        this.numOfBricksInRow = numOfBricksInRow;
        this.numOfRows = numOfRows;


    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
//        if (isCameraOn && ball.getCollisionCounter() >= 4 + currentBallCollisionCount) {
//            isCameraOn = false;
//            resetCamera();
//        }
    }


//    public void changeCamera() {
//        currentBallCollisionCount = ball.getCollisionCounter();
//
//        isCameraOn = true;
//        setCamera(new Camera(ball, Vector2.ZERO, windowDimensions.mult(1.2f), windowDimensions));
//    }

//    public void resetCamera() {
//        setCamera(null);
//    }
    private void checkForGameEnd() {
        float ballHeight = this.ball.getCenter().y();
        String prompt = "";

        if (ballHeight > windowDimensions.y()){
            // we lose
            if (livesCounter.value() <= 4)
                livesCounter.decrement();
            ball.setCenter(windowDimensions.mult(0.5f));
            if (livesCounter.value()  == 0) {
                prompt = "You lose! ";
            }

        }
        if(brickerCounter.value() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            // we win
            prompt = "You win!";
        }
        if(!prompt.isEmpty()){
            prompt += "Play again?";
            if(windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
//        System.out.println(livesCounter.value());
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.inputListener = inputListener;
//        windowController.setTargetFramerate(TARGET_FRAMERATE);
        windowDimensions =  windowController.getWindowDimensions();
        this.brickerCounter = new Counter(0);
        this.livesCounter = new Counter(NUMBER_OF_ROUNDS);
        // creating ball
        creatingBall(imageReader, soundReader, windowDimensions);

        // create paddle
        creatingPaddle(imageReader, inputListener, windowDimensions);
        // create borders

        creatingBorders(windowDimensions);

        // create background
        creatingBackground(imageReader, windowDimensions);

        // create bricks
        creatingBricks(imageReader, windowDimensions,soundReader);

        // creating graphic life
        creatingGrapicCount(imageReader);

        // create numeric life counter
        creatingNumericCount();
    }

    private void creatingNumericCount() {
        GameObject numericLifeCounter = new NumericLifeCount(new Vector2(BORDER_WIDTH ,
                windowDimensions.y() - 20)
                ,new Vector2(10,20), livesCounter,gameObjects());
        gameObjects().addGameObject(numericLifeCounter,Layer.BACKGROUND);
    }

    private void creatingGrapicCount(ImageReader imageReader) {
        Renderable graphicLifeImage = imageReader.readImage("assets/heart.png",
                true);
        GameObject graphicLifeCount = new GraphicLifeCount(new Vector2(BORDER_WIDTH + 30,
                windowDimensions.y() - 20)
                ,new Vector2(20,20),graphicLifeImage,livesCounter ,NUMBER_OF_ROUNDS,gameObjects());
        gameObjects().addGameObject(graphicLifeCount,Layer.BACKGROUND);
    }

    private void creatingBricks(ImageReader imageReader, Vector2 windowDimensions,SoundReader soundReader) {
        float brickWidth = (windowDimensions.x() - (10*2)  - (numOfBricksInRow ) * 3) / numOfBricksInRow;
        int totalBricks = numOfBricksInRow * numOfRows;
        int currentBrick = 0;
        int col = 0;
        StrategyBrickFactory strategyBrickFactory = new StrategyBrickFactory(gameObjects(),
                this,imageReader,soundReader,inputListener,
                windowController,windowDimensions,livesCounter);
        while (currentBrick < totalBricks) {
            float x = 10 + col * (brickWidth + 3) ;
            float y = 10 + (currentBrick/numOfBricksInRow) * (HEIGHT_OF_BRICK + 3);
            GameObject brick = new Brick(
                    new Vector2(x,y),
                    new Vector2(brickWidth, HEIGHT_OF_BRICK),
                    imageReader.readImage("assets/brick.png", false),
                    strategyBrickFactory.getBrickStrategy(),brickerCounter);
            this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            brick.setTag("brick");
            brickerCounter.increment();
            col = (col + 1) % numOfBricksInRow;
            if (col == 0) {
                currentBrick++;
            }
        }
    }

    private void creatingBackground(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg",
                false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions,backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void creatingBorders(Vector2 windowDimensions) {
        GameObject leftBorder = new GameObject(Vector2.ZERO,
                new Vector2(10, windowDimensions.y()),new RectangleRenderable(Color.BLACK));
        gameObjects().addGameObject(leftBorder);
        GameObject rightBorder = new GameObject(new Vector2(windowDimensions.x()-10,0),
                new Vector2(10, windowDimensions.y()),new RectangleRenderable(Color.BLACK));
        gameObjects().addGameObject(rightBorder);
        GameObject upperBorder = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(),10),new RectangleRenderable(Color.BLACK));
        gameObjects().addGameObject(upperBorder);
    }

    private void creatingPaddle(ImageReader imageReader, UserInputListener inputListener, Vector2 windowDimensions) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        GameObject paddle = new Paddle (Vector2.ZERO,new Vector2(100,15),
                paddleImage, inputListener,windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()-30));

        gameObjects().addGameObject(paddle);
        paddle.setTag("mainPaddle");
    }

    private void creatingBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions) {
        Renderable ballImage =
            imageReader.readImage("assets/ball.png",true);
        Sound collisonSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(Vector2.ZERO,new Vector2(20,20),ballImage,collisonSound);
        float ballVelX =  BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;

        ball.setVelocity(new Vector2(ballVelX,ballVelY));


        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);
        ball.setTag("Ball");
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            int numOfRows = Integer.parseInt(args[0]);
            int numOfBricksInRow = Integer.parseInt(args[1]);
            new BrickerGameManager("Brick Game", new Vector2(700, 500), numOfBricksInRow
                    , numOfRows).run();
        } else {
            new BrickerGameManager("Brick Game", new Vector2(700, 500),
                    DEFULT_NUMBER_BRICKS_IN_ROW,DEFULT_NUMBER_ROWS).run();

        }
    }
}

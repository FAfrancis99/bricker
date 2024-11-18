package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import danogl.GameObject;

import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;



public class PukeBallsStrategy extends Strategy{


    private static final float PUCK_SPEED = 250;
    private static final int NUMBER_OF_PUKES = 2;
    private final SoundReader soundReader;
    private final ImageReader imageReader;

    public PukeBallsStrategy(CollisionStrategy strategy, SoundReader soundReader,
                             ImageReader imageReader) {
        super(strategy);
        this.soundReader = soundReader;
        this.imageReader = imageReader;
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2, Counter counter) {
        super.onCollision(obj1, obj2, counter);
        // creating two pucks:
        if (obj1.getTag().equals("brick")){
            createTwoPukes(obj1);
        }

    }

    private void createTwoPukes(GameObject obj1) {
        int i = 0;
        while (NUMBER_OF_PUKES > i) {
            Puck puck = createPuck(obj1);
            PuckMove(puck);
            getGameObjects().addGameObject(puck);
            i++;
        }
    }

    private static void PuckMove(Puck puck) {
        Random rand = new Random();
        double angle = rand.nextDouble()*Math.PI;
        float velocityX = (float)Math.cos(angle) * PUCK_SPEED;
        float velocityY = (float)Math.sin(angle) * PUCK_SPEED;
        if (rand.nextBoolean())
            velocityX *= -1;
        if (rand.nextBoolean())
            velocityY *= -1;
        puck.setVelocity(new Vector2(velocityX, velocityY));
    }

    private Puck createPuck(GameObject obj1) {
        Puck puck = new Puck(obj1.getCenter(),
                new Vector2(15,15),
                imageReader.readImage("assets/mockBall.png",true),
                soundReader.readSound("assets/blop_cut_silenced.wav"));
        puck.setTag("puck");
        return puck;
    }
    //todo:
    // check if the puck out of borders.

}

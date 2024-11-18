package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Arrays;

public class GraphicLifeCount extends GameObject {
    private final Counter graphicLiveCounter;
    private final Vector2 dimensions;
    //    private int graphicLiveCounter;
    private  int numOfHearts ;
    private final GameObjectCollection lifeObject;
    private GameObject[] heartList;
    private Vector2 topLeftCorner;
    private Renderable renderable;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param lifeObject
     */
    public GraphicLifeCount(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                            Counter graphicLiveCounter, int numOfHearts, GameObjectCollection lifeObject) {
        super(topLeftCorner, dimensions, renderable);
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.graphicLiveCounter = graphicLiveCounter;
        this.numOfHearts = numOfHearts;
        this.lifeObject = lifeObject;
        this.renderable = renderable;
        // create graphic heart list
        creatHeartList(topLeftCorner, dimensions, renderable, numOfHearts, lifeObject);
    }

    private void creatHeartList(Vector2 topLeftCorner, Vector2 dimensions,
                                Renderable renderable, int numOfHearts,
                                GameObjectCollection lifeObject) {
        this.heartList = new GameObject[numOfHearts];
        int i = 0;
        while (i < numOfHearts){
            float coordinateX = topLeftCorner.x() + i * 25;
            Vector2 heartLocataion = new Vector2(coordinateX , topLeftCorner.y());
            this.heartList[i] = new GameObject(heartLocataion, dimensions, renderable);
            lifeObject.addGameObject(this.heartList[i],Layer.BACKGROUND);
            i++;
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (graphicLiveCounter.value()  < numOfHearts){
            GameObject heartToDelete = heartList[graphicLiveCounter.value()];
            this.lifeObject.removeGameObject(heartToDelete, Layer.BACKGROUND);
            numOfHearts--;
            // Create a new array and copy the remaining hearts
            createNewArrAndCopy();
        }
        handleExtraHeartCollision();
    }

    private void handleExtraHeartCollision() {
        if (canAddExtraHeart()) {
            incrementHeartCounter();
            addNewHeartToBackground();
            resetExtraHeartCheck();
        }
    }

    private boolean canAddExtraHeart() {
        return graphicLiveCounter.value() < 4 && Heart.checkExtraHeart;
    }

    private void incrementHeartCounter() {
        graphicLiveCounter.increment();
        numOfHearts++;
    }

    private void addNewHeartToBackground() {
        this.heartList = Arrays.copyOf(heartList, numOfHearts);
        Vector2 newHeartLocation = calculateNewHeartLocation();
        this.heartList[numOfHearts-1] = new GameObject(newHeartLocation, dimensions, renderable);
        lifeObject.addGameObject(this.heartList[numOfHearts-1], Layer.BACKGROUND);
    }

    private Vector2 calculateNewHeartLocation() {
        float coordinateX = topLeftCorner.x() + (numOfHearts-1) * 25;
        return new Vector2(coordinateX , topLeftCorner.y());
    }

    private void resetExtraHeartCheck() {
        Heart.checkExtraHeart = false;
    }


    private void createNewArrAndCopy() {
        GameObject[] newHeartList = new GameObject[numOfHearts];
        System.arraycopy(heartList, 0, newHeartList, 0, numOfHearts);
        this.heartList = newHeartList;
    }

    public int getGraphicLiveCounter(){
        return graphicLiveCounter.value();
    }

}

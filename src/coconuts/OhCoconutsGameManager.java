/*
 * Course: SWE2410-121
 * Fall 2025-2026
 * Name: Demarion Williams , Syeda
 * Created 10/15/2025
 */
package coconuts;

// https://stackoverflow.com/questions/42443148/how-to-correctly-separate-view-from-model-in-javafx

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.Collection;
import java.util.LinkedList;

// This class manages the game, including tracking all island objects and detecting when they hit
public class OhCoconutsGameManager {
    private final Collection<AbstractIslandObject> allObjects = new LinkedList<>();
    private final Collection<AbstractIslandObjectHittable> hittableIslandSubjects = new LinkedList<>();
    private final Collection<AbstractIslandObject> scheduledForRemoval = new LinkedList<>();
    protected HitEvent hitEvent = new HitEvent();

    private final int height, width;
    private final int DROP_INTERVAL = 10;
    private final int MAX_TIME = 100;
    private Pane gamePane;
    private IslandObjectHittableCrab theCrab;
    private IslandObjectBeach theBeach;
    private final ScoreBoard scoreboard;
    /* game play */
    private int coconutsInFlight = 0;
    private int gameTick = 0;

    public OhCoconutsGameManager(int height, int width, Pane gamePane) {
        this.height = height;
        this.width = width;
        this.gamePane = gamePane;
        this.theCrab = new IslandObjectHittableCrab(this, height, width);
        registerObject(theCrab);
        gamePane.getChildren().add(theCrab.getImageView());

        Label destroyedLabel = new Label();
        destroyedLabel.setLayoutX(10);
        destroyedLabel.setLayoutY(10);
        Label beachLabel = new Label();
        beachLabel.setLayoutX(10);
        beachLabel.setLayoutY(30);
        scoreboard = new ScoreBoard(destroyedLabel, beachLabel);
        hitEvent.attach(scoreboard);
        gamePane.getChildren().addAll(destroyedLabel, beachLabel);
        this.theBeach = new IslandObjectBeach(this, height, width);
        registerObject(theBeach);
        if (theBeach.getImageView() != null)
            System.out.println("Unexpected image view for beach");
    }

    private void registerObject(AbstractIslandObject object) {
        allObjects.add(object);
        if (object.isHittable()) {
            AbstractIslandObjectHittable asHittable = (AbstractIslandObjectHittable) object;
            hittableIslandSubjects.add(asHittable);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void coconutDestroyed() {
        coconutsInFlight -= 1;
    }

    public void tryDropCoconut() {
        if (gameTick % DROP_INTERVAL == 0 && theCrab != null) {
            coconutsInFlight += 1;
            IslandObjectHittableCoconut c = new IslandObjectHittableCoconut(this, (int) (Math.random() * width));
            registerObject(c);
            gamePane.getChildren().add(c.getImageView());
        }
        gameTick++;
    }

    public IslandObjectHittableCrab getCrab() {
        return theCrab;
    }

    public void killCrab() {
        theCrab = null;
    }

    public void advanceOneTick() {
        for (AbstractIslandObject o : allObjects) {
            o.step();
            o.display();
        }
        // see if objects hit; the hit itself is something you will add
        // you can't change the lists while processing them, so collect
        //   items to be removed in the first pass and remove them later
        scheduledForRemoval.clear();
        for (AbstractIslandObject thisObj : allObjects) {
            for (AbstractIslandObjectHittable hittableObject : hittableIslandSubjects) {
                if (thisObj.canHit(hittableObject) && thisObj.isTouching(hittableObject)) {
                    hitEvent.notifyAllObservers(hittableObject, thisObj);
                    scheduledForRemoval.add(hittableObject);
                    gamePane.getChildren().remove(hittableObject.getImageView());
                    if (hittableObject == theCrab) {
                        killCrab();
                    } else {
                        coconutDestroyed();
                    }
                }
            }
        }
        // actually remove the objects as needed
        for (AbstractIslandObject thisObj : scheduledForRemoval) {
            allObjects.remove(thisObj);
            if (thisObj instanceof AbstractIslandObjectHittable) {
                hittableIslandSubjects.remove((AbstractIslandObjectHittable) thisObj);
            }
        }
        scheduledForRemoval.clear();
    }

    public void scheduleForDeletion(AbstractIslandObject islandObject) {
        scheduledForRemoval.add(islandObject);
    }

    public boolean done() {
        boolean gameOver = coconutsInFlight == 0 && gameTick >= MAX_TIME;
        if (gameOver) {
            hitEvent.detach(scoreboard);
        }
        return gameOver;
    }

    public void fireLaser() {
        if (theCrab == null) return;
        IslandObjectLaserBeam laser = new IslandObjectLaserBeam(this, theCrab.y, theCrab.x + theCrab.width / 2);
        registerObject(laser);
        gamePane.getChildren().add(laser.getImageView());
    }
}